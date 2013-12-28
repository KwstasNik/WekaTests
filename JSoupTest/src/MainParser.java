import java.awt.List;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


public class MainParser {

	/**
	 * @param args
	 */
	static String INPUT_PATH="C:\\Users\\Kwstas\\Desktop\\gritzal\\post.sql.txt";
	static String OUPUT_PATH="C:\\Users\\Kwstas\\Desktop\\gritzal\\post.sql_out.sql.txt";
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	
	//	String description = doc.select("meta[name=description]").get(0).attr("content");
	//	System.out.println("title:"+title+" description: "+description)
		
		//String regex = "\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
		try {
			readLargerTextFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	  
	public  static void readLargerTextFile() throws IOException {
		long start = System.nanoTime();    
		
	
		    // we need to store all the lines
		  ArrayList<String>lines = new ArrayList<String>();
		  
		    // first, read the file and store the changes
		   // BufferedReader in = new BufferedReader(new FileReader( INPUT_PATH));
		  BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(INPUT_PATH),"UTF-8"));  
		  //  BufferedWriter log  = new BufferedWriter(new FileWriter( "C:\\Users\\Kwstas\\Desktop\\gritzal\\log.txt"));
		    File logFile = new File("logPost.txt");
		    BufferedWriter writer = new BufferedWriter(new FileWriter(logFile));
		    String line = in.readLine();
		    int c=0;
		    while (line != null ) {
		        if (line.contains("http")) {
		    	//if(IsMatch(line, regexpattern)){
		        	 c++;
		      //      String sValue = line.substring(line.indexOf("http"),line.indexOf("'", line.indexOf("http")));
		          ArrayList<String> links=  returnLinks(line);
		            for (String sValue : links) {
						
		            	if(!sValue.contains("http"))
		            	{
		            		sValue="http://"+sValue;
		            	}
		            String replacedUri=replaceUri(sValue,writer,c);
		            try{
		            line =line.replace(sValue,replacedUri);
		            }
		            catch(Exception E)     
		            {
		            	writer.write(c+".REPLACE ERROR "+sValue+"--->"+replacedUri);
		            }
		            writer.write(c+". "+sValue+"--->"+replacedUri);
		            writer.newLine();
		            }
		        }
		        //this sequence[, '',] means the Line Has no data,
		        //so it must be removed
		        if (!line.contains(", '',")){
		        lines.add(line);
		        }
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
		    writer.write("process Duration in nanosecs:"+elapsedTime);
		    writer.close();
			
	  }
	  
	  public static String replaceUri(String uri,BufferedWriter writer,int counter) throws IOException
	  {
			Document doc= null;
		// doc=	new Document("C:\\Users\\Kwstas\\Desktop\\gritzal\\comment.sql.txt");
	if(!uri.contains("http"))
	{
		uri="http://"+uri;
	}
	try {
		doc = Jsoup.connect(uri).get();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		//e.printStackTrace();
		writer.write(counter+". "+"failure for  "+uri);
	}
	String title="";
	if (doc!=null){
		title= doc.title();
	}
	return title;
	  }
	  
	  
	private static ArrayList<String> returnLinks(String text)
	{
		 			ArrayList links = new ArrayList();
			 
			String regex = "\\(?\\b(http://|www[.])[-A-Za-z0-9+&@#/%?=~_()|!:,.;]*[-A-Za-z0-9+&@#/%=~_()|]";
			Pattern p = Pattern.compile(regex);
			Matcher m = p.matcher(text);
			while(m.find()) {
			String urlStr = m.group();
			if (urlStr.startsWith("(") && urlStr.endsWith(")"))
			{
			urlStr = urlStr.substring(1, urlStr.length() - 1);
			}
			links.add(urlStr);
			}
			return links;
			}
		
	
}
