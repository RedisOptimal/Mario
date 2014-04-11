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

/**
 * constructor
 */
function HTMLTextDevice(id){
	this.jsjava_class="jsjava.io.HTMLTextDevice";
	var textObj=document.getElementById(id);
	if(textObj){
		this.textObj=textObj;
	}else{
		throw new IOException(IOException.ERROR,"Text device not found!");
	}
}

HTMLTextDevice.prototype=new OutputDevice();
HTMLTextDevice.prototype.constructor=HTMLTextDevice;

/**
 * Print a string to HTML text input field,and then terminate the line.
 * @param str
 * @param append if true, then bytes will be written to the end of the file rather than the beginning.
 */
HTMLTextDevice.prototype.println=function(str,append){
    if(this.textObj.value!=""){
    	this.textObj.value+="\n";
    }
	if(append&&append==true){
		this.textObj.value+=str;
	}else{
		this.textObj.value=str;
	}
};

/**
 * Print a string to HTML text input field.
 * @param str
 * @param append if true, then bytes will be written to the end of the file rather than the beginning.
 */
HTMLTextDevice.prototype.print=function(str,append){
    if(append&&append==true){
		this.textObj.value+=str;
	}else{
		this.textObj.value=str;
	}
};