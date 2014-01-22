import java.util.ArrayList;


public class QueryHelper {

	public QueryHelper()
	{
		
		
	}
	
	public String[] getQuerywithDataId (int DataId)
	
	{
		String []Queries =new String[2];
		switch (DataId)
		{
		case 1:
			Queries[0]="SELECT message,class FROM  postclassified1000 ";
			Queries[1]="select  message,class from post100Testing";
				
			break;
		case 2:
			Queries[0]="SELECT message,class FROM  postclassified1000 WHERE class !=  '10'";
			Queries[1]="select  message,class from post100Testing WHERE class !=  '10'";
		case 3:		
			Queries[0]="SELECT message,class FROM  postclassified1000 WHERE class !=  '10'";
			Queries[1]="select  message,class from post500Testing WHERE class !=  '10'";
			break;
		case 12:
			Queries[0]="SELECT message,class FROM  postclassified500vag union " +
			" select  message,class from post500Testing where class!=10 union "+
			"	select  message,class from postClassified1500to2000	  union "+
			" select  message,class from post300SotClassified where class!=10 union "+
			" select  message,class from FaF2kto30k11700classified where class!=10 union "+
			"select   message,class from post300SotClassified where class=10";
	
			Queries[1]="select  message,class from postclassified1000 ";
			break;
			
			default:
				
				break;
		}
			return Queries;
		
		}
		/*int DATA_ID=1;
		String QUERY_LEARN="SELECT message,class FROM  postclassified1000 ";
		String QUERY_TEST="select  message,class from post100Testing";*/
		
	//	int DATA_ID=2;
	//	String QUERY_LEARN="SELECT message,class FROM  postclassified1000 WHERE class !=  '10'";
	//	String QUERY_TEST="select  message,class from post100Testing WHERE class !=  '10'";
		
	//	int DATA_ID=3;
		//String QUERY_LEARN="SELECT message,class FROM  postclassified1000 WHERE class !=  '10'";
	//	String QUERY_TEST="select  message,class from post500Testing WHERE class !=  '10'";
		
		/*int DATA_ID=4;
		String QUERY_LEARN="SELECT message,class FROM  postclassified1000stemmed  ";
		String QUERY_TEST="select  message,class from post500TestingStemmed ";*/
		
		
		/*	int DATA_ID=5;
			String QUERY_LEARN="SELECT message,class FROM  postclassified1000 ";
			String QUERY_TEST="select  message,class from post500Testing";*/
			
		/*	int DATA_ID=6;
			String QUERY_LEARN="SELECT message,class FROM  postclassified500vag where class!=10	 union" +
					"	select  message,class from postclassified1000	where class!=10";
			String QUERY_TEST="select  message,class from post500Testing where class!=10";
			*/
			
		/*	int DATA_ID=7;
			String QUERY_LEARN="SELECT message,class FROM  postclassified500vag  union " +
					"	select  message,class from postclassified1000	where class!=10 union "+
					"select  message,class from post300SotClassified";
			String QUERY_TEST="select  message,class from post500Testing where class";*/
			
			/*int DATA_ID=8;
			String QUERY_LEARN="SELECT message,class FROM  postclassified500vag  union " +
					"	select  message,class from postclassified1000	where class!=10 union "+
					"	select  message,class from postClassified1500to2000	where class!=10 union "+
					"select  message,class from post300SotClassified ";
			String QUERY_TEST="select  message,class from post500Testing ";*/
			
			/*int DATA_ID=9;
			String QUERY_LEARN="SELECT message,class FROM  postclassified500vag  where class!=10 union " +
					" select  message,class from postclassified1000	where class!=10 union "+
					"	select  message,class from postClassified1500to2000	where class!=10 union "+
					"select  message,class from post300SotClassified where class!=10 union "+
					"(select   message,class from post300SotClassified where class=10 LIMIT 100)";
			
			String QUERY_TEST="select  message,class from post500Testing ";*/
			
		/*int DATA_ID=10;
		String QUERY_LEARN="SELECT message,class FROM  postclassified500vag  where class!=10 union " +
				" select  message,class from postclassified1000	union "+
			//	"	select  message,class from postClassified1500to2000	 ";
				"select  message,class from post300SotClassified where class!=10  ";
			*/
		
		//int DATA_ID=10;
		/*String QUERY_LEARN="SELECT message,class FROM  postclassified500vag union " +
				" select  message,class from post500Testing where class!=10 union "+
				"	select  message,class from postClassified1500to2000	  union "+
				" select  message,class from post300SotClassified where class!=10 union "+
				" select  message,class from FaF2kto30k11700classified where class!=10 union "+
				"select   message,class from post300SotClassified where class=10";
		
		String QUERY_TEST="select  message,class from postclassified1000 ";*/
		
		
		/*int DATA_ID=11;
		String QUERY_LEARN="SELECT message,class FROM  postclassified500vag union " +
				" select  message,class from post500Testing  union "+
				"	select  message,class from  postclassified1000   union "+
				" select  message,class from post300SotClassified   union "+
				" select  message,class from FaF2kto30k11700classified where class!=10 union "+
				"select   message,class from post300SotClassified ";
		
		String QUERY_TEST="select  message,class from  postClassified1500to2000	";
		*/
		
		
	
}
