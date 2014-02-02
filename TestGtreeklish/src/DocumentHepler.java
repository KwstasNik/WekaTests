
public class DocumentHepler {

	
	private String docType;
	public DocumentHepler(String docType)
	{
		this.docType=docType;
		
		
	}
	
	public int getPrevIndex(String line)
	{int index=0;
		switch (docType) {
		case "SQL":
		 	  index= line.indexOf("', '", line.indexOf("', '")+4)+4;
			break;
		case "ARFF":
			  index= line.indexOf("'")+1;
			break;
		default:
			break;
		}
		return index;
	}
	
	public int getNextIndex(String line)
	{
		int index=0;
		switch (docType) {
		case "SQL":
			  index= line.indexOf("', '", getPrevIndex(line));
			 
			break;
		case "ARFF":
			  index= line.indexOf("','");
			break;
		default:
			break;
		}
		return index;
	}
	
	public boolean isProperLine(String line)
	{
		boolean res=false;
		switch (docType) {
		case "SQL":
			res=line.contains(", '");
			 
			break;
		case "ARFF":
			res= line.contains("','")&&( !line.contains("@"));
			break;
		default:
			break;
		}
		return res;
	}
}
