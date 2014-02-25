import weka.classifiers.Classifier;
import weka.classifiers.functions.SMO;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.StringToWordVector;


public class ClassifierHelper {
	String DATASOURCE_PATH="C:\\Users\\Kwstas\\Desktop\\wekaTest\\training3_3_10_7Stemmed.arff";
	Instances data;
	Instances newTrain;
	StringToWordVector filter;
 	Classifier cModel;
	public ClassifierHelper()
	{
		
	}
	public void createFilter() throws Exception
	{
		DataSource sourceTrain = new DataSource(DATASOURCE_PATH);
		 data = sourceTrain.getDataSet();
 filter = new StringToWordVector();
/*	filter.setOptions(
	      weka.core.Utils.splitOptions("-R first-last -W 100000 " +
	                                "-prune-rate -1.0 -T -I -N 0 -stemmer " +
	                                           "weka.core.stemmers.NullStemmer -M 1 " +
	                                          "-tokenizer \"weka.core.tokenizers.WordTokenizer \"" +
	                                          "-delimiters \" \\r\\n\\t.,;:\\\'\\\"()?!\""));		*/

filter.setOptions(
	      weka.core.Utils.splitOptions("-R first-last -W 1000 " +
"-prune-rate -1.0 -N 0 -stemmer" +
" weka.core.stemmers.NullStemmer -M 6 -tokenizer " +
"weka.core.tokenizers.WordTokenizer " +
"-delimiters \" \\r\\n\\t.,;:\\\'\\\"()?!\""));

filter.setInputFormat(data);  // initializing the filter once with training set
 newTrain = Filter.useFilter(data, filter);  // configures the Filter based on train instances and returns filtered instances

	}
	
	public  Filter getFilter()
	{
		return filter;
	}
	
	public void createClassifier() throws Exception
	{
		if (newTrain.classIndex() == -1){
			newTrain.setClassIndex(0);
		}
		 
	 	 
   	 cModel=  new SMO();
   	cModel.buildClassifier(newTrain);
   	
}








	public Classifier getClassifier()
	{
		 return cModel;
	}
}
