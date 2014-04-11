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

 /**  The StringTokenizer class references to java.util.StringTokenizer of J2SE1.4 */
 
/**
 * constructor
 * @param str
 * @param delim
 */
function StringTokenizer(str,delim){
	this.jsjava_class="jsjava.util.StringTokenizer";
    this.str=str;
    this.delim=delim;
    this.elements=str.split(delim);
    this.size=0;
    if(this.elements){
        this.size=this.elements.length;
    }
    this.nextIndex=0;
}

StringTokenizer.prototype=new Enumeration();
StringTokenizer.prototype.constructor=StringTokenizer;

/**
 * Calculates the number of times that this tokenizer's nextToken 
 * method can be called before it generates an exception.
 */
StringTokenizer.prototype.countTokens=function(){
	return this.size;
};

/**
 * Returns the same value as the hasMoreTokens method.
 */
StringTokenizer.prototype.hasMoreElements=function(){
	return this.nextIndex<this.size;
};

/**
 * Tests if there are more tokens available from this tokenizer's string.
 */
StringTokenizer.prototype.hasMoreTokens=function(){
	return this.nextIndex<this.size;
};

/**
 * Returns the same value as the nextToken method, except that its declared 
 * return value is Object rather than String.
 */
StringTokenizer.prototype.nextElement=function(){
	return this.elements[this.nextIndex++];
	
};

/**
 * Returns the next token from this string tokenizer.
 */
StringTokenizer.prototype.nextToken=function(){
	return this.elements[this.nextIndex++];
};

/**
 * Returns the next token in this string tokenizer's string.
 */
StringTokenizer.prototype.nextToken=function(delim){
	return this.str.split(delim)[this.nextIndex];
};