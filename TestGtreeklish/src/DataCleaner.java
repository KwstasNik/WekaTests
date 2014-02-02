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
import java.nio.channels.SelectableChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.axis.Version;
import org.apache.lucene.analysis.el.GreekAnalyzer;
import org.apache.lucene.analysis.util.CharArraySet;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;



public class DataCleaner {

	/**
	 * @param args
	 */
	// static String INPUT_PATH="C:\\Users\\Kwstas\\DropboxV2\\Dropbox\\gritzal\\postExtra80k_120k.sql";
	static String INPUT_PATH="C:\\Users\\Kwstas\\Desktop\\wekaTest\\training33456.arff";
	
//	static String OUPUT_PATH="C:\\Users\\Kwstas\\DropboxV2\\Dropbox\\gritzal\\postExtra80k_120kClean.sql";
	static String OUPUT_PATH="C:\\Users\\Kwstas\\Desktop\\wekaTest\\training33456Clean.arff";
	
	static String splitSeqString="}+";
	public static String documentType="ARFF";
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	
	//	String description = doc.select("meta[name=description]").get(0).attr("content");
	//	System.out.println("title:"+title+" description: "+description)
		
		//String regex = "\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
	
	/*for (int i=0;i<2;i++){	
		if(i==1)
		{
			 INPUT_PATH="C:\\Users\\Kwstas\\DropboxV2\\Dropbox\\thesis drafts\\post.sql_out.sql";
			 OUPUT_PATH="C:\\Users\\Kwstas\\DropboxV2\\Dropbox\\thesis drafts\\post.sql_outGreekClean.sql";
		}*/
		try {
			readLargerTextFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	//}
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
		    StringBuffer packedString=new StringBuffer();
		    ArrayList<TempLine> totalStrLst=new ArrayList<TempLine>();
		    ArrayList<TempLine> StrLst=new ArrayList<TempLine>();
		    ArrayList<String> newStrLstList=new ArrayList<String>();
		 	DocumentHepler documentHepler=new DocumentHepler(documentType);
			String nextLine;
			  
		    while (line != null  ) {
		    
		    	nextLine = in.readLine();
		    	
		    	//phase1
		     	
		    	
		     if (documentHepler.isProperLine(line)){
		    	 TempLine tmpLine=new TempLine();
		    	 
		    	int mesgStart=documentHepler.getPrevIndex(line);
		    	int  mesgStop=documentHepler.getNextIndex(line);
		   	tmpLine.setPrev(line.substring(0, mesgStart));
		  
		 	 
			   	tmpLine.setNext(line.substring(mesgStop));
			
			// System.out.println(line);
			 String original=line.substring(mesgStart, mesgStop).toString();
		     
		     original=replaceUriWithTitle(original,writer,c);
			  
		     
			 
			 StrLst.add(tmpLine);
				
		     
		     String cleanStringreek=null;
			 
			 if(packedString.length()<4500&&nextLine!=null)
			 {
				
				 packedString.append(original);
				 packedString.append(splitSeqString);
			 }
			 else{
				 if (nextLine==null)
				 {
					 packedString.append(original);
				 }
				 c=StrLst.size()+c;
		    	//if(IsMatch(line, regexpattern)){
		        	
		        	System.out.println(c);
		      //      String sValue = line.substring(line.indexOf("http"),line.indexOf("'", line.indexOf("http")));
		        	try {
		        		String inString=packedString.toString();
		        				inString=inString.replaceAll("(.)\\1+", "$1");
		        				inString=clearSpecial(inString);
		        				//inString="kalimera";
		        				
						String greekOriginal = Parser.getGreek(inString);
						packedString=new StringBuffer();
							  cleanStringreek=clearSpecial(greekOriginal);
							  cleanStringreek=cleanStopWords(cleanStringreek);
							  cleanStringreek=cleanNumbers(cleanStringreek);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		        	newStrLstList=SplitString(cleanStringreek);
		        int i=0;
		        	for (TempLine orgString : StrLst) {
		        		
						/*if(c==4582)
						{
						int sz=	StrLst.size();
							int y=0;
							y++;
						}*/
					
							try{
								if(i<newStrLstList.size()){
		        		orgString.setReplace(newStrLstList.get(i));
								}
						}
						catch(Exception e)
						{
							
							e.printStackTrace();
						}
		           i++;
		        	}
		        	totalStrLst.addAll(StrLst);
		        	StrLst.clear();
		        //this sequence[, '',] means the Line Has no data,
		        //so it must be removed
		       // if (!line.contains(", '',")){
		      //  lines.add(line);
		      //  }
			 }
		     
		     }
		    	
		    	
		      
		    
		     
		     line=nextLine;
		     }
		    in.close();
		 
		    // now, write the file again with the changes
		    FileOutputStream fout = new FileOutputStream( new File( OUPUT_PATH ) );
		    PrintWriter out = new PrintWriter(new OutputStreamWriter(fout, "UTF-8"));
		    for (TempLine l : totalStrLst){
		    String replString=l.getReplace();
		    if (replString!=null)
		    {
			if(replString.contains("}")){ replString = replString.replace("}", " "); }
    		if(replString.contains("+")){ replString = replString.replace("+", " "); }
		    	if(!replString.trim().isEmpty()){
		    		
		    	
		        out.println(l.getPrev()+replString+l.getNext());
		    	}
		    }
		    	}
		    
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
	 
	  
	  
	  public static ArrayList<String> SplitString( String input) {
		  
		  ArrayList<String> entryList=new ArrayList<String>();
		int splitIndex=  input.indexOf(splitSeqString);
		while (splitIndex!=-1){
			
			entryList.add(input.substring(0,splitIndex));
			input=input.substring(splitIndex+splitSeqString.length());
		 splitIndex=  input.indexOf(splitSeqString);
		 
		 }
		entryList.add(input);
		return entryList;
		  
	}
	  
	
	  public static String replaceUriWithTitle(String line,BufferedWriter writer,int c) throws IOException
	  {
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
		  return line;
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
		

private static String clearSpecial(String string) {
	
	
		if(string.contains("!")){ string = string.replace("!", " "); }
		if(string.contains("'")){ string = string.replace("'", " "); }
    	if(string.contains(".")){ string = string.replace(".", " "); }
    	if(string.contains("/")){ string = string.replace("/", " "); }
    	if(string.contains("\n")){ string = string.replace("\n", " "); }
    	if(string.contains("\t")){ string = string.replace("\t", " "); }
    	if(string.contains("*")){ string = string.replace("*", " "); }
    	if(string.contains("$")){ string = string.replace("$", " "); }
    	if(string.contains("^")){ string = string.replace("^", " "); }
    
    	if(string.contains("-")){ string = string.replace("-", " "); }
    	if(string.contains("=")){ string = string.replace("=", " "); }
    	if(string.contains("%")){ string = string.replace("%", " "); }
    	if(string.contains("#")){ string = string.replace("#", " "); }
    	if(string.contains("~")){ string = string.replace("~", " "); }
    	//if(string.contains("(")){ string = string.replace("(", " ("); }
    	//if(string.contains(")")){ string = string.replace(")", ") "); }
    	if(string.contains("[")){ string = string.replace("[", " "); }
    	//if(string.contains("]")){ string = string.replace("]", " "); }
    	if(string.contains("\"")){ string = string.replace("\"", " "); }
    	if(string.contains("\\ç")){ string = string.replace("\\ç", " "); }
    	if(string.contains("\\ô")){ string = string.replace("\\ô", " "); }
    	if(string.contains(";")){ string = string.replace(";", " "); }
    	if(string.contains("?")){ string = string.replace("?", " "); }
    	if(string.contains("<")){ string = string.replace("<", " "); }
    	if(string.contains(">")){ string = string.replace(">", " "); }
    	if(string.contains(",")){ string = string.replace(",", " "); }
    	if(string.contains("´")){ string = string.replace("´", " "); }
    	if(string.contains("«")){ string = string.replace("«", " "); }
    	if(string.contains("»")){ string = string.replace("»", " "); }
    	if(string.contains("?")){ string = string.replace("?", " "); }
    	if(string.contains(":")){ string = string.replace(":", " "); }
    	if(string.contains("ò")){ string = string.replace("ò", "ó"); }
        
    	if(string.contains("Ü")){ string = string.replace("Ü", "á"); }
    	if(string.contains("Ý")){ string = string.replace("Ý", "å"); }
    	if(string.contains("ü")){ string = string.replace("ü", "ï"); }
    	if(string.contains("ß")){ string = string.replace("ß", "é"); }
    	if(string.contains("Þ")){ string = string.replace("Þ", "ç"); }
    	if(string.contains("ý")){ string = string.replace("ý", "õ"); }
    	if(string.contains("þ")){ string = string.replace("þ", "ù"); }
    	if(string.contains("ú")){ string = string.replace("ú", "é"); }
    	if(string.contains("À")){ string = string.replace("À", "é"); }
    	if(string.contains("û")){ string = string.replace("û", "õ"); }
    	if(string.contains("à")){ string = string.replace("à", "õ"); }
    	
    	if(string.contains("”")){ string = string.replace("”", " "); }
    	if(string.contains("“")){ string = string.replace("“", " "); }
    	if(string.contains("…")){ string = string.replace("…", " "); }
        
    	
    
    	
   
    	return string.toLowerCase();
	}
	

private static String cleanStopWords(String string)
{GreekAnalyzer da=new GreekAnalyzer(null);
 CharArraySet stopSet= GreekAnalyzer.getDefaultStopSet();
 
 for (Object stopwrd : stopSet) {
	String stoW=new String(((char[])stopwrd));
	 stoW=" "+stoW+" ";
		if(string.contains(stoW))
		{ 
			string = string.replace(stoW," ");
			}
	    
	 
}
 
	return string;
}


private static String cleanNumbers (String string)
{
	  
	if(string.contains("0")){ string = string.replace("0", " "); }
	if(string.contains("1")){ string = string.replace("1", " "); }
	if(string.contains("2")){ string = string.replace("2", " "); }
	if(string.contains("3")){ string = string.replace("3", " "); }
	if(string.contains("4")){ string = string.replace("4", " "); }
	if(string.contains("5")){ string = string.replace("5", " "); }
	if(string.contains("6")){ string = string.replace("6", " "); }
	if(string.contains("7")){ string = string.replace("7", " "); }
	if(string.contains("8")){ string = string.replace("8", " "); }
	if(string.contains("9")){ string = string.replace("9", " "); }
	
	
	return string;
}

}


 class TempLine
{
	 private String Prev;
	 
	 public String getPrev() {
			return Prev;
		}

		public void setPrev(String Prev) {
			this.Prev = Prev;
		}
	 
	 private String Next;
	 
	 public String getNext() {
			return Next;
		}

		public void setNext(String Next) {
			this.Next = Next;
		}
		
	public String getReplace() {
		return replace;
	}

	public void setReplace(String replace) {
		this.replace = replace;
	}

	
	private String replace;
	
	
	
	public TempLine()
	{
		
	}
}
