import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.text.html.parser.Element;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPException;

import org.apache.axis.message.SOAPHeaderElement;

import com.innoetics.services.InnoG2GWSClassSoap;
import com.innoetics.services.InnoG2GWSClassSoapProxy;
import com.innoetics.services.InnoG2GWSClassSoapStub;


public class Parser {

	/**
	 * @param args
	 */
	static String INPUT_PATH="C:\\Users\\Kwstas\\Desktop\\gritT\\topallin.arff";
	static String OUPUT_PATH="C:\\Users\\Kwstas\\Desktop\\gritT\\topallinOut.arff";
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		 try {
			readLargerTextFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
	
		
		
	}
	public  static String getGreek(String input) throws Exception 
	
	{
		String out=input;
		InnoG2GWSClassSoapProxy proxy=new InnoG2GWSClassSoapProxy();	
		InnoG2GWSClassSoapStub soap=(InnoG2GWSClassSoapStub)proxy.getInnoG2GWSClassSoap();
		
	SOAPHeaderElement header=new SOAPHeaderElement(new QName("http://services.innoetics.com","ServiceAuthHeader"));
	try {
		header.addChildElement("Username").addTextNode("nikolkon");
		header.addChildElement("Password").addTextNode("g2g17012014!");
		
	} catch (SOAPException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
		soap.setHeader(header);
		
		
		try {
			out = proxy.greeklishToGreek(input);
			//System.out.println(out);
			
			
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	 return out;
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
		    int replacement=0;
		    while (line != null ) {
		    	
		        if (containsEnglish(line)) {
		    	//if(IsMatch(line, regexpattern)){
		        	
		      //      String sValue = line.substring(line.indexOf("http"),line.indexOf("'", line.indexOf("http")));
		        	replacement++;
		            String replaced=line;
					try {
						replaced = getGreek(line);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		           
		            line =replaced;
		            
		            writer.newLine();
		            
		         
		          /*  try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}*/
		            }
		        c++;
		        
		        lines.add(line);
		        System.out.println(c);
		        System.out.println("------------finished-----------");
		        System.out.println("Entries replaced="+replacement);
		        
		        line = in.readLine();
		        }
		        //this sequence[, '',] means the Line Has no data,
		        //so it must be removed
		       
		      
		    
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
	
	public static boolean containsEnglish(String toExamine) {
	    Pattern pattern = Pattern.compile("[abcdefghigklmnopqrstuvwxyz]");
	    Matcher matcher = pattern.matcher(toExamine);
	    return matcher.find();
	}
}
