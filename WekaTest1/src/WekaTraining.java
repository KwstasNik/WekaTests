

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
		
		int DATA_ID=1;
	//	String METHOD="NaiveBayesMultinomial";
	//	String METHOD="SMO";
		String METHOD="NaiveBayes";
	Boolean saveToDataBase =false;
	 InstanceQuery query=null;	
	int previousTextId=0;
	if(saveToDataBase){
		  query = new InstanceQuery();
		 query.setUsername("root");
		 query.setPassword("");
		 query.setQuery("select * from post");
		 // You can declare that your data set is sparse
		 // query.setSparseData(true);
		 Instances data = query.retrieveInstances();
		//System.out.println("Instance"+data.toString());
			
		 if (!data.isEmpty()){
		 previousTextId=(int)data.get(data.size()-1).value(0);
		 }
	}
		 
		
		
		String ClassifierName=METHOD;
		
		//DataSource sourceTrain = new DataSource("trainingSet.arff");
		
		//Instances train = null;
		train = sourceTrain.getDataSet();
		
		if (train.classIndex() == -1)
			train.setClassIndex(train.numAttributes() - 1);

	
		StringToWordVector filter = new StringToWordVector();
		filter.setOptions(
			      weka.core.Utils.splitOptions("-R first-last -W 100000 " +
			                                "-prune-rate -1.0 -T -I  -N 0 -stemmer " +
			                                           "weka.core.stemmers.NullStemmer -M 1 " +
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
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ClassifierName+".model"+"Test"+(previousTextId+1)));
		oos.writeObject(cModel);
		oos.flush();
		oos.close();
		
		//save filter
		ObjectOutputStream oos1 = new ObjectOutputStream(new FileOutputStream(ClassifierName+"filter"+"Test"+(previousTextId+1)));
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
			
			System.out.println("false positive -10 class " + eTest.falseNegativeRate(0));
			System.out.println("false positive 0 class " + eTest.falseNegativeRate(1));
			System.out.println("false positive 10 class " + eTest.falseNegativeRate(2) + "\n");
			
			System.out.println("false negative -10 class " + eTest.falsePositiveRate(0));
			System.out.println("false negative 0 class " + eTest.falsePositiveRate(1));
			System.out.println("false negative 10 class " + eTest.falsePositiveRate(2) + "\n");
			
			System.out.println("recall -10 class " + eTest.recall(0));
			System.out.println("recall 0 class " + eTest.recall(1));
			System.out.println("recall 10 class " + eTest.recall(2) + "\n");
			
			System.out.println("precision -10 class " + eTest.precision(0));
			System.out.println("precision 0 class " + eTest.precision(1));
			System.out.println("precision 10 class " + eTest.precision(2) + "\n");

			System.out.println("fmeasure -10 class " + eTest.fMeasure(0));
			System.out.println("fmeasure 0 class " + eTest.fMeasure(1));
			System.out.println("fmeasure 10 class " + eTest.fMeasure(2));
			
			System.out.println("-----------------");
			System.out.println("-----------------");
			}
			 // Get a statement from the connection
		    
		      // Execute the query
		      /*Connection conn = (Connection)DriverManager.getConnection("jdbc:mysql://localhost:3306/wekatest", "root", "");
		      Statement stmt = (Statement) conn.createStatement() ;
String querString="INSERT INTO 'wekatest'.'trainingstats' ( 'ExperId', 'Class_Id', 'falsePosit', 'falseNegat', 'recall', 'precision', 'fmeasure')" +
 		" VALUES ('"+1+"', '"+1+"', '"+eTest.falseNegativeRate(0)+"', '"+eTest.falsePositiveRate(0)+"', '"+ eTest.recall(0)+"', '"+eTest.precision(0)+"', '"+ eTest.fMeasure(0)+"')";
System.out.println(querString);		    
int rs = stmt.executeUpdate(querString);*/
		

			 
			
		
			 
				 
/////////////////////////////////////////////////////////////////////////////////////////////
		// test NB with test set
		System.out.println("classifier "+ClassifierName+" test set");
		DataSource source1 = new DataSource("testSet.arff");
		Instances test = null;
		test = source1.getDataSet();
		if (test.classIndex() == -1) {
			test.setClassIndex(test.numAttributes() - 1);
		}

		Filter filter2 = (Filter) weka.core.SerializationHelper.read(ClassifierName+"filter"+"Test"+(previousTextId+1));
		Instances newTest1 = Filter.useFilter(test, filter2); 
															
		Classifier cModel1 = (Classifier) weka.core.SerializationHelper.read(ClassifierName+".model"+"Test"+(previousTextId+1));

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
		
		if(saveToDataBase){
		 String strg="INSERT INTO experiment(Id,ExperimentDescr, ExperimentDataId,ClassifierType,TrainingTime,ClassificationTime) VALUES ("+(previousTextId+1)+",'Test"+(previousTextId+1)+"',"+DATA_ID+",'"+ClassifierName+"',"+elapsedTrainingTime+","+MeanClassificationTime+")";
			System.out.println(strg); 
			query.update(strg);
		 
			
			
		for (int i=0;i<3;i++){
		
				 strg="INSERT INTO trainingstats(Id,ExperId, Class_Id, falsePosit,falseNegat,recall, `precision`,fmeasure) VALUES (Null,"+(previousTextId+1)+","+(i+1)+","+eTest.falsePositiveRate(i)+","+eTest.falseNegativeRate(i)+","+ eTest.recall(i)+","+eTest.precision(i)+","+eTest.fMeasure(i)+")";
				System.out.println(strg); 
				query.update(strg);
			
					
			 strg="INSERT INTO testingstats(Id,ExperId, Class_Id, falsePosit,falseNegat,recall, `precision`,fmeasure) VALUES (Null,"+(previousTextId+1)+","+(i+1)+","+eTest2.falsePositiveRate(i)+","+eTest2.falseNegativeRate(i)+","+ eTest2.recall(i)+","+eTest2.precision(i)+","+eTest2.fMeasure(i)+")";
			System.out.println(strg); 
			query.update(strg);
			}
			}
		if(!saveToDataBase){
		System.out.println("different values " + counter1);
		System.out.println("false positive -10 class " + eTest2.falseNegativeRate(0));
		System.out.println("false positive 0 class " + eTest2.falseNegativeRate(1));
		System.out.println("false positive 10 class " + eTest2.falseNegativeRate(2) + "\n");
		
		System.out.println("false negative -10 class " + eTest2.falsePositiveRate(0));
		System.out.println("false negative 0 class " + eTest2.falsePositiveRate(1));
		System.out.println("false negative 10 class " + eTest2.falsePositiveRate(2) + "\n");
		
		System.out.println("recall -10 class " + eTest2.recall(0));
		System.out.println("recall 0 class " + eTest2.recall(1));
		System.out.println("recall 10 class " + eTest2.recall(2) + "\n");
		
		System.out.println("precision -10 class " + eTest2.precision(0));
		System.out.println("precision 0 class " + eTest2.precision(1));
		System.out.println("precision 10 class " + eTest2.precision(2) + "\n");

		System.out.println("fmeasure -10 class " + eTest2.fMeasure(0));
		System.out.println("fmeasure 0 class " + eTest2.fMeasure(1));
		System.out.println("fmeasure 10 class " + eTest2.fMeasure(2));
		
		System.out.println("-----------------------------------------------------\n");
	
	//--------------------------------------------------------------------------
	//-------------------------------------------------------------------------
		
	
	
	//------------------------------------------------------------------------
		System.out.println("-------------finished----------------------\n");
		
		RucCurveUtil.createCurve(eTest2,cModel,0 );
		}
		
		
	}

}
