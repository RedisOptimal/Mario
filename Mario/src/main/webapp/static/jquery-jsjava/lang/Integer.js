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

/**  The Integer class references to java.lang.Integer of J2SE1.4 */
 
/**
 * constructor
 * @param value
 */
function Integer(value){
	this.jsjava_class="jsjava.lang.Integer";
    this.value=value;
}
Integer.MIN=-Math.pow(2,31);
Integer.MAX=Math.pow(2,31)-1;
Integer.MIN_VALUE=-Math.pow(2,31);
Integer.MAX_VALUE=Math.pow(2,31)-1;
Integer.digits = [
	'0' , '1' , '2' , '3' , '4' , '5' ,
	'6' , '7' , '8' , '9' , 'a' , 'b' ,
	'c' , 'd' , 'e' , 'f' , 'g' , 'h' ,
	'i' , 'j' , 'k' , 'l' , 'm' , 'n' ,
	'o' , 'p' , 'q' , 'r' , 's' , 't' ,
	'u' , 'v' , 'w' , 'x' , 'y' , 'z'
    ];

/**
 * check whether the input value is a int value
 * @param d
 */
Integer.checkValid=function(i){
	if(isNaN(i)){
		return false;
	}
	if(typeof(i)=="number"){
		if(Math.floor(i)!=i){
			return false;
		}
	}else{
		if(i.indexOf(".")!=-1){
			return false;
		}
	}
	i=parseInt(i);
	if(i<=Integer.MAX&&i>=Integer.MIN){
    	return true;
    }
    return false;
};

/**
 * return the int value parsed from str
 * @param str
 */
Integer.parseInt=function(str){
    if(isNaN(str)){
		throw new NumberFormatException(NumberFormatException.NOT_NUMBER,"Not a number Exception!");
	}
    var i=parseInt(str);
    if(!Integer.checkValid(i)){
        return;
    }
    return i;
};

/**
 * compare whether this object value is equals to input Integer object value
 * @param b input Integer object
 */
Integer.prototype.compareTo=function(b){
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
 * return the object's int value
 */
Integer.prototype.intValue=function(){
    return this.value; 
};

/**
 * return a string description
 */
Integer.prototype.toString=function(){
    return this.value; 
};

/**
 * compare whether this object is equal to input object o
 * @param o
 */
Integer.prototype.equals=function(o){
    if(o==undefined){
        return false; 
    }
    if(o.jsjava_class&&o.jsjava_class=="jsjava.lang.Integer"){
        return this.value==o.value; 
    }
    return false;
};

/**
 * Returns a string representation of the integer argument as an unsigned integer in base 16
 * @param i an integer to be converted to a string
 */
Integer.toHexString=function(i){
    return Integer.toUnsignedString(i, 4);
};

/**
 * Returns a string representation of the integer argument as an unsigned integer in base 8
 * @param i an integer to be converted to a string
 */
Integer.toOctalString=function(i){
    return Integer.toUnsignedString(i, 3);
};

/**
 * Returns a string representation of the integer argument as an unsigned integer in base 2
 * @param i an integer to be converted to a string
 */
Integer.toBinaryString=function(i){
    return Integer.toUnsignedString(i, 1);
};

/**
 * Convert the integer to an unsigned number
 */
Integer.toUnsignedString=function(i,shift){
	var buf = new Array(32);
	var charPos = 32;
	var radix = 1 << shift;
	var mask = radix - 1;
	do {
	    buf[--charPos] = Integer.digits[i & mask];
	    i >>>= shift;
	} while (i != 0);
	var count=32 - charPos;
	var carray=new Array(count);
	for(var j=0;j<count;j++){
		carray[j]=buf[j+charPos];
	}
	return carray.join("");
};