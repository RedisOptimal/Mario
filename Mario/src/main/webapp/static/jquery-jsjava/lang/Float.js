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

/**  The Float class references to java.lang.Float of J2SE1.4 */
 
/**
 * constructor
 * @param value
 */
function Float(value){
	this.jsjava_class="jsjava.lang.Float";
    this.value=value;
}
Float.MIN=Math.pow(2,-149);
Float.MAX=(2-Math.pow(2,-23))*Math.pow(2,127);
Float.MIN_VALUE=Math.pow(2,-149);
Float.MAX_VALUE=(2-Math.pow(2,-23))*Math.pow(2,127);
Float.POSITIVE_INFINITY=1.0/0.0;
Float.NEGATIVE_INFINITY=-1.0/0.0;

/**
 * check whether the input value is a float value
 * @param f
 */
Float.checkValid=function(f){
	if(isNaN(f)){
		return false;
	}
	f=parseFloat(f);
    if(f<Float.POSITIVE_INFINITY&&f>Float.NEGATIVE_INFINITY){
    	return true;
	}
	return false;
};

/**
 * judge whether the given f is an infinite number
 * @param f
 */
Float.isInfinite=function(f){
    return (f==Float.POSITIVE_INFINITY||f==Float.NEGATIVE_INFINITY);
};

/**
 * return the float value parsed from str
 * @param str
 */
Float.parseFloat=function(str){
    if(isNaN(str)){
		throw new NumberFormatException(NumberFormatException.NOT_NUMBER,"Not a number Exception!");
	}
    return parseFloat(str);
};

/**
 * compare whether this object value is equals to input Float object value
 * @param b input Float object
 */
Float.prototype.compareTo=function(b){
    if(b==undefined){
        return -1; 
    }
    if(this.value>b.value){
        return 1; 
    }else if(this.value==b.value){
        return 0; 
    }else{
        return -1;  
    }
};

/**
 * return the object's float value
 */
Float.prototype.floatValue=function(){
    return this.value;
};

/**
 * judge whether the current Float value is an infinite number
 */
Float.prototype.isInfinite=function(){
    return (this.value==Float.POSITIVE_INFINITY||this.value==Float.NEGATIVE_INFINITY);
};

/**
 * return a string description
 */
Float.prototype.toString=function(){
    return this.value; 
};

/**
 * compare whether this object is equal to input object o
 * @param o
 */
Float.prototype.equals=function(o){
    if(o==undefined){
        return false; 
    }
    if(o.jsjava_class&&o.jsjava_class=="jsjava.lang.Float"){
        return this.value==o.value; 
    }
    return false;
};