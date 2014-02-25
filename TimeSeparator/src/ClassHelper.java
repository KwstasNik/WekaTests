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

	String POST_DATASOURCE_PATH="C:\\Users\\Kwstas\\Desktop\\wekaTest\\musicOnlyStemmed.arff";
	String COMMENT_DATASOURCE_PATH="C:\\Users\\Kwstas\\Desktop\\wekaTest\\musicOnlyStemmed.arff";
	
	String MODEL_PATH="";
	 String OUPUT_PATH="C:\\Users\\Kwstas\\DropboxV2\\Dropbox\\thesis drafts\\post.sql_RepLinksGreekClean.sql";
	 Instances	  postdata ;
	 Instances    commentData;
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
							
			 postdata = query.retrieveInstances();
				
			
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
	
	public void setDatasourcePath(String DATASOURCE_PATH,String COMMENT_DATASOURCE_PATH) {
		this.POST_DATASOURCE_PATH=DATASOURCE_PATH;
		this.COMMENT_DATASOURCE_PATH=COMMENT_DATASOURCE_PATH;
	}
	
	
	
	
	public Instances loadFromPostDatasource(Filter filter,String DATASOURCE_PATH) throws Exception
	{
		DataSource sourceTrain = new DataSource(DATASOURCE_PATH);
		postdata = sourceTrain.getDataSet();
		 Instances data2=sourceTrain.getDataSet();
		 data2.deleteAttributeAt(2);
		 data2.deleteAttributeAt(2);
		 Instances newInstances = Filter.useFilter(data2, filter);
		 return newInstances;
	}
	
	public Instances loadFromCommentDatasource(Filter filter,String DATASOURCE_PATH) throws Exception
	{
		DataSource sourceTrain = new DataSource(DATASOURCE_PATH);
		 commentData = sourceTrain.getDataSet();
		 Instances data2=sourceTrain.getDataSet();
		 data2.deleteAttributeAt(3);
		 data2.deleteAttributeAt(3);
		 Instances newInstances = Filter.useFilter(data2, filter);
		 return newInstances;
	}
	
	
	
	public void setModelPath(String MODEL_PATH) 
	{
		this.MODEL_PATH=MODEL_PATH;
	}
	
	public ArrayList<UserInfo> GenerateClassificationData(Instances filteredPostData,Instances filteredCommentData,String StartDay,String StopDay,int constantDays,	Classifier cModel ) throws Exception
	{
		////////////////////////////////////////////////
		// Test the model
//		Evaluation eTestTesting = new Evaluation(newTest1);
		//eTestTesting.evaluateModel(cModel1, newTest1);
		
		if (filteredPostData.classIndex() == -1) {
			filteredPostData.setClassIndex(0);
		}
		

	
		ArrayList<UserInfo> usrInfLst=new ArrayList<UserInfo>();
		//data.deleteAttributeAt(1);
		//old testing
		//data.delete(1);
		ArrayList <InstanceInfo> instInfList=new <InstanceInfo>ArrayList();
		
		for (int i = 0; i < filteredPostData.numInstances(); i++) {
			InstanceInfo insInfo=new InstanceInfo();
			insInfo.setDate((postdata.instance(i).stringValue(2)));
			insInfo.setUserId(postdata.instance(i).stringValue(3));
			insInfo.setInstanceMessage((postdata.instance(i).stringValue(0)));
			//filteredData.instance(i).deleteAttributeAt(0);
			//filteredData.instance(i).deleteAttributeAt(1);
			//filteredData.instance(i).deleteAttributeAt(data.instance(i).numAttributes()-2);
		
			insInfo.setInstance(filteredPostData.instance(i));
			instInfList.add(insInfo);
		}
		
		ArrayList <InstanceInfo> instInfListComment=new <InstanceInfo>ArrayList();
		
		for (int i = 0; i < filteredCommentData.numInstances(); i++) {
			InstanceInfo insInfo=new InstanceInfo();
			insInfo.setDate((commentData.instance(i).stringValue(3)));
			insInfo.setUserId(commentData.instance(i).stringValue(4));
			insInfo.setInstanceMessage((commentData.instance(i).stringValue(0)));
			insInfo.setRelatedPostId((commentData.instance(i).stringValue(2)));
			
		
		
			insInfo.setInstance(filteredCommentData.instance(i));
			instInfListComment.add(insInfo);
		}
		
		
		
	
			
		   setPostsInuserList(instInfList,cModel,usrInfLst);
		   setCommentsInuserList(instInfListComment,cModel,usrInfLst);
			
		
		
		
		
		
			  FileOutputStream fout = new FileOutputStream( new File( OUPUT_PATH ) );
			    PrintWriter out = new PrintWriter(new OutputStreamWriter(fout, "UTF-8"));
			    DateTime stopDateTime=DateTime.parse(StopDay);
			    DateTime startDateTime=DateTime.parse(StartDay);
				
			for (UserInfo userInfo : usrInfLst) {
			     ArrayList<Post> pstLstList=userInfo.getPostList();
			     ArrayList<Comment> cmtLstList=userInfo.getCommentList();
				  
			     UserCalendar uCalendar=new UserCalendar(StartDay, StopDay, constantDays);
			     
			    for (Post post : pstLstList) {
			    	//Type for posts is 0
			    	DateTime postDateTime=DateTime.parse(post.getDate());
			    	
			    	if((postDateTime.isAfter(startDateTime)||
			    			postDateTime.isEqual(startDateTime))&&
			    			(postDateTime.isBefore(stopDateTime)||
			    			postDateTime.isEqual(stopDateTime))
			    		
			    			){
			    
			    		//uCalendar.inserMessageToPeriod(post.getMes_class(),postDateTime, 0,post.getMessageString());
			    		uCalendar.insertPostToPeriod(post);
			    		
			    	}
				}
			    
			    for (Comment comment : cmtLstList) {
			    	//Type for posts is 0
			    	DateTime commentDateTime=DateTime.parse(comment.getDate());
			    	
			    	if((commentDateTime.isAfter(startDateTime)||
			    			commentDateTime.isEqual(startDateTime))&&
			    			(commentDateTime.isBefore(stopDateTime)||
			    					commentDateTime.isEqual(stopDateTime))
			    		
			    			){
			    
			    		//uCalendar.inserMessageToPeriod(post.getMes_class(),postDateTime, 0,post.getMessageString());
			    		uCalendar.insertCommentToPeriod(comment);
			    		
			    	}
				}
			    
			    userInfo.setUserCalendr(uCalendar.getUserCalendar());
			   	}
			
			
			
		
		return usrInfLst;
	}


	
	private ArrayList<UserInfo>  setPostsInuserList(ArrayList <InstanceInfo> instInfList,Classifier cModel,	ArrayList<UserInfo> usrInfLst) throws Exception
	{
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
			
			//Posts
			
			while(c<usrInfLst.size()&& found==false) {
			if (usrInfLst.get(c).getId()==usrIdString)
			{
				
				Post post=new Post();
				post.setDate(instInfList.get(i).getDate());
				try {
					post.setMessageString(instInfList.get(i).getInstanceMessage());
					
				} catch (Exception e) {
					post.setMessageString("error in MessageParsing");
					
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
				usi.setCommentList(new ArrayList<Comment>());
				usi.setId(usrIdString);
				
				Post post=new Post();
				post.setDate(postdata.instance(i).stringValue(2));
				post.setMessageString(instInfList.get(i).getInstanceMessage());
				post.setMes_class(pred);
				usi.getPostList().add(post);
				usrInfLst.add(usi);
			}
			
		}
		
		return usrInfLst;
	}

	
	private ArrayList<UserInfo>  setCommentsInuserList(ArrayList <InstanceInfo> instInfList,Classifier cModel,	ArrayList<UserInfo> usrInfLst) throws Exception
	{
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
			
			//Comments
			
			while(c<usrInfLst.size()&& found==false) {
			if (usrInfLst.get(c).getId()==usrIdString)
			{
				Comment comment=new Comment();
				comment.setDate(instInfList.get(i).getDate());
				comment.setMes_class(pred);
				comment.setRelatedpostId(instInfList.get(i).getRelatedPostId());
		    
				try {
					comment.setMessageString(instInfList.get(i).getInstanceMessage());
					
				} catch (Exception e) {
					comment.setMessageString("error in MessageParsing");
					
				}
				
				usrInfLst.get(c).getCommentList().add(comment);
				found=true;
				
			}
			c++;	
			}
			if(found==false)
			{
				UserInfo usi=new UserInfo();
				usi.setPostList(new ArrayList<Post>());
				usi.setCommentList(new ArrayList<Comment>());
				usi.setId(usrIdString);
				
				Comment comment=new Comment();
				comment.setDate(postdata.instance(i).stringValue(2));
				comment.setMessageString(instInfList.get(i).getInstanceMessage());
				comment.setMes_class(pred);
				usi.getCommentList().add(comment);
				usrInfLst.add(usi);
			}
			
		}
		
		return usrInfLst;
	}

}
