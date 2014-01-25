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



public class DataCleaner {

	/**
	 * @param args
	 */
	static String INPUT_PATH="C:\\Users\\Kwstas\\DropboxV2\\Dropbox\\thesis drafts\\allin.sql";
	static String OUPUT_PATH="C:\\Users\\Kwstas\\DropboxV2\\Dropbox\\thesis drafts\\allinGreekClean.sql";
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	
	//	String description = doc.select("meta[name=description]").get(0).attr("content");
	//	System.out.println("title:"+title+" description: "+description)
		
		//String regex = "\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
	
	for (int i=0;i<2;i++){	
		if(i==1)
		{
			 INPUT_PATH="C:\\Users\\Kwstas\\DropboxV2\\Dropbox\\thesis drafts\\post.sql_out.sql";
			 OUPUT_PATH="C:\\Users\\Kwstas\\DropboxV2\\Dropbox\\thesis drafts\\post.sql_outGreekClean.sql";
		}
		try {
			readLargerTextFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		    StringBuffer packedString=new StringBuffer();
		    ArrayList<TempLine> totalStrLst=new ArrayList<TempLine>();
		    ArrayList<TempLine> StrLst=new ArrayList<TempLine>();
		    String[] newStrLstList=new String[1000];
			String nextLine;
		    while (line != null  ) {
		    
		    	nextLine = in.readLine();
		     if (line.contains(", '")){
		    	 TempLine tmpLine=new TempLine();
		    	 
		   	 int mesgStart= line.indexOf("', '", line.indexOf("', '")+4)+4;
		   	 
		   	tmpLine.setPrev(line.substring(0, mesgStart));
		  
			 int mesgStop= line.indexOf("', '", mesgStart);
		  	 
			   	tmpLine.setNext(line.substring(mesgStop));
			
			 StrLst.add(tmpLine);
			// System.out.println(line);
			 String original=line.substring(mesgStart, mesgStop).toString();
			
			 String cleanStringreek=null;
			 
			 if(packedString.length()<4000&&nextLine!=null)
			 {
			
				 packedString.append(original);
				 packedString.append("  split line  < ");
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
						String greekOriginal = Parser.getGreek(inString);
						packedString=new StringBuffer();
							  cleanStringreek=clearSpecial(greekOriginal);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		        	newStrLstList=cleanStringreek.split("split line <");
		        int i=0;
		        	for (TempLine orgString : StrLst) {
						
		        		orgString.setReplace(newStrLstList[i]);
		           
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
		        out.println(l.getPrev()+l.getReplace()+l.getNext());}
		    out.close();
		    
		    
		    long elapsedTime = System.nanoTime() - start;
		    writer.write("process Duration in nanosecs:"+elapsedTime);
		    writer.close();
			
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
    	if(string.contains("+")){ string = string.replace("+", " "); }
    	if(string.contains("-")){ string = string.replace(" - ", " "); }
    	if(string.contains("=")){ string = string.replace("=", " "); }
    	if(string.contains("%")){ string = string.replace("%", " "); }
    	if(string.contains("#")){ string = string.replace("#", " "); }
    	if(string.contains("~")){ string = string.replace("~", " "); }
    	//if(string.contains("(")){ string = string.replace("(", " ("); }
    	//if(string.contains(")")){ string = string.replace(")", ") "); }
    	if(string.contains("[")){ string = string.replace("[", " "); }
    	if(string.contains("]")){ string = string.replace("]", " "); }
    	if(string.contains("\"")){ string = string.replace("\"", " "); }
    	if(string.contains("\\ç")){ string = string.replace("\\ç", " "); }
    	if(string.contains("\\ô")){ string = string.replace("\\ô", " "); }
    	if(string.contains(";")){ string = string.replace(";", " "); }
    	if(string.contains("?")){ string = string.replace("?", " "); }
    	//if(string.contains("<")){ string = string.replace("<", " "); }
    	if(string.contains(">")){ string = string.replace(">", " "); }
    	if(string.contains(",")){ string = string.replace(",", " "); }
    	if(string.contains("´")){ string = string.replace("´", " "); }
    	//if(string.contains(":")){ string = string.replace(":", " "); }
    	if(string.contains("«")){ string = string.replace("«", " "); }
    	if(string.contains("»")){ string = string.replace("»", " "); }
    	
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
    	
    	
   
    	return string.toLowerCase();
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
