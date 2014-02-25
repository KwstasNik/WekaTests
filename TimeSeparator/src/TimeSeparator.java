import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;

import com.thoughtworks.xstream.XStream;

import weka.classifiers.Classifier;
import weka.core.Instances;
import weka.filters.Filter;


public class TimeSeparator {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
			ClassHelper ch=new ClassHelper();
			
			Instances filteredData;
			Instances filteredDataComments;
			
			try {
				
				ClassifierHelper cHelper=new ClassifierHelper();
				cHelper.createFilter();
				cHelper.createClassifier();
				Filter filt=cHelper.getFilter();
				Classifier cl=cHelper.getClassifier();
				//filteredData = ch.loadDataFromDb(filt);
			//	filteredData=ch.loadFromDatasource(filt,"C:\\Users\\Kwstas\\Desktop\\wekaTest\\musicOnlyStemmed.arff");
			filteredData=ch.loadFromPostDatasource(filt,"C:\\Users\\Kwstas\\Desktop\\wekaTest\\trainingAllTeststem.arff");
			filteredDataComments=ch.loadFromPostDatasource(filt,"C:\\Users\\Kwstas\\Desktop\\wekaTest\\trainingAllTeststem.arff");
			
			ArrayList<UserInfo> uiArrayList=ch.GenerateClassificationData(filteredData,filteredDataComments, "2009-1-1", "2013-12-30", 7,cl);
			OutputHelper outputHelper=new OutputHelper("C:\\Users\\Kwstas\\Desktop\\wekaTest\\calendar.txt");
			outputHelper.createCalendarDoc(uiArrayList);
			outputHelper.createXML(uiArrayList, "C:\\Users\\Kwstas\\Desktop\\wekaTest\\UsersInfo.xml");
			
		
			
			
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		System.out.println("Done");
	}

}
