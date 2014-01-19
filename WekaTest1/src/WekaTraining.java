

import java.awt.Frame;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Timer;

import javax.naming.InitialContext;
import javax.swing.JOptionPane;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

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

public class WekaTraining {


	public static void main(String[] args) throws Exception {
		//VARIABLES
		int previousExpId=0;
		
	//	String METHOD="NaiveBayesMultinomial" Cannot handle multi-valued nominal class;
		String METHOD="SMO";
	//	String METHOD="NaiveBayes";
	//	String METHOD="J48";
	//	String METHOD="LinearRegression" Cannot handle multi-valued nominal class;
		
		
		/*int DATA_ID=1;
		String QUERY_LEARN="SELECT message,class FROM  postclassified1000 ";
		String QUERY_TEST="select  message,class from post100Testing";*/
		
	//	int DATA_ID=2;
	//	String QUERY_LEARN="SELECT message,class FROM  postclassified1000 WHERE class !=  '10'";
	//	String QUERY_TEST="select  message,class from post100Testing WHERE class !=  '10'";
		
	//	int DATA_ID=3;
		//String QUERY_LEARN="SELECT message,class FROM  postclassified1000 WHERE class !=  '10'";
	//	String QUERY_TEST="select  message,class from post500Testing WHERE class !=  '10'";
		
		/*int DATA_ID=4;
		String QUERY_LEARN="SELECT message,class FROM  postclassified1000stemmed  ";
		String QUERY_TEST="select  message,class from post500TestingStemmed ";*/
		
		
		/*	int DATA_ID=5;
			String QUERY_LEARN="SELECT message,class FROM  postclassified1000 ";
			String QUERY_TEST="select  message,class from post500Testing";*/
			
		/*	int DATA_ID=6;
			String QUERY_LEARN="SELECT message,class FROM  postclassified500vag where class!=10	 union" +
					"	select  message,class from postclassified1000	where class!=10";
			String QUERY_TEST="select  message,class from post500Testing where class!=10";
			*/
			
		/*	int DATA_ID=7;
			String QUERY_LEARN="SELECT message,class FROM  postclassified500vag  union " +
					"	select  message,class from postclassified1000	where class!=10 union "+
					"select  message,class from post300SotClassified";
			String QUERY_TEST="select  message,class from post500Testing where class";*/
			
			/*int DATA_ID=8;
			String QUERY_LEARN="SELECT message,class FROM  postclassified500vag  union " +
					"	select  message,class from postclassified1000	where class!=10 union "+
					"	select  message,class from postClassified1500to2000	where class!=10 union "+
					"select  message,class from post300SotClassified ";
			String QUERY_TEST="select  message,class from post500Testing ";*/
			
			/*int DATA_ID=9;
			String QUERY_LEARN="SELECT message,class FROM  postclassified500vag  where class!=10 union " +
					" select  message,class from postclassified1000	where class!=10 union "+
					"	select  message,class from postClassified1500to2000	where class!=10 union "+
					"select  message,class from post300SotClassified where class!=10 union "+
					"(select   message,class from post300SotClassified where class=10 LIMIT 100)";
			
			String QUERY_TEST="select  message,class from post500Testing ";*/
			
		/*int DATA_ID=10;
		String QUERY_LEARN="SELECT message,class FROM  postclassified500vag  where class!=10 union " +
				" select  message,class from postclassified1000	union "+
			//	"	select  message,class from postClassified1500to2000	 ";
				"select  message,class from post300SotClassified where class!=10  ";
			*/
		
		//int DATA_ID=10;
		/*String QUERY_LEARN="SELECT message,class FROM  postclassified500vag union " +
				" select  message,class from post500Testing where class!=10 union "+
				"	select  message,class from postClassified1500to2000	  union "+
				" select  message,class from post300SotClassified where class!=10 union "+
				" select  message,class from FaF2kto30k11700classified where class!=10 union "+
				"select   message,class from post300SotClassified where class=10";
		
		String QUERY_TEST="select  message,class from postclassified1000 ";*/
		
		
		/*int DATA_ID=11;
		String QUERY_LEARN="SELECT message,class FROM  postclassified500vag union " +
				" select  message,class from post500Testing  union "+
				"	select  message,class from  postclassified1000   union "+
				" select  message,class from post300SotClassified   union "+
				" select  message,class from FaF2kto30k11700classified where class!=10 union "+
				"select   message,class from post300SotClassified ";
		
		String QUERY_TEST="select  message,class from  postClassified1500to2000	";
		*/
		
		int DATA_ID=12;
		String QUERY_LEARN="Select message,class from( " +
						"Select * From TopIrrelevant union " +
						"Select * From  TopSports union "+
						"Select * From TopMusic union "+
						"Select * From  TopPolitics union "+
						"Select * From  TopNews  "+
						") as All0 order by message Limit 500";
		
		

		String QUERY_TEST="select  message,class from  postclassified1000	";
		
			
		Boolean saveToDataBase =false;
		 String comment="No Comment";
			
		 
		String filterOptionsString="weka.filters.unsupervised.attribute.StringToWordVector -R first-last -W 1000 -prune-rate -1.0 -I -N 0 -stemmer weka.core.stemmers.NullStemmer -M 1 -tokenizer"+ 
					" \"weka.core.tokenizers.WordTokenizer -delimiters  \\r\\n\\t.,;:\\\'\\\"()?!\"";
		System.out.println("Filter Option String: "+filterOptionsString+"/n");
				
		 Instances data=null;
		 InstanceQuery query=null;	
//End Variables
	 
	 //Dialog Boxes
		 JOptionPane optionPane= new JOptionPane(
				   "SaveBuffer Test to Database?",
				    JOptionPane.QUESTION_MESSAGE,
				    JOptionPane.YES_NO_OPTION);
		 int res=optionPane.showConfirmDialog(null, "Save the test to Database ?");
	 if (res==0)
	 {
		 saveToDataBase=true;
		 comment=optionPane.showInputDialog("Comments For the Test");
	 }
	 
	 
	 
		  query = new InstanceQuery();
		 query.setUsername("root");
		 query.setPassword("");
		 
		// query.setQuery("SELECT message,class FROM  postclassified1000 WHERE class !=  ''");
		 
		 query.setQuery(QUERY_LEARN);
			
		 // You can declare that your data set is sparse
		 // query.setSparseData(true);
		  data = query.retrieveInstances();
		//System.out.println("Instance"+data.toString());
			
		
	
		 
		
		
		String ClassifierName=METHOD;
		
	//DataSource sourceTrain = new DataSource("trainingSet.arff");
		
		//Instances train = null;
	//	train = sourceTrain.getDataSet();
		Instances trainPre=data;
		
	  NominalToString nmFilterString=new NominalToString();
	  nmFilterString.setAttributeIndexes("1");
		StringToWordVector filter = new StringToWordVector();
	
		filter.setOptions(
			      weka.core.Utils.splitOptions(filterOptionsString));
		
//    -R first-last -W 100000 -prune-rate -1.0 -T -I -N 0 -L -stemmer weka.core.stemmers.NullStemmer -M 1 -tokenizer "weka.core.tokenizers.WordTokenizer -delimiters \" \\r\\n\\t.,;:\\\'\\\"()?!\""	
	//	String[] aList= filter.getOptions();
		nmFilterString.setInputFormat(trainPre);
		Instances train=Filter.useFilter(trainPre, nmFilterString);
		
		 filter.setInputFormat(train);  // initializing the filter once with training set
		 Instances newTrain = Filter.useFilter(train, filter);  // configures the Filter based on train instances and returns filtered instances
	/*	for (int i=0;i<10;i++){
		 System.out.println(newTrain.get(i));
		}*/

			if (newTrain.classIndex() == -1){
				newTrain.setClassIndex(0);
			}
			
		 Classifier cModel=null;
		 RegSMO reg=null;
		 System.out.println(" Reporting \n");
		System.out.println("Filter Option String: "+filterOptionsString+"/n");
			
		 switch (METHOD)
		 {
		 case "NaiveBayesMultinomial":
			 
	    System.out.println("training NaiveBayesMultinomial");
	    
		 cModel=new NaiveBayesMultinomial();
		 
		 break;
		 case "NaiveBayes":
			 System.out.println("training NaiveBayes");
			
			  cModel = new NaiveBayes();
			 break;
			 
	     case "LinearRegression":
	    	 System.out.println("training LinearRegression");
	    	
	    	 cModel=new LinearRegression();
	    	 break;
				 
	     case "J48":
	    	 System.out.println("training J48");
	    	 
	    	cModel=new J48();
				
			
	    	 break;
	    	 
	    	
	     case "SMO":
	    	 System.out.println("training SVM");
	    	
		    	 System.out.println("training SMO");
		    	 
		    	 cModel=  new SMO();
		    	  
	    	 
	    	 
	    	 break;
	     case "LogisticRegression":
	    	 
			 System.out.println("training Logistic Regression");
			 
			  cModel = new weka.classifiers.functions.Logistic();
			 break;
	    default:
	    		 
	     
	     
		 
		 
		 
		 
		 }
		
		 
			long start = System.nanoTime();  
		 cModel.buildClassifier(newTrain);
		 long elapsedTrainingTime = System.nanoTime() - start;
		 
		 
		//save models
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ClassifierName+".model"+DATA_ID+METHOD));
		oos.writeObject(cModel);
		oos.flush();
		oos.close();
		
		//save filter
		ObjectOutputStream oos1 = new ObjectOutputStream(new FileOutputStream(ClassifierName+"filter"+"Test"+DATA_ID+METHOD));
		oos1.writeObject(filter);
		oos1.flush();
		oos1.close();

		System.out.println("-----\nclassifier testing using training set\n-----");
		System.out.println("-----------------------------------\nclassifier" +ClassifierName);
		 // Test the model
		 Evaluation eTestTraining = new Evaluation(newTrain);
		 eTestTraining.evaluateModel(cModel, newTrain);
		 
		 String strSummary = eTestTraining.toSummaryString();
		 System.out.println(strSummary);
		 
		 // Get the confusion matrix
		 double[][] cmMatrix = eTestTraining.confusionMatrix();
		 
		 System.out.println("Eval instances: " + eTestTraining.numInstances());
			int counter = 0;
			for (int i = 0; i < eTestTraining.numInstances(); i++) {
				double pred = cModel.classifyInstance(newTrain.instance(i));
				if (newTrain.instance(i).classAttribute().value((int) newTrain.instance(i).classValue()).equals(newTrain.instance(i).classAttribute().value((int) pred))) {
				} else {
					counter++;
				}
			}
			
		
			System.out.println("different values " + counter);
			try {
				System.out.println( eTestTraining.toClassDetailsString());
				System.out.println(eTestTraining.toMatrixString());
			
			
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			
			}
			 

			 
			
		
			 
				 
/////////////////////////////////////////////////////////////////////////////////////////////
		// test NB with test set
		System.out.println("classifier "+ClassifierName+" test set");
	
		//DataSource source1 = new DataSource("testSet.arff");
	
		Instances test = null;
		InstanceQuery	testquery = new InstanceQuery();
		testquery.setUsername("root");
		testquery.setPassword("");
			
		//testquery.setQuery("select  message,class from post100Testing WHERE class !=  ''");
		testquery.setQuery(QUERY_TEST);
		 
		  test = testquery.retrieveInstances();
	
		
	
			
			
		
//	    -R first-last -W 100000 -prune-rate -1.0 -T -I -N 0 -L -stemmer weka.core.stemmers.NullStemmer -M 1 -tokenizer "weka.core.tokenizers.WordTokenizer -delimiters \" \\r\\n\\t.,;:\\\'\\\"()?!\""	
			nmFilterString.setInputFormat(test);
			Instances newTest1Pre=Filter.useFilter(test, nmFilterString);
			
			

		Filter filter2 = (Filter) weka.core.SerializationHelper.read(ClassifierName+"filter"+"Test"+DATA_ID+METHOD);
		String[] aList= filter.getOptions();
		Instances newTest1 = Filter.useFilter(newTest1Pre, filter2); 
															
		Classifier cModel1 = (Classifier) weka.core.SerializationHelper.read( ClassifierName+".model"+DATA_ID+METHOD);


		if (newTest1.classIndex() == -1) {
			newTest1.setClassIndex(0);
		}
		// Test the model
		Evaluation eTestTesting = new Evaluation(newTest1);
		eTestTesting.evaluateModel(cModel1, newTest1);
		
		Instance instances=newTest1.instance(1);
		String strSummary2 = eTestTesting.toSummaryString();
		System.out.println(strSummary2);

		// Get the confusion matrix
		double[][] cmMatrix2 = eTestTesting.confusionMatrix();

		
		
		System.out.println("Eval instances: " + eTestTesting.numInstances());
		int counter1 = 0;
		double MeanClassificationTime=0;
		int ClassificationTime=0;
		for (int i = 0; i < eTestTesting.numInstances(); i++) {
			
			 start = System.nanoTime();  
			double pred = cModel1.classifyInstance(newTest1.instance(i));
			long time= System.nanoTime()-start;
			ClassificationTime+=time;
			if (newTest1.instance(i).classAttribute().value((int) newTest1.instance(i).classValue()).equals(newTest1.instance(i).classAttribute().value((int) pred))) {
			} else {
				counter1++;
			}
		}
		MeanClassificationTime=(ClassificationTime/ eTestTesting.numInstances());
		
		
	
			try {
				System.out.println(eTestTesting.toClassDetailsString());
			
			String stringBuffer=eTestTesting.toMatrixString();
			
			
			
			System.out.println(stringBuffer);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		if(saveToDataBase)
		{	
				
			
			InstanceQuery  queryExp = new InstanceQuery();
			queryExp.setUsername("root");
			queryExp.setPassword("");
			queryExp.setQuery("SELECT Id from experiment order by id");
				Instances  dataExp = queryExp.retrieveInstances();
			 if (!dataExp.isEmpty()){
				 previousExpId=(int)dataExp.get(dataExp.numInstances()-1).value(0);
				 previousExpId=previousExpId+1;
				 }
			 Helpers.saveTrainingMetricsToDatabase(eTestTraining, ClassifierName, elapsedTrainingTime, MeanClassificationTime, previousExpId, DATA_ID, queryExp,Helpers.createClassList(eTestTraining), comment,filterOptionsString);
				
			Helpers.saveTestingMetricsToDatabase(eTestTesting, ClassifierName, elapsedTrainingTime, MeanClassificationTime, previousExpId, DATA_ID, queryExp,Helpers.createClassList(eTestTesting));
	
		}
	
	
	//------------------------------------------------------------------------
		System.out.println("-------------finished----------------------\n");
		
		//RucCurveUtil.createCurve(eTest2,cModel,0 );
		
		
		
	}

}
