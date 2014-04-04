import java.util.ArrayList;


public class Config {
	/*database*/
	public static final String URL = "jdbc:mysql://localhost:3306/wekatest?useUnicode=true&mysqlEncoding=utf8&characterEncoding=utf8";
	public static final String USER = "root";
	public static final String PASSWORD = "";
	public static final String CLASSES_Arff="{'0','1','2','10'}";
	//public static final String[] CLASSES=new String[] {"0","1","2","10"};
	//public static final int[] CLASSES_SIZE=new int[]{310,310,450,450};
	private  ArrayList<ClassConfig> Configuration;
	
	//public static final String QUERY_STRING= "Select class,message,userid,date,id from allingreekcleanV3 where class= ";
	
	//public static final String QUERY_STRING= "Select class,message,userid,date,postid from commentnew where class= ";
	public static final String QUERY_STRING= "Select class,message,userid,date,id from postwithlinksv2 where class= ";
	
	//public static final int NUMBER = 300;
	Config()
	{
		Configuration=new ArrayList<>();
		
		ClassConfig config0=new ClassConfig();
		config0.setClassDescription("0");
		config0.setEntriesCount(100000);
		config0.setMaxwordcount(2000);
		config0.setMinwordCount(0);
		Configuration.add(config0);
		
		ClassConfig config1=new ClassConfig();
		config1.setClassDescription("1");
		config1.setEntriesCount(100000);
		config1.setMaxwordcount(5000);
		config1.setMinwordCount(0);
		Configuration.add(config1);
		
		ClassConfig config2=new ClassConfig();
		config2.setClassDescription("2");
		config2.setEntriesCount(100000);
		config2.setMaxwordcount(2000);
		config2.setMinwordCount(0);
		Configuration.add(config2);
		
		ClassConfig config10=new ClassConfig();
		config10.setClassDescription("10");
		config10.setEntriesCount(140000);
		config10.setMaxwordcount(2000);
		config10.setMinwordCount(0);
		Configuration.add(config10);
		
		
		/*ClassConfig config20=new ClassConfig();
		config20.setClassDescription("20");
		config20.setEntriesCount(140000);
		config20.setMaxwordcount(2000);
		config20.setMinwordCount(0);
		Configuration.add(config20);
		
		ClassConfig config40=new ClassConfig();
		config40.setClassDescription("40");
		config40.setEntriesCount(140000);
		config40.setMaxwordcount(2000);
		config40.setMinwordCount(0);
		Configuration.add(config40);

		ClassConfig config50=new ClassConfig();
		config50.setClassDescription("50");
		config50.setEntriesCount(140000);
		config50.setMaxwordcount(2000);
		config50.setMinwordCount(0);
		Configuration.add(config50);*/
	}
	
	public  ArrayList<ClassConfig>  getConfiguration() {
		return Configuration;
	}
	
}

 class ClassConfig
{
	 
	
	 
	 private String classDescription;
	 
private int entriesCount;

public int getEntriesCount() {
	return entriesCount;
}

public void setEntriesCount(int entriesCount) {
	this.entriesCount = entriesCount;
}

public int getMaxwordcount() {
	return maxwordcount;
}

public void setMaxwordcount(int maxwordcount) {
	this.maxwordcount = maxwordcount;
}

public int getMinwordCount() {
	return minwordCount;
}

public void setMinwordCount(int minwordCount) {
	this.minwordCount = minwordCount;
}

public String getClassDescription() {
	return classDescription;
}

public void setClassDescription(String classDescription) {
	this.classDescription = classDescription;
}

private int maxwordcount;


private int minwordCount;

}