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

/**  The Class class references to java.lang.Class of J2SE1.4 */
 
/**
 * constructor
 * @param value 
 */
function Class(className){
	this.jsjava_class="jsjava.lang.Class";
	this.className=className;
	var flag=true;
	if(className==undefined){
		flag=false;
	}
	var c=null;
	try{
		c=eval("new "+className+"()");
		if(!c.jsjava_class){
			flag=false;
		}
	}catch(e){
		flag=false;		
	}
	if(!flag){
		throw new ClassNotFoundException(ClassNotFoundException.ERROR,"Class "+className+" is not found!");
	}
	this.instance=c;
}

/**
 * Returns the Class object associated with the class or interface with the given string name
 * @param className
 */
Class.forName=function(className){	
	return new Class(className);
};

/**
 * Returns a Constructor object that reflects the specified public constructor of the class represented by this Class object
 * @param parameterTypes
 */
Class.prototype.getConstructor=function(parameterTypes){
    return new Constructor(this.className,parameterTypes);
};

/**
 * Creates a new instance of the class represented by this Class object.
 */
Class.prototype.newInstance=function(){
    return this.instance;
};

/**
 * Returns the name of the entity.
 */
Class.prototype.getName=function(){
    return this.className;
};

/**
 * return a string description
 */
Class.prototype.toString=function(){
    return this.className;
};