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

 /**  The GregorianCalendar class references to java.util.GregorianCalendar of J2SE1.4 */
 
/**
 * constructor
 */
function GregorianCalendar(){
	this.jsjava_class="jsjava.util.GregorianCalendar";
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

GregorianCalendar.prototype=new Calendar();
GregorianCalendar.prototype.constructor=GregorianCalendar;