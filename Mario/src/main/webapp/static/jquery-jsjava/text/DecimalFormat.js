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
 
 
/**  The DecimalFormat class references to java.text.DecimalFormat of J2SE1.4 */
 
/**
 * constructor
 */
 function DecimalFormat(){
     this.jsjava_class="jsjava.text.DecimalFormat";
 }
 
 DecimalFormat.prototype=new NumberFormat();
 DecimalFormat.prototype.constructor=DecimalFormat;
 
 DecimalFormat.SPECIAL_CHARS=["0",".","-",",","E","%","\u00A4","\u2030"];
 
 /**
 * apply the pattern
 * @param pattern
 */
 DecimalFormat.prototype.applyPattern=function(pattern){
 	 if(pattern==undefined){
 	 	pattern="";
 	 }
 	 function contains(arr,char){
 	 	for(var i=0;i<arr.length;i++){
 	 		if(arr[i]==char){
 	 			return true;
 	 		}
 	 	}
 	 	return false;
 	 }
 	 for(var i=0;i<pattern.length;i++){
 	 	if(!contains(DecimalFormat.SPECIAL_CHARS,pattern.charAt(i))){
 	 		throw new IllegalArgumentException(IllegalArgumentException.ERROR,"Malformed pattern "+pattern);
 	 	}
 	 } 
     this.pattern=pattern;
 };