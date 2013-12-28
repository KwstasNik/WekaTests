import weka.classifiers.evaluation.Evaluation;


public  class Helpers {

	
	public static void printTestResultsNums(weka.classifiers.Evaluation eTest) {
		
	//for (int i=0;i<eTest.confusionMatrix())	
			
			
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
