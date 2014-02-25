import java.io.ObjectInputStream.GetField;
import java.util.Scanner;

import org.apache.lucene.analysis.el.GreekAnalyzer;
import org.apache.lucene.analysis.el.GreekStemmer;
import org.apache.lucene.analysis.util.CharArraySet;

import weka.core.stemmers.Stemmer;


public class StemmerHelper {

	GreekStemmer stemmer;
	public StemmerHelper()
	{
		   stemmer=new GreekStemmer();
	}
	
	public String getStemmedLine(String line)
	{
	
		 Scanner scanner=new Scanner(line);
		    
	      while(scanner.hasNext())
	      {
	    	   String currentString=scanner.next();
	    	  char[] token=currentString.toCharArray();
	  		int l = stemmer.stem(token, token.length);
      CharArraySet hString= GreekAnalyzer.getDefaultStopSet();
	  	   String   stem = new String(token, 0, l);
	  	   
	  	 line=line.replace(currentString, stem);
	
	      }
	      return line;
	}
}
