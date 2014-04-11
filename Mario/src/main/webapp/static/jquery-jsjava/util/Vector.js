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

 /**  The Vector class references to java.util.Vector of J2SE1.4 */
 
/**
 * constructor
 */
function Vector(){
	this.jsjava_class="jsjava.util.Vector";
	this.capacity=50;
    this._size=0;
    this.span=10;
    this._elements=new Array(this.capacity);
}

Vector.prototype=new AbstractList();
Vector.prototype.constructor=Vector;

/**
 * Adds the specified component to the end of this vector, increasing its size by one
 * @param pvalue
 */
Vector.prototype.addElement=function (pvalue){
    this.recapacity();
    this._elements[this._size++]=pvalue; 
};

/**
 * Returns the component at the specified index.
 * @param index
 */
Vector.prototype.elementAt=function (index){
    return this._elements[index];
};

/**
 * Returns an enumeration of the components of this vector.
 * @param index
 */
Vector.prototype.elements=function (){
    function _Enumeration(list){
    	if(list==undefined){
			list=new Vector();
		}
		this.list=list;
	    this.nextIndex=0;
	    this.size=list.getSize();
    }
    _Enumeration.prototype=new Enumeration();
    _Enumeration.prototype.constructor=_Enumeration;
    _Enumeration.prototype.hasMoreElements=function(){
	    return this.nextIndex<this.size;
	};	
	_Enumeration.prototype.nextElement=function(){
	    var nextObj;
	    if(this.nextIndex<this.size){
	        nextObj=this.list.get(this.nextIndex);
	        this.nextIndex++;
	        return nextObj;
	    }
	    throw new NoSuchElementException(NoSuchElementException,"Vector Enumeration");
	};
	return new _Enumeration(this);    
};

/**
 * Inserts the specified object as a component in this vector at the specified index
 * @param pvalue
 * @param index
 */
Vector.prototype.insertElementAt=function (pvalue,index){
    this.addIndexOf(index,pvalue);
};

/**
 * Returns the last component of the vector.
 */
Vector.prototype.lastElement=function (){
    return this.get(this._size-1);
};

/**
 * Removes the element at the specified position in this vector
 * @param index
 */
Vector.prototype.removeElementAt=function (index){
    return this.removeIndexOf(index);
};

/**
 * Removes the first (lowest-indexed) occurrence of the argument from this vector
 * @param pvalue
 */
Vector.prototype.removeElement=function (pvalue){
    this.remove(pvalue);
};

/**
 * Removes all components from this vector and sets its size to zero
 */
Vector.prototype.removeAllElements=function (){
    this.capacity=50;
    this._size=0;
    this.span=10;
    this._elements=new Array(this.capacity);
};

/**
 * Sets the component at the specified index of this vector to be the specified object 
 * @param pvalue
 * @param index
 */
Vector.prototype.setElementAt=function (pvalue,index){
    this.set(index,pvalue);
};