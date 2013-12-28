

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.Timer;

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
import weka.filters.unsupervised.attribute.StringToWordVector;
import weka.filters.unsupervised.attribute.SortLabels.CaseInsensitiveComparator;
import weka.gui.treevisualizer.TreeBuild;
import weka.classifiers.trees.J48; 
import weka.core.Instances;
import weka.experiment.InstanceQuery;

public class WekaTraining {


	public static void main(String[] args) throws Exception {
		//VARIABLES
		int DATA_ID=1;
	//	String METHOD="NaiveBayesMultinomial" Cannot handle multi-valued nominal class;
	//	String METHOD="SMO";
		String METHOD="NaiveBayes";
	//	String METHOD="J48";
	//	String METHOD="LinearRegression" Cannot handle multi-valued nominal class;
		
		String QUERY_LEARN="SELECT message,class FROM  postclassified1000 ";
	//String QUERY_LEARN="SELECT message,class FROM  postclassified1000 WHERE class !=  ''";
		String QUERY_TEST="select  message,class from post100Testing";
	//	String QUERY_TEST="select  message,class from post100Testing WHERE class !=  ''";
	
		 Instances data=null;
	Boolean saveToDataBase =false;
	 InstanceQuery query=null;	
//End Variables

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
		Instances train=data;
		if (train.classIndex() == -1)
			train.setClassIndex(train.numAttributes() - 1);

	
		StringToWordVector filter = new StringToWordVector();
		filter.setOptions(
			      weka.core.Utils.splitOptions("-R first-last -W 10000 " +
			                                "-prune-rate -1.0 -T -I  -N 0 -stemmer " +
			                                          // "weka.core.stemmers.NullStemmer
			                                "-M 1 " +
			                                          "-tokenizer \"weka.core.tokenizers.WordTokenizer \"" +
			                                          "-delimiters \" \\r\\n\\t.,;:\\\'\\\"()?!\""));
//    -R first-last -W 100000 -prune-rate -1.0 -T -I -N 0 -L -stemmer weka.core.stemmers.NullStemmer -M 1 -tokenizer "weka.core.tokenizers.WordTokenizer -delimiters \" \\r\\n\\t.,;:\\\'\\\"()?!\""	
	
		 filter.setInputFormat(train);  // initializing the filter once with training set
		 Instances newTrain = Filter.useFilter(train, filter);  // configures the Filter based on train instances and returns filtered instances
		for (int i=0;i<10;i++){
		 System.out.println(newTrain.get(i));
		}
		 Classifier cModel=null;
		 RegSMO reg=null;
		 
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
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ClassifierName+".model"+"Test"));
		oos.writeObject(cModel);
		oos.flush();
		oos.close();
		
		//save filter
		ObjectOutputStream oos1 = new ObjectOutputStream(new FileOutputStream(ClassifierName+"filter"+"Test"));
		oos1.writeObject(filter);
		oos1.flush();
		oos1.close();

		System.out.println("-----\nclassifier testing using training set\n-----");
		System.out.println("-----------------------------------\nclassifier" +ClassifierName);
		 // Test the model
		 Evaluation eTest = new Evaluation(newTrain);
		 eTest.evaluateModel(cModel, newTrain);

		 String strSummary = eTest.toSummaryString();
		 System.out.println(strSummary);
		 
		 // Get the confusion matrix
		 double[][] cmMatrix = eTest.confusionMatrix();
		 
		 System.out.println("Eval instances: " + eTest.numInstances());
			int counter = 0;
			for (int i = 0; i < eTest.numInstances(); i++) {
				double pred = cModel.classifyInstance(newTrain.instance(i));
				if (newTrain.instance(i).classAttribute().value((int) newTrain.instance(i).classValue()).equals(newTrain.instance(i).classAttribute().value((int) pred))) {
				} else {
					counter++;
				}
			}
			
			if(!saveToDataBase){
			System.out.println("different values " + counter);
			try {
				System.out.println( eTest.toClassDetailsString());
				System.out.println(eTest.toMatrixString());
				
			
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
		
		if (test.classIndex() == -1) {
			test.setClassIndex(test.numAttributes() - 1);
		}

		Filter filter2 = (Filter) weka.core.SerializationHelper.read(ClassifierName+"filter"+"Test");
		Instances newTest1 = Filter.useFilter(test, filter2); 
															
		Classifier cModel1 = (Classifier) weka.core.SerializationHelper.read(ClassifierName+".model"+"Test");

		// Test the model
		Evaluation eTest2 = new Evaluation(newTest1);
		eTest2.evaluateModel(cModel1, newTest1);
		String strSummary2 = eTest2.toSummaryString();
		System.out.println(strSummary2);

		// Get the confusion matrix
		double[][] cmMatrix2 = eTest2.confusionMatrix();

		System.out.println("Eval instances: " + eTest2.numInstances());
		int counter1 = 0;
		double MeanClassificationTime=0;
		int ClassificationTime=0;
		for (int i = 0; i < eTest2.numInstances(); i++) {
			
			 start = System.nanoTime();  
			double pred = cModel1.classifyInstance(newTest1.instance(i));
			long time= System.nanoTime()-start;
			ClassificationTime+=time;
			if (newTest1.instance(i).classAttribute().value((int) newTest1.instance(i).classValue()).equals(newTest1.instance(i).classAttribute().value((int) pred))) {
			} else {
				counter1++;
			}
		}
		MeanClassificationTime=(ClassificationTime/ eTest2.numInstances());
		
		
		
			try {
				System.out.println(eTest2.toClassDetailsString());
				System.out.println(eTest2.toMatrixString());
				
				
				
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		
	
	
	//------------------------------------------------------------------------
		System.out.println("-------------finished----------------------\n");
		
		//RucCurveUtil.createCurve(eTest2,cModel,0 );
		
		
		
	}

}
