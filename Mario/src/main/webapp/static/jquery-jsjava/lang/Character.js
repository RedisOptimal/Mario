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

/**  The Character class references to java.lang.Character of J2SE1.4 */
 
/**
 * constructor
 * @param value 
 */
function Character(value){
	this.jsjava_class="jsjava.lang.Character";
    if(value==undefined||value.length>1){
        return;
    }else{
        this.value=value;
    }
}

/**
 * check whether the input value is a char value
 * @param c
 */
Character.checkValid=function(c){
    var regx=/^[\u0000-\uffff]$/gi;
    return regx.test(c);
};

/**
 * return the object's char value
 */
Character.prototype.charValue=function(){
    return this.value; 
};

/**
 * compare whether this object value is equals to input Character object value
 * @param b input Character object
 */
Character.prototype.compareTo=function(c){
    if(b==undefined){
        return -1; 
    }
    if(this.value==b.value){
        return 1; 
    }else{
        return -1;  
    }
};

/**
 * return a string description
 */
Character.prototype.toString=function(){
    return this.value; 
};

/**
 * compare whether this object is equal to input object o
 * @param o
 */
Character.prototype.equals=function(o){
    if(o==undefined){
        return false; 
    }
    if(o.jsjava_class&&o.jsjava_class=="jsjava.lang.Character"){
        return this.value==o.value; 
    }
    return false;
};

/**
 * return the boolean value whether the input c is a digit
 * @param c
 */
Character.isDigit=function(c){
    return !isNaN(c);
};

/**
 * change the c to lower case
 * @param c
 */
Character.toLowerCase=function(c){
    if(Character.checkValid(c)){
        return c.toLowerCase();
    }
};