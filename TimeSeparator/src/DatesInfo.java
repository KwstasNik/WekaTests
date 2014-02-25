import java.awt.List;
import java.awt.Window.Type;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import org.joda.time.DateTime;

import com.thoughtworks.xstream.annotations.XStreamOmitField;


public class DatesInfo {
	
	@XStreamOmitField
	private DateTime Day;
	
	private String DayAsString;
	
	private ArrayList<Message> messageLst;
	
	private ArrayList<Post> postLst;

	private ArrayList<Comment> commentLst;
	
	public DatesInfo()
	{
		
	}

	public DateTime getDay() {
		return Day;
	}

	public void setDay(DateTime day) {
		Day = day;
		setDayAsString(day.toString("yyyy/MM/dd"));
	}

	public ArrayList<Message> getMessageLst() {
		return messageLst;
	}

	public void setMessageLst(ArrayList<Message> messageLst) {
		this.messageLst = messageLst;
	}

	public String getDayAsString() {
		return DayAsString;
	}

	public void setDayAsString(String dayAsString) {
		DayAsString = dayAsString;
	}

	public ArrayList<Post> getPostLst() {
		return postLst;
	}

	public void setPostLst(ArrayList<Post> postLst) {
		this.postLst = postLst;
	}

	public ArrayList<Comment> getCommentLst() {
		return commentLst;
	}

	public void setCommentLst(ArrayList<Comment> commentLst) {
		this.commentLst = commentLst;
	}
	
	
}

class Message
{
	private Double msgClass;
	//Type is 0 for post 1 for comment
	private int Type;
	private String messageString; 
	public Double getMsgClass() {
		return msgClass;
	}
	public void setMsgClass(Double msgClass) {
		this.msgClass = msgClass;
	}
	public int getType() {
		return Type;
	}
	public void setType(int type) {
		Type = type;
	}
	public String getMessageString() {
		return messageString;
	}
	public void setMessageString(String messageString) {
		this.messageString = messageString;
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
	
	public void inserMessageToPeriod(double Class,DateTime Date,int Type,String messageStr)
	{
		DateTime curDateTime=Date;
		int i=0;
		try{
		while(Date.isAfter(userCalendar.get(i).getDay()))
		{
			curDateTime=userCalendar.get(i).getDay();
			i++;
		}
		}
		catch (Exception E)
		{
			E.printStackTrace();
			System.out.println(Date);
		}
		if (i==0){i=1;};
	ArrayList<Message> curMsgList=userCalendar.get(i-1).getMessageLst();
	if(curMsgList==null){
		ArrayList<Message> msglst=new ArrayList<Message>();
		Message msgMessage=new Message();
		msgMessage.setMsgClass(Class);
		msgMessage.setMessageString(messageStr);
		msgMessage.setType(Type);
		msglst.add(msgMessage);
		userCalendar.get(i-1).setMessageLst(msglst);
		
	}
	else
	{
		Message msgMessage=new Message();
	msgMessage.setMsgClass(Class);
	msgMessage.setMessageString(messageStr);
	
	msgMessage.setType(Type);
		curMsgList.add(msgMessage);
	}
	
	}
	

	public void insertPostToPeriod(Post post)
	{
		DateTime curDateTime=DateTime.parse(post.getDate());
		int i=0;
		try{
		while(DateTime.parse(post.getDate()).isAfter(userCalendar.get(i).getDay()))
		{
			curDateTime=userCalendar.get(i).getDay();
			i++;
		}
		}
		catch (Exception E)
		{
			E.printStackTrace();
			System.out.println(post.getDate());
		}
		if (i==0){i=1;};
	ArrayList<Post> curMsgList=userCalendar.get(i-1).getPostLst();
	if(curMsgList==null){
		ArrayList<Post> postlst=new ArrayList<Post>();
		postlst.add(post);
		userCalendar.get(i-1).setPostLst(postlst);
	
		
	}
	else
	{
		
		curMsgList.add(post);
	}
	}

	
	public void insertCommentToPeriod(Comment comment)
	{
		DateTime curDateTime=DateTime.parse(comment.getDate());
		int i=0;
		try{
		while(DateTime.parse(comment.getDate()).isAfter(userCalendar.get(i).getDay()))
		{
			curDateTime=userCalendar.get(i).getDay();
			i++;
		}
		}
		catch (Exception E)
		{
			E.printStackTrace();
			System.out.println(comment.getDate());
		}
		if (i==0){i=1;};
	ArrayList<Comment> curMsgList=userCalendar.get(i-1).getCommentLst();
	if(curMsgList==null){
		ArrayList<Comment> commntlst=new ArrayList<Comment>();
		commntlst.add(comment);
		userCalendar.get(i-1).setCommentLst(commntlst);
	
		
	}
	else
	{
		
		curMsgList.add(comment);
	}
	
	}
	
	
	public ArrayList<DatesInfo> getUserCalendar()
	{
		
		return userCalendar;
	}
}