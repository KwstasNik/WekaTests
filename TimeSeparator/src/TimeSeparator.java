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
			
			Instances filteredDataPosts;
			Instances filteredDataComments;
			
			try {
				
				ClassifierHelper cHelper=new ClassifierHelper();
				cHelper.createFilter();
				cHelper.createClassifier();
				Filter filt=cHelper.getFilter();
				Classifier cl=cHelper.getClassifier();
				//filteredData = ch.loadDataFromDb(filt);
			//	filteredData=ch.loadFromDatasource(filt,"C:\\Users\\Kwstas\\Desktop\\wekaTest\\musicOnlyStemmed.arff");
			filteredDataPosts=ch.loadFromPostDatasource(filt,"C:\\Users\\Kwstas\\Desktop\\wekaTest\\trainingAllWithIds.arff");
			filteredDataComments=ch.loadFromCommentDatasource(filt,"C:\\Users\\Kwstas\\Desktop\\wekaTest\\trainingComments.arff");
			
			ArrayList<UserInfo> uiArrayList=ch.GenerateClassificationData(filteredDataPosts,filteredDataComments, "2009-1-1", "2013-12-30", 7,cl);
			OutputHelper outputHelper=new OutputHelper("C:\\Users\\Kwstas\\Desktop\\wekaTest\\calendarV2.txt");
			outputHelper.createCalendarDoc(uiArrayList);
			outputHelper.createXML(uiArrayList, "C:\\Users\\Kwstas\\Desktop\\wekaTest\\UsersInfoV2.xml");
			
		
			
			
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		System.out.println("Done");
	}

}
