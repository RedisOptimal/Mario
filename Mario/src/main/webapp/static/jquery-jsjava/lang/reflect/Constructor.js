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

/**  The Constructor class references to java.lang.reflect.Constructor of J2SE1.4 */
 
/**
 * constructor
 * @param value 
 */
function Constructor(className,parameterTypes){
	this.jsjava_class="jsjava.lang.reflect.Constructor";
	this.className=className;
	this.parameterTypes=parameterTypes;
	if(parameterTypes==undefined||!(parameterTypes instanceof Array)){
		this.parameterTypes=[];
	}
}

/**
 * Uses the constructor represented by this Constructor object to create and initialize a new instance.
 * @param className
 */
Constructor.prototype.newInstance=function(initargs){	
	this.initargs=initargs;
	if(initargs==undefined||!(initargs instanceof Array)){
		this.initargs=[];
	}
	var l1=this.initargs.length;
	var l2=this.parameterTypes.length;
	if(l1<l2){
		for(var i=l1;i<l2;i++){
			this.initargs[i]=undefined;
		}		
	}
	var instanceStr="new "+this.className+"(";
	for(var i=0;i<l2;i++){
		instanceStr+="this.initargs["+i+"],";
	}
	if(instanceStr.lastIndexOf(",")==instanceStr.length-1){
		instanceStr=instanceStr.substring(0,instanceStr.length-1);
	}
	instanceStr+=")";
	var instance=eval(instanceStr);
	return instance;
};

/**
 * Returns the name of this constructor, as a string
 */
Constructor.prototype.getName=function(){
    return this.className;
};

/**
 * Returns an array of Class objects that represent the formal parameter types
 */
Constructor.prototype.getParameterTypes=function(){
    return this.parameterTypes;
};

/**
 * Returns the Class object representing the class that declares the constructor represented by this Constructor object
 */
Constructor.prototype.getDeclaringClass=function(){
    return this.className;
};