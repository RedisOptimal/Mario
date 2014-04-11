/**
 *  Copyright (C) 2006 zhangbo (freeeob@gmail.com)
 *
 *  This product is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU Lesser General Public License as published by
 *  the Free Software Foundation; either version 2.1 of the License, or
 *  (at your option) any later version.
 * 
 *  This product is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser General Public License for more details.
 * 
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with this library; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA.
 *
 *  author:zhangbo
 *  Email:jsjava@gmail.com
 */

 /**  The Calendar class references to java.util.Calendar of J2SE1.4 */
 
/**
 * constructor
 */
function Calendar(){
	this.jsjava_class="jsjava.util.Calendar";
	this._date=new Date();
	this.time=this._date.getTime();
	this.year=this._date.getYear();
	this.month=this._date.getMonth();
	this.date=this._date.getDate();
	this.day=this._date.getDay();
	this.hours=this._date.getHours();
	this.minutes=this._date.getMinutes();
	this.seconds=this._date.getSeconds();
}

Calendar.ERA = 0;    
Calendar.YEAR = 1;
Calendar.MONTH = 2;
Calendar.WEEK_OF_YEAR = 3;
Calendar.WEEK_OF_MONTH = 4;
Calendar.DATE = 5;
Calendar.DAY_OF_MONTH = 5;
Calendar.DAY_OF_YEAR = 6;
Calendar.DAY_OF_WEEK = 7;
Calendar.DAY_OF_WEEK_IN_MONTH = 8;
Calendar.AM_PM = 9;
Calendar.HOUR = 10;
Calendar.HOUR_OF_DAY = 11;
Calendar.MINUTE = 12;
Calendar.SECOND = 13;
Calendar.MILLISECOND = 14;
Calendar.ZONE_OFFSET = 15;
Calendar.DST_OFFSET = 16;
Calendar.FIELD_COUNT = 17;
Calendar.SUNDAY = 1;
Calendar.MONDAY = 2;
Calendar.TUESDAY = 3;
Calendar.WEDNESDAY = 4;
Calendar.THURSDAY = 5;
Calendar.FRIDAY = 6;
Calendar.SATURDAY = 7;
Calendar.JANUARY = 0;
Calendar.FEBRUARY = 1;
Calendar.MARCH = 2;
Calendar.APRIL = 3;
Calendar.MAY = 4;
Calendar.JUNE = 5;
Calendar.JULY = 6;
Calendar.AUGUST = 7;
Calendar.SEPTEMBER = 8;
Calendar.OCTOBER = 9;
Calendar.NOVEMBER = 10;
Calendar.DECEMBER = 11;
Calendar.UNDECIMBER = 12;
Calendar.AM = 0;
Calendar.PM = 1;
Calendar.BIG_MONTH_DAYS=31;
Calendar.SMALL_MONTH_DAYS=30;
Calendar.LEAP_YEAR_FEB_DAYS=29;
Calendar.NON_LEAP_YEAR_FEB_DAYS=28;
Calendar.DAYS_OF_WEEK=7;

/**
 * check whether the calendar object is valid
 * @param c calendar object
 */
Calendar.prototype.checkValid=function(c){
	if(c==undefined||!c.jsjava_class||c.jsjava_class!=this.jsjava_class){
	    throw new IllegalArgumentException(IllegalArgumentException.ERROR,"Invalid arguments!");
	}
};

/**
 * Compares the time field records.
 * @param c calendar object
 */
Calendar.prototype.after=function(c){
	this.checkValid(c);
	if(this.getTimeInMillis()>c.getTimeInMillis()){
		return true;
	}
	return false;
};

/**
 * Compares the time field records
 * @param c calendar object
 */
Calendar.prototype.before=function(c){
	this.checkValid(c);
	if(this.getTimeInMillis()<c.getTimeInMillis()){
		return true;
	}
	return false;
};

/**
 * Gets the value for a given time field.
 * @param field the given time field.
 */
Calendar.prototype.get=function(field){
	if(typeof(field)!="number"){
		throw new IllegalArgumentException(IllegalArgumentException.ERROR,"Invalid arguments!");
	}
	var result=-1;
	switch(field){
		case Calendar.ERA :{
			result=Calendar.ERA+1;
			break;
		};    
		case Calendar.YEAR :{
			result=this.year;
			break;
		};
		case Calendar.MONTH :{
			result=this.month;
			break;
		};
		case Calendar.WEEK_OF_YEAR :{
			var fDate=new Date(this.year,0,1);
			var fDay=fDate.getDay();
			var cDate=this.get(Calendar.DAY_OF_YEAR);
			var n=Math.floor((cDate+fDay-1)/Calendar.DAYS_OF_WEEK)+1;
			result=n;
			break;
		};
		case Calendar.WEEK_OF_MONTH :{
			var fDate=new Date(this.year,this.month,1);
			var fDay=fDate.getDay();
			var cDate=this.date;
			var n=Math.floor((cDate+fDay-1)/Calendar.DAYS_OF_WEEK)+1;
			result=n;
			break;
		};
		case Calendar.DATE :{
			result=this.date;
			break;
		};
		case Calendar.DAY_OF_MONTH :{
			result=this.date;
			break;
		};
		case Calendar.DAY_OF_YEAR :{
			var nDay=0;
			var month=this.month;			
			for(var i=0;i<month;i++){
				nDay+=Calendar.getDaysOfMonth(this.year,i);
			}
			nDay+=this.date;
			result=nDay;
			break;
		};
		case Calendar.DAY_OF_WEEK :{
			result=this.day+1;
			break;
		};
		case Calendar.DAY_OF_WEEK_IN_MONTH :{
			var date=this.date;
			var n=Math.floor((date-1)/Calendar.DAYS_OF_WEEK)+1;
			result=n;
			break;
		};
		case Calendar.AM_PM :{
			if(this.hours>12){
				result=Calendar.PM;
			}else{
				result=Calendar.AM;
			}
			break;
		}
		case Calendar.HOUR :{
			var hours=this.hours;
			if(hours>=12){
				result=hours-12;
			}else{
				result=hours;
			}
			break;
		};
		case Calendar.HOUR_OF_DAY :{
			result=this.hours;
			break;
		};
		case Calendar.MINUTE :{
			result=this.minutes;
			break;
		};
		case Calendar.SECOND :{
			result=this.seconds;
			break;
		};
		case Calendar.MILLISECOND :{
			result=this.time;
			break;
		};
		case Calendar.ZONE_OFFSET :{
			result=this._date.getTimezoneOffset()*60*1000;
			break;
		};
		case Calendar.DST_OFFSET :{
			result=0;
			break;
		};
	}
	return result;
};

/**
 * Gets a calendar using the default time zone and locale.
 */
Calendar.prototype.getInstance=function(){
	return new Calendar();
};

/**
 * Gets this Calendar's current time.
 */
Calendar.prototype.getTime=function(){
	return this._date;
};

/**
 * Gets this Calendar's current time as a long.
 */
Calendar.prototype.getTimeInMillis=function(){
	return this.time;
};

/**
 * check whether the calendar year is a leap year
 */
Calendar.prototype.isLeapYear=function(){
	var year=this.year;
	if(year%100==0){
		if(year%400==0){
			return true;
		}
		return false;
	}
	if(year%4==0){
		return true;
	}
	return false;
};

/**
 * check whether the given year is a leap year
 * @param year the given year number
 */
Calendar.isLeapYear=function(year){
	if(year%100==0){
		if(year%400==0){
			return true;
		}
		return false;
	}
	if(year%4==0){
		return true;
	}
	return false;
};

/**
 * Sets this Calendar's current time with the given Date.
 * @param date the given Date.
 */
Calendar.prototype.setTime=function(date){
	this._date=date;
	this.time=this._date.getTime();
	this.year=this._date.getYear();
	this.month=this._date.getMonth();
	this.date=this._date.getDate();
	this.day=this._date.getDay();
	this.hours=this._date.getHours();
	this.minutes=this._date.getMinutes();
	this.seconds=this._date.getSeconds();
};

/**
 * check whether the calendar month is a big month
 */
Calendar.prototype.isBigMonth=function(){
	var bigMonth=",1,3,5,7,8,10,12,";
	if(bigMonth.indexOf(","+(this.month+1)+",")!=-1){
		return true;
	}
	return false;
};

/**
 * check whether the given month is a big month
 * @param month the given month
 */
Calendar.isBigMonth=function(month){
	var bigMonth=",1,3,5,7,8,10,12,";
	if(bigMonth.indexOf(","+(month+1)+",")!=-1){
		return true;
	}
	return false;
};

/**
 * check whether the calendar month is a small month
 */
Calendar.prototype.isSmallMonth=function(){
	var smallMonth=",4,6,9,11,";
	if(smallMonth.indexOf(","+(this.month+1)+",")!=-1){
		return true;
	}
	return false;
};

/**
 * check whether the given month is a small month
 * @param month the given month
 */
Calendar.isSmallMonth=function(month){
	var smallMonth=",4,6,9,11,";
	if(smallMonth.indexOf(","+(month+1)+",")!=-1){
		return true;
	}
	return false;
};

/**
 * check whether the given month is February
 */
Calendar.prototype.isSpecialMonth=function(){
	if(this.month==1){
		return true;
	}
	return false;
};

/**
 * check whether the given month is February
 * @param month the given month
 */
Calendar.isSpecialMonth=function(month){
	if(month==1){
		return true;
	}
	return false;
};

/**
 * Get the number of days of the given month of year
 * @param year the given year
 * @param month the given month
 */
Calendar.getDaysOfMonth=function(year,month){
	if(Calendar.isBigMonth(month)){
		return Calendar.BIG_MONTH_DAYS;
	}
	if(Calendar.isSmallMonth(month)){
		return Calendar.SMALL_MONTH_DAYS;
	}
	if(!year){
		year=new Date().getYear();
	}
	if(Calendar.isLeapYear(year)){
		return Calendar.LEAP_YEAR_FEB_DAYS;
	}else{
		return Calendar.NON_LEAP_YEAR_FEB_DAYS;
	}
	
};