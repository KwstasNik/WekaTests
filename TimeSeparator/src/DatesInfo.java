import java.awt.List;
import java.awt.Window.Type;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import org.joda.time.DateTime;


public class DatesInfo {

	private DateTime Day;
	private ArrayList<Message> messageLst;
	
	public DatesInfo()
	{
		
	}

	public DateTime getDay() {
		return Day;
	}

	public void setDay(DateTime day) {
		Day = day;
	}

	public ArrayList<Message> getMessageLst() {
		return messageLst;
	}

	public void setMessageLst(ArrayList<Message> messageLst) {
		this.messageLst = messageLst;
	}
	
	
}

class Message
{
	private double msgClass;
	//Type is 0 for post 1 for comment
	private int Type;
	public double getMsgClass() {
		return msgClass;
	}
	public void setMsgClass(double msgClass) {
		this.msgClass = msgClass;
	}
	public int getType() {
		return Type;
	}
	public void setType(int type) {
		Type = type;
	}
	

}


class UserCalendar
{
	ArrayList<DatesInfo> userCalendar;
	
	public UserCalendar(String StartDay,String StopDay,int constantDays) throws ParseException
	{
		DatesHelper dHelper=new DatesHelper(StartDay, StopDay, constantDays);
		
		this.userCalendar= dHelper.GeneratePeriodList();

	}
	
	public void inserMessageToPeriod(double Class,DateTime Date,int Type)
	{
		DateTime curDateTime=Date;
		int i=0;
		while(curDateTime.isBefore(userCalendar.get(i).getDay()))
		{
			curDateTime=userCalendar.get(i).getDay();
			
		}
		
	ArrayList<Message> curMsgList=userCalendar.get(i-1).getMessageLst();
	if(curMsgList==null){
		ArrayList<Message> msglst=new ArrayList<Message>();
		Message msgMessage=new Message();
		msgMessage.setMsgClass(Class);
		msgMessage.setType(Type);
		msglst.add(msgMessage);
		userCalendar.get(i-1).setMessageLst(msglst);
		
	}
	else
	{
		Message msgMessage=new Message();
	msgMessage.setMsgClass(Class);
	msgMessage.setType(Type);
		curMsgList.add(msgMessage);
	}
	
	}

	
	public ArrayList<DatesInfo> getUserCalendar()
	{
		
		return userCalendar;
	}
}