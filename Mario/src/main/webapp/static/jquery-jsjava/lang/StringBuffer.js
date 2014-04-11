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

/**  The StringBuffer class references to java.lang.StringBuffer of J2SE1.4 */
 
/**
 * constructor
 * @param str
 */
function StringBuffer(str){
	this.jsjava_class="jsjava.lang.StringBuffer";
	if(str==undefined||str==null){
		str="";
	}
    this.orig=str; 
    this.nStr=str;
}

/**
 * append a string after the current string
 * @param pvalue
 */
StringBuffer.prototype.append=function (pvalue){
    this.nStr+=pvalue; 
};

/**
 * return the length of the current string
 */
StringBuffer.prototype.getLength=function (){
    return this.nStr.length; 
};

/**
 * return the char at specified index
 * @param index
 */
StringBuffer.prototype.charAt=function (index){
    return this.nStr.charAt(index); 
};

/**
 * delete content from index1 to index2
 * @param index1
 * @param index2
 */
StringBuffer.prototype.deleteBetween=function (index1,index2){
    if(index1<index2){
     var str=this.nStr.substring(0,index1)+this.nStr.substring(index2);
     this.nStr=str;
    }else if(index1==index2){
     var str=this.nStr.substring(0,index1)+this.nStr.substring(index1+1);
     this.nStr=str;
    }
    
};

/**
 * return the current string
 */
StringBuffer.prototype.getValue=function (){
    return this.nStr; 
};

/**
 * return a string description
 */
StringBuffer.prototype.toString=function (){
    return this.nStr; 
};

/**
 * delete a char at specified index
 * @param index
 */
StringBuffer.prototype.deleteCharAt=function (index){
    this.deleteBetween(index,index); 
};

/**
 * return the chars array from the current string
 */
StringBuffer.prototype.getChars=function (){
    var chars=new Array(this.getLength()); 
    for(var i=0;i<chars.length;i++){
        chars[i]=this.nStr.charAt(i); 
    }
   return chars;
};

/**
 * return the index of str in the current string
 * @param str
 */
StringBuffer.prototype.indexOf=function (str){
    return this.nStr.indexOf(str); 
};

/**
 * insert a string in specified index
 * @param index
 * @param str
 */
StringBuffer.prototype.insert=function (index,str){
    var s= this.nStr.substring(0,index)+str+this.nStr.substring(index);
    this.nStr=s;
};

/**
 * return the last index of str in the current string
 * @param str
 */
StringBuffer.prototype.lastIndexOf=function (str){
    return this.nStr.lastIndexOf(str); 
};

/**
 * return the substring from index
 * @param index
 */
StringBuffer.prototype.substring=function (index){
    return this.nStr.substring(index); 
};

/**
 * return the substring from index1 to index2
 * @param index1
 * @param index2
 */
StringBuffer.prototype.substringBetween=function (index1,index2){
    return this.nStr.substring(index1,index2); 
};

/**
 * reverse the current string char sequence
 */
StringBuffer.prototype.reverse=function (){
    var str="";
    for(var i=this.getLength()-1;i>=0;i--){
        str+=this.charAt(i); 
    } 
    this.nStr=str;
};
