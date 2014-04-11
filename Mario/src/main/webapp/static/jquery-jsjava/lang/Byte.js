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

/**  The Byte class references to java.lang.Byte of J2SE1.4 */
 
/**
 * constructor
 * @param value 
 */
function Byte(value){
	this.jsjava_class="jsjava.lang.Byte";
    this.value=value;
}
Byte.MIN=-Math.pow(2,7);
Byte.MAX=Math.pow(2,7)-1;
Byte.MIN_VALUE=-Math.pow(2,7);
Byte.MAX_VALUE=Math.pow(2,7)-1;

/**
 * check whether the input value is a byte value
 * @param b
 */
Byte.checkValid=function(b){
	if(isNaN(b)){
		return false;
	}
	if(typeof(b)=="number"){
		if(Math.floor(b)!=b){
			return false;
		}
	}else{
		if(b.indexOf(".")!=-1){
			return false;
		}
	}
	b=parseInt(b);
	if(b<=Byte.MAX&&b>=Byte.MIN){
    	return true;
    }
    return false;
};

/**
 * create a new Byte object with str
 * @param str
 */
Byte.parseByte=function(str){
    if(isNaN(str)){
		throw new NumberFormatException(NumberFormatException.NOT_NUMBER,"Not a number Exception!");
	} 
    var b=parseInt(str);
    if(!Byte.checkValid(b)){
        return;
    }
    return b;
};

/**
 * compare whether this object value is equals to input Boolean object value
 * @param b input Byte object
 */
Byte.prototype.compareTo=function(b){
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
 * return the object's byte value
 */
Byte.prototype.byteValue=function(){
    return this.value; 
};

/**
 * return a string description
 */
Byte.prototype.toString=function(){
    return this.value; 
};

/**
 * compare whether this object is equal to input object o
 * @param o
 */
Byte.prototype.equals=function(o){
    if(o==undefined){
        return false; 
    }
    if(o.jsjava_class&&o.jsjava_class=="jsjava.lang.Byte"){
        return this.value==o.value; 
    }
    return false;
};