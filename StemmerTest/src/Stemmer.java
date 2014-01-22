import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

import org.apache.lucene.analysis.el.GreekStemmer;
import org.tartarus.snowball.ext.PorterStemmer;

import java.awt.List;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.interfaces.DSAKey;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.lucene.analysis.el.GreekStemmer;
public class Stemmer {
	static String INPUT_PATH="C:\\Users\\Kwstas\\Desktop\\wekaTest\\topallinOut.arff";
	static String OUPUT_PATH="C:\\Users\\Kwstas\\Desktop\\wekaTest\\topallinOutStemmed.arff";
	/**
	 * 
	 * @param args
	 * @throws IOException 
	 * 
	 * 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
	
	
		
		long start = System.nanoTime();    
		
		
	    // we need to store all the lines
		  ArrayList<String>lines = new ArrayList<String>();
		  
		    // first, read the file and store the changes
		   // BufferedReader in = new BufferedReader(new FileReader( INPUT_PATH));
		  BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(INPUT_PATH),"UTF-8"));  
		  //  BufferedWriter log  = new BufferedWriter(new FileWriter( "C:\\Users\\Kwstas\\Desktop\\gritzal\\log.txt"));
		    File logFile = new File("logPost.txt");
		    
		    String line = in.readLine();
		    
		    GreekStemmer stemmer=new GreekStemmer();
	    int c=0;
	    while (line!=null) {
	    	 Scanner scanner=new Scanner(line);
	    
	      while(scanner.hasNext())
	      {
	    	  String currentString=scanner.next();
	    	  char[] token=currentString.toCharArray();
	  		int l = stemmer.stem(token, token.length);
	  	
	  	   String   stem = new String(token, 0, l);
	  	   
	  	 line=line.replace(currentString, stem);
	
	      }
	    
	    lines.add(line);
	    line = in.readLine(); 
	    }
	    in.close();
	 
	    // now, write the file again with the changes
	    FileOutputStream fout = new FileOutputStream( new File( OUPUT_PATH ) );
	    PrintWriter out = new PrintWriter(new OutputStreamWriter(fout, "UTF-8"));
	    for (String l : lines){
	        out.println(l);}
	    out.close();
	    
	    
	    long elapsedTime = System.nanoTime() - start;
	   
		
  }
}	
	