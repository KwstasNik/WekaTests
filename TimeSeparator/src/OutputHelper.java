import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;

import com.thoughtworks.xstream.XStream;


public class OutputHelper {
    PrintWriter out;
    
	public OutputHelper(String OUPUT_PATH) throws FileNotFoundException, UnsupportedEncodingException {
		// TODO Auto-generated constructor stub
		 FileOutputStream fout = new FileOutputStream( new File( OUPUT_PATH ) );
		     out = new PrintWriter(new OutputStreamWriter(fout, "UTF-8"));
	}
	
	public void createCalendarDoc( ArrayList<UserInfo> uiArrayList)

	{
		UserInfo userInfofInfo= uiArrayList.get(0);
		out.print(String.format("%1$"+19+ "s", "USERS  "));
		
		for (DatesInfo dInfo : userInfofInfo.getUserCalendr()) 
		{
			out.print(String.format("%1$"+30+ "s", dInfo.getDay().toString("MM/dd/yyyy")));
				
		}
		out.print("\n");
		for (UserInfo userInfo : uiArrayList) {
			out.print(String.format("%1$"+18+ "s", userInfo.getId())+": ");
			for (DatesInfo dInfo : userInfo.getUserCalendr()) {
			//	out.print(dInfo.getDay().toString("YYYY/MM/DD")+" [");
				out.print(" [");
				if(dInfo.getMessageLst()==null)
				{
					out.print(String.format("%1$"+28+ "s","X],"));
					
				}
				else
				{
					StringBuffer sb=new StringBuffer();
					for (Message msg : dInfo.getMessageLst()) {
						sb.append(msg.getMsgClass().intValue());
						//sb.append(" ");
						
					}
					out.print(String.format("%1$"+28+ "s",sb.toString()+"],"));
					
				}
			}
			out.print("\n");
		}
		out.close();
	}

	
public void createXML( ArrayList<UserInfo> uiArrayList,String Path) throws FileNotFoundException
{
	UserInfoList uil=new UserInfoList();
	uil.setUserInfoCatalog(uiArrayList);
	XStream xStream=new XStream();
	xStream.autodetectAnnotations(true);
	OutputStreamWriter    writer = new OutputStreamWriter(new FileOutputStream(new File(Path)),Charset.forName("UTF-8"));
	xStream.toXML(uil, writer);

}


private ArrayList<UserInfo>  clearempty(ArrayList<UserInfo> uiArrayList)
{
	System.out.println("Cleaning Unused");
	double rem=0;
	for (UserInfo userInfo : uiArrayList) {
		Iterator<DatesInfo> iterator= userInfo.getUserCalendr().iterator();
		while(iterator.hasNext()) {
			DatesInfo dInfo=iterator.next();
			if ((dInfo.getCommentLst()==null||dInfo.getCommentLst().size()==0)&&(dInfo.getPostLst()==null||dInfo.getPostLst().size()==0))
			{rem++;
			iterator.remove();
			}
			
		}
		System.out.println("Cleaned "+rem);
		
	}
	
	
	return uiArrayList;
	
	
}
}
