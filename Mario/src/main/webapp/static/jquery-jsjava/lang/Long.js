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

/**  The Long class references to java.lang.Long of J2SE1.4 */
 
/**
 * constructor
 * @param value
 */ 
function Long(value){
	this.jsjava_class="jsjava.lang.Long";
    this.value=value;
}
Long.MIN=-Math.pow(2,63);
Long.MAX=Math.pow(2,63)-1;
Long.MIN_VALUE=-Math.pow(2,63);
Long.MAX_VALUE=Math.pow(2,63)-1;

/**
 * check whether the input value is a long value
 * @param l
 */
Long.checkValid=function(l){
	if(isNaN(l)){
		return false;
	}
	if(typeof(l)=="number"){
		if(Math.floor(l)!=l){
			return false;
		}
	}else{
		if(l.indexOf(".")!=-1){
			return false;
		}
	}
	l=parseInt(l);
	if(l<=Long.MAX&&l>=Long.MIN){
    	return true;
    }
    return false;
};

/**
 * return the long value parsed from str
 * @param str
 */
Long.parseLong=function(str){
    if(isNaN(str)){
		throw new NumberFormatException(NumberFormatException.NOT_NUMBER,"Not a number Exception!");
	}
    var l=parseInt(str);
    if(!Long.checkValid(l)){
        return;
    }
    return l;
};

/**
 * compare whether this object value is equals to input Long object value
 * @param b input Long object
 */
Long.prototype.compareTo=function(b){
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
 * return the object's long value
 */
Long.prototype.longValue=function(){
    return this.value;
};

/**
 * return a string description
 */
Long.prototype.toString=function(){
    return this.value; 
};

/**
 * compare whether this object is equal to input object o
 * @param o
 */
Long.prototype.equals=function(o){
    if(o==undefined){
        return false; 
    }
    if(o.jsjava_class&&o.jsjava_class=="jsjava.lang.Long"){
        return this.value==o.value; 
    }
    return false;
};