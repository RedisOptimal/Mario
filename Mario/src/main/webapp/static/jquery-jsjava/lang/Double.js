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

/**  The Double class references to java.lang.Double of J2SE1.4 */
 
/**
 * constructor
 * @param value
 */
function Double(value){
	this.jsjava_class="jsjava.lang.Double";
    this.value=value;
}
Double.MIN=Math.pow(2,-1074);
Double.MAX=(2-Math.pow(2,-52))*Math.pow(2,1023);
Double.MIN_VALUE=Math.pow(2,-1074);
Double.MAX_VALUE=(2-Math.pow(2,-52))*Math.pow(2,1023);
Double.POSITIVE_INFINITY=1.0/0.0;
Double.NEGATIVE_INFINITY=-1.0/0.0;
Double.NaN=0.0/0.0;

/**
 * check whether the input value is a double value
 * @param d
 */
Double.checkValid=function(d){
	if(isNaN(d)){
		return false;
	}
	d=parseFloat(d);
    if(d<Double.POSITIVE_INFINITY&&d>Double.NEGATIVE_INFINITY){
        return true;
    }
    return false;
};


/**
 * judge whether the given d is an infinite number
 * @param d
 */
Double.isInfinite=function(d){
    return (d==Double.POSITIVE_INFINITY||d==Double.NEGATIVE_INFINITY);
};

/**
 * return the double value parsed from str
 * @param str
 */
Double.parseDouble=function(str){
    if(isNaN(str)){
		throw new NumberFormatException(NumberFormatException.NOT_NUMBER,"Not a number Exception!");
	}
    return parseFloat(str);
};

/**
 * compare whether this object value is equals to input Double object value
 * @param b input Double object
 */
Double.prototype.compareTo=function(b){
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
 * judge whether the current Double value is an infinite number
 * @param d
 */
Double.prototype.isInfinite=function(){
    return (this.value==Double.POSITIVE_INFINITY||this.value==Double.NEGATIVE_INFINITY);
};

/**
 * return the object's double value
 */
Double.prototype.DoubleValue=function(){
    return this.value;
};

/**
 * return a string description
 */
Double.prototype.toString=function(){
    return this.value; 
};

/**
 * compare whether this object is equal to input object o
 * @param o
 */
Double.prototype.equals=function(o){
    if(o==undefined){
        return false; 
    }
    if(o.jsjava_class&&o.jsjava_class=="jsjava.lang.Double"){
        return this.value==o.value; 
    }
    return false;
};

/**
 * Returns true if the specified number is a Not-a-Number (NaN) value, false otherwise.
 * @param v
 */
Double.isNaN=function(v) {
	return (v!= v);
};