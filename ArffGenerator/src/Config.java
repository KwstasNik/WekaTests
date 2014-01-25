
public class Config {
	/*database*/
	public static final String URL = "jdbc:mysql://localhost:3306/wekatest?useUnicode=true&mysqlEncoding=utf8&characterEncoding=utf8";
	public static final String USER = "root";
	public static final String PASSWORD = "";
	public static final String CLASSES_Arff="{'0','1','2','10'}";
	public static final String[] CLASSES=new String[] {"0","1","2","10"};
	
	public static final String QUERY_STRING= "Select class,message from allingreekclean where class= ";
	
	public static final int NUMBER = 300;
	
}
