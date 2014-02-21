import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.annotation.Generated;

import org.joda.time.DateTime;




public class DatesHelper {
	private DateTime start_date;
	private DateTime stop_date; 
	private int constantDays;
	
	public DatesHelper(String StartDay,String StopDay,int constantDays) throws ParseException
	{
		this.start_date = DateTime.parse(StartDay);
		this.stop_date =DateTime.parse(StopDay);
		this.constantDays=constantDays;
		
	}
	
	public ArrayList<DatesInfo> GeneratePeriodList()
	{
		
		DateTime currentDay=start_date;
		ArrayList<DatesInfo> dateLst=new ArrayList<DatesInfo>();
		while (currentDay.isBefore(stop_date))
		{
			DatesInfo di=new DatesInfo();
			di.setDay(currentDay);
			
			dateLst.add(di);
		//	di.setMessageLst(messageLst);
			currentDay=currentDay.plusDays(constantDays);
		}
		DateTime lastDate=dateLst.get(dateLst.size()-1).getDay();
		//to Fix
		if(Integer.parseInt(lastDate.toString("MM"))<31)
		{
			DatesInfo di=new DatesInfo();
		di.setDay(lastDate.plusDays(1));
		
			dateLst.add(di);
		}
		return dateLst;
	}
	
	
}
