import java.util.ArrayList;

import com.mysql.jdbc.Statement;

import weka.classifiers.evaluation.Evaluation;
import weka.experiment.InstanceQuery;


public  class Helpers {

	
	public static void printTestResultsNums(weka.classifiers.Evaluation eTest) {
		
	}
	
	public static int getClassIdFromClassIndex(int classIndex,ArrayList<String> classList)
	{
		return Integer.parseInt(classList.get(classIndex).trim());
		
	}
	
	public static void saveMetricsToDatabase(weka.classifiers.Evaluation trainingTest
			,weka.classifiers.Evaluation testingTest,String ClassifierName,
			long elapsedTrainingTime, double MeanClassificationTime,
			int previousTextId,int DATA_ID,InstanceQuery query,ArrayList<String> classList) throws Exception {

	/*	0		irrelevant		10       
		1		Sports		0
		2		Politics		2
		3		Music		1
		4		news		3
		
		    πρέπει οι κλάσεις στη βάση να έχουν αυτή τη σειρά 
		    αν γιατι τα ids ειναι τα class indexes στους πινακεσ
		    και αν αυτά αλλαξουν θα πρέπει να αλλαχτουν και στους πίνακες της 
		    βάσης*/

		
		
		
			
		
			 String strg="INSERT INTO experiment(Id,ExperimentDescr, ExperimentDataId,ClassifierType,TrainingTime,ClassificationTime) VALUES ("+(previousTextId)+",'Test"+(previousTextId)+"',"+DATA_ID+",'"+ClassifierName+"',"+elapsedTrainingTime+","+MeanClassificationTime+")";
				System.out.println(strg); 
				query.update(strg);
			 
				
				
			for (int i=0;i<trainingTest.confusionMatrix().length;i++){
			
					 strg="INSERT INTO trainingstats(Id,ExperId, Class_Id, falsePosit,falseNegat,recall, `precision`,fmeasure) VALUES ("+0 +","+(previousTextId)+","+(getClassIdFromClassIndex(i,classList))+","+trainingTest.falsePositiveRate(i)+","+trainingTest.falseNegativeRate(i)+","+ trainingTest.recall(i)+","+trainingTest.precision(i)+","+trainingTest.fMeasure(i)+")";
					System.out.println(strg); 
					query.update(strg);
				
					 strg="INSERT INTO trainingconfusionmatrix(Id,ExperimentId, ClassId,TP, TN, FP,FN) VALUES ("+0  +","+(previousTextId)+","+((getClassIdFromClassIndex(i,classList)))+","+trainingTest.numTruePositives(i)+","+trainingTest.numTrueNegatives(i)+","+ +trainingTest.numFalsePositives(i)+","+trainingTest.numFalseNegatives(i)+")";	
					 query.update(strg);	
					 
				 strg="INSERT INTO testingstats(Id,ExperId, Class_Id, falsePosit,falseNegat,recall, `precision`,fmeasure) VALUES ("+0 +","+(previousTextId)+","+((getClassIdFromClassIndex(i,classList)))+","+testingTest.falsePositiveRate(i)+","+testingTest.falseNegativeRate(i)+","+ testingTest.recall(i)+","+testingTest.precision(i)+","+testingTest.fMeasure(i)+")";
				 query.update(strg);
				 
				 strg="INSERT INTO testingconfusionmatrix(Id,ExperimentId, ClassId,TP, TN, FP,FN) VALUES ("+0  +","+(previousTextId)+","+(i)+","+testingTest.numTruePositives((getClassIdFromClassIndex(i,classList)))+","+testingTest.numTrueNegatives(i)+","+ +testingTest.numFalsePositives(i)+","+testingTest.numFalseNegatives(i)+")";			
				System.out.println(strg); 
				query.update(strg);
				}
				
	}
	
	public static void printTestResults(weka.classifiers.Evaluation eTest) {
		
		System.out.println("false negative No class " + eTest.getMetricsToDisplay());
		
		System.out.println("false negative No class " + eTest.falseNegativeRate(0));
		System.out.println("false negative sports class " + eTest.falseNegativeRate(1));
		System.out.println("false negative politics class " + eTest.falseNegativeRate(2));
		System.out.println("false negative music class " + eTest.falseNegativeRate(3));
	
		
		System.out.println("false negative news class " + eTest.falseNegativeRate(4)+"\n");
		
		
		System.out.println("false positive No class " + eTest.falsePositiveRate(0));
		System.out.println("false positive sports class " + eTest.falsePositiveRate(1));
		System.out.println("false positive politics class " + eTest.falsePositiveRate(2));
		System.out.println("false positive music class " + eTest.falsePositiveRate(3));
		System.out.println("false positive news class " + eTest.falsePositiveRate(4)+"\n");
		
		
		System.out.println(" recall No class " + eTest.recall(0));
		System.out.println(" recall sports class " + eTest.recall(1));
		System.out.println(" recall politics class " + eTest.recall(2));
		System.out.println(" recall music class " + eTest.recall(3));
		System.out.println(" recall news class " + eTest.recall(4)+"\n");
		
		
		System.out.println(" precision No class " + eTest.precision(0));
		System.out.println(" precision sports class " + eTest.precision(1));
		System.out.println(" precision politics class " + eTest.precision(2));
		System.out.println(" precision music class " + eTest.precision(3));
		System.out.println(" precision news class " + eTest.precision(4)+"\n");
		

		System.out.println(" fmeasure No class " + eTest.fMeasure(0));
		System.out.println(" fmeasure sports class " + eTest.fMeasure(1));
		System.out.println(" fmeasure politics class " + eTest.fMeasure(2));
		System.out.println(" fmeasure music class " + eTest.fMeasure(3));
		System.out.println(" fmeasure news class " + eTest.fMeasure(4)+"\n");
		
		
		
		System.out.println("-----------------");
		System.out.println("-----------------");
	}
}
