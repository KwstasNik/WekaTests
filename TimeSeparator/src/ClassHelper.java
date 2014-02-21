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

	String DATASOURCE_PATH="C:\\Users\\Kwstas\\Desktop\\wekaTest\\musicOnlyStemmed.arff";
	String MODEL_PATH="";
	 String OUPUT_PATH="C:\\Users\\Kwstas\\DropboxV2\\Dropbox\\thesis drafts\\post.sql_RepLinksGreekClean.sql";
	 Instances	  data ;
	public ClassHelper()
	{
		
	
	}
	
	public Instances loadDataFromDb(Filter filter) throws Exception
	{
		//DataSource sourceTrain = new DataSource(DATASOURCE_PATH);
		InstanceQuery query = new InstanceQuery();
			 query.setUsername("root");
			 query.setPassword("");
			 query.setQuery("Select * FROM allingreekcleanv3 where class=1 limit 1000");
			 
			 Instances data2 = query.retrieveInstances();
			 data2.deleteAttributeAt(0);
			 data2.deleteAttributeAt(0);
			 data2.deleteAttributeAt(1);
							
			 data = query.retrieveInstances();
				
			
			 NominalToString flt=new NominalToString();
			 flt.setOptions(weka.core.Utils.splitOptions("-C 1"));
			 flt.setInputFormat(data2);  // initializing the filter once with training set
				
			 data2=flt.useFilter(data2, flt);
	//	 data = sourceTrain.getDataSet();
		
/*StringToWordVector filter = new StringToWordVector();
		
		filter.setOptions(
			      weka.core.Utils.splitOptions("-R first-last -W 1000 " +
		"-prune-rate -1.0 -N 0 -stemmer" +
		" weka.core.stemmers.NullStemmer -M 6 -tokenizer " +
		"weka.core.tokenizers.WordTokenizer " +
		"-delimiters \" \\r\\n\\t.,;:\\\'\\\"()?!\""));*/
	//	Filter filter = (Filter) weka.core.SerializationHelper.read("C:\\Users\\Kwstas\\Desktop\\wekaTest\\filter");
				
	
		// filter.setInputFormat(data2);  // initializing the filter once with training set
		 
		 Instances newInstances = Filter.useFilter(data2, filter);
		
		 return newInstances;
	}
	
	public void setDatasourcePath(String DATASOURCE_PATH) {
		this.DATASOURCE_PATH=DATASOURCE_PATH;
		
		
	}
	
	public Instances loadFromDatasource(Filter filter,String DATASOURCE_PATH) throws Exception
	{
		DataSource sourceTrain = new DataSource(DATASOURCE_PATH);
		 data = sourceTrain.getDataSet();
		 Instances data2=sourceTrain.getDataSet();
		 data2.deleteAttributeAt(2);
		 data2.deleteAttributeAt(2);
		 Instances newInstances = Filter.useFilter(data2, filter);
		 return newInstances;
	}
	
	
	public void setModelPath(String MODEL_PATH) 
	{
		this.MODEL_PATH=MODEL_PATH;
	}
	
	public ArrayList<UserInfo> GenerateClassificationData(Instances filteredData,String StartDay,String StopDay,int constantDays,	Classifier cModel ) throws Exception
	{
		////////////////////////////////////////////////
		// Test the model
//		Evaluation eTestTesting = new Evaluation(newTest1);
		//eTestTesting.evaluateModel(cModel1, newTest1);
		
		if (filteredData.classIndex() == -1) {
			filteredData.setClassIndex(0);
		}
		

	
		ArrayList<UserInfo> usrInfLst=new ArrayList<UserInfo>();
		//data.deleteAttributeAt(1);
		//old testing
		//data.delete(1);
		ArrayList <InstanceInfo> instInfList=new <InstanceInfo>ArrayList();
		
		for (int i = 0; i < filteredData.numInstances(); i++) {
			InstanceInfo insInfo=new InstanceInfo();
			insInfo.setDate((data.instance(i).stringValue(2)));
			insInfo.setUserId(data.instance(i).stringValue(3));
			
			//filteredData.instance(i).deleteAttributeAt(0);
			//filteredData.instance(i).deleteAttributeAt(1);
			//filteredData.instance(i).deleteAttributeAt(data.instance(i).numAttributes()-2);
		
			insInfo.setInstance(filteredData.instance(i));
			instInfList.add(insInfo);
		}
		int count=0;
		for (int i = 0; i < instInfList.size(); i++) {
			
	
			
			double pred = cModel.classifyInstance(instInfList.get(i).getInstance());
			
			System.out.println(pred);
			System.out.println(count);
			if (pred==0.0)
			{
				count++;
			}
		//	System.out.println(data.instance(i)+"-"+pred);
			String usrIdString=instInfList.get(i).getUserId();
			boolean found=false;
			int c=0;
			
			while(c<usrInfLst.size()&& found==false) {
			if (usrInfLst.get(c).getId()==usrIdString)
			{
				Post post=new Post();
				post.setDate(instInfList.get(i).getDate());
				try {
					post.setMessageString(instInfList.get(i).getInstance().stringValue(1));
					
				} catch (Exception e) {
					post.setMessageString("error");
					
				}
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
		}
	
		
			  FileOutputStream fout = new FileOutputStream( new File( OUPUT_PATH ) );
			    PrintWriter out = new PrintWriter(new OutputStreamWriter(fout, "UTF-8"));
			    DateTime stopDateTime=DateTime.parse(StopDay);
			    DateTime startDateTime=DateTime.parse(StartDay);
				
			for (UserInfo userInfo : usrInfLst) {
			     ArrayList<Post> pstLstList=userInfo.getPostList();
			     UserCalendar uCalendar=new UserCalendar(StartDay, StopDay, constantDays);
			     
			    for (Post post : pstLstList) {
			    	//Type for posts is 0
			    	DateTime postDateTime=DateTime.parse(post.getDate());
			    	
			    	if((postDateTime.isAfter(startDateTime)||
			    			postDateTime.isEqual(startDateTime))&&
			    			(postDateTime.isBefore(stopDateTime)||
			    			postDateTime.isEqual(stopDateTime))
			    		
			    			){
			    
			    		uCalendar.inserMessageToPeriod(post.getMes_class(),postDateTime, 0);
			    	}
				}
			    
			    userInfo.setUserCalendr(uCalendar.getUserCalendar());
			   	}
			
			
			
		
		return usrInfLst;
	}



}
