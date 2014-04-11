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
 
 
/**  The NumberFormat class references to java.text.NumberFormat of J2SE1.4 */
 
/**
 * constructor
 */
 function NumberFormat(){
     this.jsjava_class="jsjava.text.NumberFormat";
 }
 
 NumberFormat.prototype=new Format();
 NumberFormat.prototype.constructor=NumberFormat;
 
 /**
 * format the number
 * @param number
 */
 NumberFormat.prototype.format=function(number){
 	 if(isNaN(number)){
     	throw new IllegalArgumentException(IllegalArgumentException.ERROR,"The argument must be a number");
     }
     var pattern=this.pattern;
     if(pattern==""){
     	return number;
     }
     var strNum=new String(number);
     var numNum=parseFloat(number);
     var isNegative=false;
     if(numNum<0){
     	isNegative=true;
     }
     if(isNegative){
     	strNum=strNum.substring(1,strNum.length);
     	numNum=-numNum;
     }
     var ePos=pattern.indexOf("E");
     var pPos=pattern.indexOf("%");
     if(ePos!=-1&&pPos!=-1){
     	throw new IllegalArgumentException(IllegalArgumentException.ERROR,"Malformed exponential pattern : E and % can not be existed at the same time");
     }
     if(ePos!=-1){
     	if(ePos==pattern.length-1){
     		throw new IllegalArgumentException(IllegalArgumentException.ERROR,"Malformed exponential pattern "+this.pattern);
     	}
     	beStr=pattern.substring(0,ePos);
     	aeStr=pattern.substring(ePos+1);
     	var dPos=beStr.indexOf(".");
     	var dPosOfNum=strNum.indexOf(".");
     	if(dPos!=-1){     		
     		if(dPosOfNum==-1){
     			dPosOfNum=strNum.length-1;
     		}
     		var strNumBuffer=new StringBuffer(strNum);
     		strNumBuffer.deleteCharAt(dPosOfNum);
     		strNumBuffer.insert(dPos,".");
     		var snbStr=strNumBuffer.getValue();
     		var adStrLength=beStr.length-dPos;
     		var snbFixed=new Number(parseFloat(snbStr)).toFixed(adStrLength-1);     		
     		var aeLabel=dPosOfNum-dPos;
	     	if(isNegative){
	     		return "-"+snbFixed+"e"+(aeLabel);
	     	}else{
	     		return snbFixed+"e"+(aeLabel);
	     	}
     	}else{
     		if(dPosOfNum==-1){
     			dPosOfNum=strNum.length-1;
     		}
     		var strNumBuffer=new StringBuffer(strNum);
     		strNumBuffer.deleteCharAt(dPosOfNum);
     		strNumBuffer.insert(beStr.length,".");
     		var snbStr=strNumBuffer.getValue();
     		var adStrLength=beStr.length-beStr.length;
     		var snbFixed=-1;
     		if(adStrLength==0){
     			snbFixed=new Number(parseFloat(snbStr)).toFixed();     		
     		}else{
     			snbFixed=new Number(parseFloat(snbStr)).toFixed(adStrLength-1);
     		}
     		var aeLabel=dPosOfNum-beStr.length;
	     	if(isNegative){
	     		return "-"+snbFixed+"e"+(aeLabel);
	     	}else{
	     		return snbFixed+"e"+(aeLabel);
	     	}
     	}    	
     }
     if(pPos!=-1){
     	if(pPos!=pattern.length-1){
     		throw new IllegalArgumentException(IllegalArgumentException.ERROR,"Malformed exponential pattern "+this.pattern);
     	}
   	 	pattern=pattern.substring(0,pattern.length-1);
   	 	numNum=parseFloat(number)*100;
     	strNum=new String(numNum);
     	if(isNegative){
	     	strNum=strNum.substring(1,strNum.length);
	     	numNum=-numNum;
	    }
   	 }    
     var dPos=pattern.indexOf(".");
   	 var dPosOfNum=strNum.indexOf(".");   	
   	 var result=""; 
   	 if(dPos!=-1){     		
   		if(dPosOfNum==-1){
   			dPosOfNum=strNum.length-1;
   		}
   		var adStrLength=pattern.length-dPos;
   		var snbFixed=new Number(parseFloat(strNum)).toFixed(adStrLength-1);   
   		if(isNegative){
     		result="-"+snbFixed;
     	}else{
     		result=snbFixed;
     	}
   	 }else{
   	 	if(dPosOfNum==-1){
   			dPosOfNum=strNum.length-1;
   		}
   		var snbFixed=new Number(parseFloat(strNum)).toFixed();   
   		if(isNegative){
     		result="-"+snbFixed;
     	}else{
     		result=snbFixed;
     	}
   	 }
   	 if(pPos!=-1){
   	 	result+="%";
   	 }
   	 return result;
 };