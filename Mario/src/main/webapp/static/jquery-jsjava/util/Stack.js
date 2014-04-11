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

 /**  The Stack class references to java.util.Stack of J2SE1.4 */
 
/**
 * constructor
 */
function Stack(){
	this.jsjava_class="jsjava.util.Stack";
	this.capacity=50;
    this.size=0;
    this.span=10;
    this.elements=new Array(this.capacity);
}

Stack.prototype=new Vector();
Stack.prototype.constructor=Stack;
Stack.MESSAGE_NOTFOUND=-1;

/**
 * Increases the capacity of and internally reorganizes this 
 * stack, in order to accommodate and access its entries 
 * more efficiently.
 */
Stack.prototype.recapacity=function (){
    if(this.capacity-this.size<10){
     this.capacity+=this.span;
     var oldElements=this.elements;
        this.elements=new Array(this.capacity); 
        for(var i=0;i<this.size;i++){
            this.elements[i]=oldElements[i]; 
        }
    }
};

/**
 * Pushes an item onto the top of this stack.
 * @param obj
 */
Stack.prototype.push=function(obj){
	this.recapacity();
	this.elements[this.size++]=obj;
};

/**
 * Removes the object at the top of this stack and returns that object 
 * as the value of this function.
 */
Stack.prototype.pop=function(){
	if(this.empty()){
		throw new EmptyStackException(EmptyStackException.ERROR,"Stack has been empty!");
	}
    var oldElems=this.elements;
	this.elements=new Array(this.capacity);
	for(var i=0;i<this.size-1;i++){
	    this.elements[i]=oldElems[i];
	}
	this.size--;
	return oldElems[this.size];
};

/**
 * Looks at the object at the top of this stack without removing 
 * it from the stack
 */
Stack.prototype.peek=function(){
    return this.elements[this.size-1];
};

/**
 * Tests if this stack is empty.
 */
Stack.prototype.empty=function(){
	return this.size==0;
};

/**
 * Returns the 1-based position where an object is on this stack.
 */
Stack.prototype.search=function(obj){
	for(var i=0;i<this.size;i++){
	    if(this.elements[i].equals(obj)){
	        return i+1;
	    }
	}
	return Stack.MESSAGE_NOTFOUND;
};