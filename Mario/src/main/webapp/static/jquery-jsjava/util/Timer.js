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

 /**  The Timer class references to java.util.Timer of J2SE1.4 */
 
/**
 * constructor
 */
function Timer(){
	this.jsjava_class="jsjava.util.Timer";
    this.innerTimer=null;
    this.task=null;
    this.date=null;
    this.period=null;
    this.task2=null;
}

/**
 * Terminates this timer, discarding any currently scheduled tasks.
 */
Timer.prototype.cancel=function(){
    clearInterval(this.innerTimer);
};

/**
 * Tell the timer the current Timer instance
 */
Timer.prototype.setInstance=function(instanceName){
    this.instanceName=instanceName;	
};

/**
 * Schedules the specified task for execution at the specified time.
 * @param task a TimerTask object
 * @param date a Date object
 */
Timer.prototype.scheduleOnce=function(task,date){
    this.task=task
    this.date=date;
    var currDate=new Date();
    var currDateTime=currDate.getTime();
    var dateTime=date.getTime();
    var diffTime=dateTime-currDateTime;
    if(diffTime>=0){
        this.innerTimer=setTimeout("eval("+this.instanceName+".task.run())",diffTime);	
    }
};

/**
 * Schedules the specified task for repeated fixed-delay execution, 
 * beginning after the specified delay.
 * @param task a TimerTask object
 * @param period time in milliseconds between successive task executions.
 * @param delay delay in milliseconds before task is to be executed.
 */
Timer.prototype.scheduleRepeat=function(task,period,delay){
    this.task2=task
    this.period=period;	
    var evalStr="eval("+this.instanceName+".task2.run())";
    if(!delay||delay==0){
        eval(this.instanceName+".task2.run()");	
    }else{
        eval(this.instanceName+".task2.run()");	
    }
    this.innerTimer=setInterval(evalStr,period);
};

