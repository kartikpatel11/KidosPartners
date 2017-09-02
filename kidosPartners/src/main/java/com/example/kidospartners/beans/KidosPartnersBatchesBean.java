package com.example.kidospartners.beans;

import java.util.Arrays;
import java.util.LinkedList;

public class KidosPartnersBatchesBean {

	private String starttime;
	
	private String endtime;
	
	private LinkedList<String> days;
	
	private LinkedList<String> daysList=new LinkedList<String>(Arrays.asList("Mon","Tue","Wed","Thu","Fri","Sat","Sun"));
	
	private String _id;

	public KidosPartnersBatchesBean(LinkedList<Boolean> _days,
			String _fromTime, String _toTime) {
		this.starttime=_fromTime;
		this.endtime=_toTime;
		
		this.days=new LinkedList<String>();
		for(int i=0;i<_days.size();i++)
		{
			if(_days.get(i))
				this.days.add(daysList.get(i));
		}
		
		
	}

	public void setDays(LinkedList<String> days) {
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

	
	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public LinkedList<String> getDays() {
		return days;
	}

	@Override
	public String toString() {
		return "KidosPartnersBatchesBean [starttime=" + starttime
				+ ", endtime=" + endtime + ", days=" + days + ", _id=" + _id
				+ "]";
	}

	
	
	
}
