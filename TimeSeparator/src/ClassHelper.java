import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.StringToWordVector;

import java.awt.Frame;
import java.awt.List;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;

import javax.naming.InitialContext;
import javax.swing.JOptionPane;

import org.joda.time.DateTime;


import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.bayes.NaiveBayesMultinomial;
import weka.classifiers.functions.LinearRegression;
import weka.classifiers.functions.SMO;
import weka.classifiers.functions.SimpleLogistic;
import weka.classifiers.functions.supportVector.PolyKernel;
import weka.classifiers.functions.supportVector.RegSMO;
import weka.core.Capabilities;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.NominalToString;
import weka.filters.unsupervised.attribute.StringToWordVector;
import weka.filters.unsupervised.attribute.SortLabels.CaseInsensitiveComparator;
import weka.gui.SaveBuffer;
import weka.gui.treevisualizer.TreeBuild;
import weka.classifiers.trees.J48; 
import weka.core.Instances;
import weka.datagenerators.Test;
import weka.experiment.InstanceQuery;
public class ClassHelper {

	String DATASOURCE_PATH="C:\\Users\\Kwstas\\Desktop\\wekaTest\\training3_3_10_7Stemmed.arff";
	String MODEL_PATH="";
	 String OUPUT_PATH="C:\\Users\\Kwstas\\DropboxV2\\Dropbox\\thesis drafts\\post.sql_RepLinksGreekClean.sql";

	public ClassHelper()
	{
		
	
	}
	
	private Instances loadData() throws Exception
	{
		DataSource sourceTrain = new DataSource(DATASOURCE_PATH);
		Instances data = sourceTrain.getDataSet();
		
StringToWordVector filter = new StringToWordVector();
		
		filter.setOptions(
			      weka.core.Utils.splitOptions("-R first-last -W 1000 " +
		"-prune-rate -1.0 -N 0 -stemmer" +
		" weka.core.stemmers.NullStemmer -M 6 -tokenizer " +
		"weka.core.tokenizers.WordTokenizer " +
		"-delimiters \" \\r\\n\\t.,;:\\\'\\\"()?!\""));
	
		 filter.setInputFormat(data);  // initializing the filter once with training set
		 Instances newInstances = Filter.useFilter(data, filter);
		 return newInstances;
	}
	
	public void setDatasourcePath(String DATASOURCE_PATH) {
		this.DATASOURCE_PATH=DATASOURCE_PATH;
		
	}
	
	public void setModelPath(String DATASOURCE_PATH) 
	{
		
	}
	
	public void GenerateClassificationData(Instances data,String StartDay,String StopDay,int constantDays) throws Exception
	{
		////////////////////////////////////////////////
		// Test the model
//		Evaluation eTestTesting = new Evaluation(newTest1);
		//eTestTesting.evaluateModel(cModel1, newTest1);
		
		if (data.classIndex() == -1) {
			data.setClassIndex(0);
		}
		
			
	Classifier cModel = (Classifier) weka.core.SerializationHelper.read(MODEL_PATH);
	
		ArrayList<UserInfo> usrInfLst=new ArrayList<UserInfo>();
		
		//old testing
		for (int i = 0; i < data.numInstances(); i++) {
			
		
			double pred = cModel.classifyInstance(data.instance(i));
			
		//	System.out.println(data.instance(i)+"-"+pred);
			String usrIdString=data.instance(i).stringValue(3);
			boolean found=false;
			int c=0;
			while(c<usrInfLst.size()&& found==false) {
			if (usrInfLst.get(c).getId()==usrIdString)
			{
				Post post=new Post();
				post.setDate(data.instance(i).stringValue(2));
				post.setMessageString(data.instance(i).stringValue(3));
				post.setMes_class(pred);
				usrInfLst.get(c).getPostList().add(post);
				found=true;
				
			}
			c++;	
			}
			if(found==false)
			{
				UserInfo usi=new UserInfo();
				usi.setPostList(new ArrayList<Post>());
				usi.setId(usrIdString);
				
				Post post=new Post();
				post.setDate(data.instance(i).stringValue(2));
				post.setMessageString(data.instance(i).stringValue(3));
				post.setMes_class(pred);
				usi.getPostList().add(post);
				usrInfLst.add(usi);
			}
			  FileOutputStream fout = new FileOutputStream( new File( OUPUT_PATH ) );
			    PrintWriter out = new PrintWriter(new OutputStreamWriter(fout, "UTF-8"));
			 
			for (UserInfo userInfo : usrInfLst) {
			     ArrayList<Post> pstLstList=userInfo.getPostList();
			     UserCalendar uCalendar=new UserCalendar(StartDay, StopDay, constantDays);
			    for (Post post : pstLstList) {
			    	//Type for posts is 0
			    	uCalendar.inserMessageToPeriod(post.getMes_class(),DateTime.parse(post.getDate()), 0);
			   		
				}
			    
			    userInfo.setUserCalendr(uCalendar.getUserCalendar());
			   	}
			
			
		}
	}



}
