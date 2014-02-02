import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JOptionPane;

import org.omg.CORBA.PUBLIC_MEMBER;


public class Generator {

	static List<Integer> intGreekList = new ArrayList<Integer>();
	static List<Integer> intGreeklishList = new ArrayList<Integer>();
	
	//static List<String> greek = new ArrayList<String>();
	//static List<String> greeklish = new ArrayList<String>();
	static List<List<messageEntry> > messageEntryListPackEntries=new   ArrayList<List<messageEntry> >();
	static messageEntry RandomArray []=new  messageEntry[10000];
	static int training = 0;
	static int total = 0;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		setup();
		createFiles();
	}

	private static  void createFiles() {

		 JOptionPane optionPane= new JOptionPane(
				   "Save?",
				    JOptionPane.QUESTION_MESSAGE,
				    JOptionPane.YES_NO_OPTION);
		String name=optionPane.showInputDialog("Save to File");
		 
		 final String file1 = "C:\\Users\\Kwstas\\Desktop\\wekaTest\\" +name+
		 		".arff";
		//create training arff files
		  // Create file 
		  //FileWriter fstream1;
		try {
			//fstream1 = new FileWriter(file1);
		
			 Writer writer = new OutputStreamWriter(new FileOutputStream(file1), "UTF-8");	
			 BufferedWriter out1 = new BufferedWriter(writer);		 		 
		//	BufferedWriter outRateGreeklish = new BufferedWriter(writer);  
		  out1.write("@relation messages\n" +
				  "@attribute message string\n"+
				  "@attribute class "+Config.CLASSES_Arff+"\n @data \n\n");
		  String temp = null;
		  Random ran=new Random();
		  for (int l=0;l<messageEntryListPackEntries.size();l++){
			  
			  List<messageEntry> currnetEntryList=messageEntryListPackEntries.get(l);
			  int numOfEntr=Config.CLASSES_SIZE[l];
			  if (Config.CLASSES_SIZE[l]>currnetEntryList.size())
			{
				   numOfEntr=currnetEntryList.size();
			}
			
		  for(int i= 0; i < numOfEntr/*intGreekList.size()*/; i++ ){
			  
			  int position=ran.nextInt(10000);
			  //find an empty position
			  while(RandomArray[position]!=null){
				  position=ran.nextInt(10000);
			  }
			  
			  
			 RandomArray [position]=currnetEntryList.get(i);
			  
		  }
		  }
		  
		  
		  for (int i=0;i<RandomArray.length;i++){
			  if (RandomArray[i]!=null){
		  temp =RandomArray[i].getMessage();
		  temp = temp.replace("\n", " ");
		  out1.write("'" + RandomArray[i].getMes_class() + "'" + "," + "'" + temp + "'" + "," + "\n"); 
			  }
		  }
		  out1.close();
		  
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("created greek training file");
		
		
		
		
		
	
		
		
		  
		  
	}



	public static  void setup(){
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
			for (int c=0;c<Config.CLASSES.length;c++){
		List<messageEntry> messageEntryList = new ArrayList<messageEntry>();
			
		try {
			con = DriverManager.getConnection(Config.URL, Config.USER, Config.PASSWORD);
			st = con.createStatement();
			//return random
			String query = Config.QUERY_STRING+Config.CLASSES[c]+" order by RAND() limit "+Config.CLASSES_SIZE[c];
			System.out.println(query);
			rs = st.executeQuery(query);
			
		}catch (Exception e) {e.printStackTrace(); System.out.println("Failed execute select content query");}
		
		try{
			 System.out.println("processing comments for "+Config.CLASSES[c]);
			  while ( rs.next() ) {
				  //System.out.println(rs.getString("username") + "," + rs.getString("content"));
				  messageEntry messageEntry=new messageEntry();
				  messageEntry.setMes_class(rs.getString("message").replaceAll("\n", " "));
				  messageEntry.setMessage(rs.getString("class"));
				 messageEntryList.add(messageEntry);
			  }
			  messageEntryListPackEntries.add(messageEntryList);	
		} catch (Exception e) {e.printStackTrace(); System.out.println("Exception during process");}
		
	 finally {
		try {
			if (rs != null) {
				rs.close();
			}
			if (st != null) {
				st.close();
			}
			if (con != null) {
				con.close();
			}
		}catch (Exception e) {e.printStackTrace(); System.out.println("Exception during finally");}
	 }
	}
	}
}

class messageEntry
{
private String message;

public String getMessage() {
	return message;
}

public void setMessage(String message) {
	this.message = message;
}

public String getMes_class() {
	return mes_class;
}

public void setMes_class(String mes_class) {
	this.mes_class = mes_class;
}

private String mes_class;
}
