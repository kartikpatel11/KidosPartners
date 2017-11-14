package com.androidapp.kidospartners.beans;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class KidosPartnersBatchesBean {

	private String starttime;
	
	private String endtime;
	
	private ArrayList<String> days;


	private LinkedList<String> daysList=new LinkedList<String>(Arrays.asList("Mon","Tue","Wed","Thu","Fri","Sat","Sun"));
	



	public KidosPartnersBatchesBean(LinkedList<Boolean> _days,
									String _fromTime, String _toTime) {
		this.starttime=_fromTime;
		this.endtime=_toTime;
		
		this.days=new ArrayList<String>();
		for(int i=0;i<_days.size();i++)
		{
			if(_days.get(i))
				this.days.add(daysList.get(i));
		}
}

	public void setDays(ArrayList<String> days) {
		this.days = days;

	}

	public String getStarttime() {
		return starttime;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}

	public String getEndtime() {
		return endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}


	public String getDays() {
		return Arrays.toString(this.days.toArray());
	}

	@Override
	public String toString() {
		return "KidosPartnersBatchesBean [starttime=" + starttime
				+ ", endtime=" + endtime + ", days=" + days
				+ "]";
	}

	
	
	
}
