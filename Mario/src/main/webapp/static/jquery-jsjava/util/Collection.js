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

 /**  The Collection class references to java.util.Collection of J2SE1.4 */
 
/**
 * constructor
 */
function Collection(){
	this.jsjava_class="jsjava.util.Collection";
}

Collection.CONSTANT_ILLEGAL_INVOCATION="This is an interface method and you should use the concrete method";

/**
 * Increases the capacity of and internally reorganizes this 
 * Collection, in order to accommodate and access its entries 
 * more efficiently.
 */
Collection.prototype.recapacity=function (){
	throw new IllegalStateException(IllegalStateException.ERROR,Collection.CONSTANT_ILLEGAL_INVOCATION);
};

/**
 * Appends the specified element to the end of this Collection
 * @param pvalue
 */
Collection.prototype.add=function (pvalue){
    throw new IllegalStateException(IllegalStateException.ERROR,Collection.CONSTANT_ILLEGAL_INVOCATION); 
};

/**
 * Adds all of the elements in the specified collection to this collection
 * @param pvalue
 */
Collection.prototype.addAll=function (c){
    throw new IllegalStateException(IllegalStateException.ERROR,Collection.CONSTANT_ILLEGAL_INVOCATION); 
};

/**
 * Removes all of the elements from this Collection
 */
Collection.prototype.clear=function (){
    throw new IllegalStateException(IllegalStateException.ERROR,Collection.CONSTANT_ILLEGAL_INVOCATION);
};

/**
 * Returns true if this Collection contains the specified element.
 * @param pvalue
 */
Collection.prototype.contains=function (pvalue){
    throw new IllegalStateException(IllegalStateException.ERROR,Collection.CONSTANT_ILLEGAL_INVOCATION);
};

/**
 * Returns true if this collection contains all of the elements in the specified collection.
 * @param c
 */
Collection.prototype.containsAll=function (c){
    throw new IllegalStateException(IllegalStateException.ERROR,Collection.CONSTANT_ILLEGAL_INVOCATION);
};

/**
 * Returns the element at the specified position in this Collection.
 * @param index
 */
Collection.prototype.get=function (index){
    throw new IllegalStateException(IllegalStateException.ERROR,Collection.CONSTANT_ILLEGAL_INVOCATION);
};

/**
 * Returns true if this Collection contains no elements.
 */
Collection.prototype.isEmpty=function (){
    throw new IllegalStateException(IllegalStateException.ERROR,Collection.CONSTANT_ILLEGAL_INVOCATION);
};

/**
 * Returns an iterator over the elements in this Collection in proper sequence.
 */
Collection.prototype.iterator=function (){
    throw new IllegalStateException(IllegalStateException.ERROR,Collection.CONSTANT_ILLEGAL_INVOCATION);
};

/**
 * Removes the first occurrence in this Collection of the specified element
 * @param pvalue
 */
Collection.prototype.remove=function (pvalue){
    throw new IllegalStateException(IllegalStateException.ERROR,Collection.CONSTANT_ILLEGAL_INVOCATION);
};

/**
 * Removes all this collection's elements that are also contained in the specified collection
 * @param c
 */
Collection.prototype.removeAll=function (c){
    throw new IllegalStateException(IllegalStateException.ERROR,Collection.CONSTANT_ILLEGAL_INVOCATION);
};

/**
 * Retains only the elements in this collection that are contained in the specified collection
 * @param c
 */
Collection.prototype.retainAll=function (c){
    throw new IllegalStateException(IllegalStateException.ERROR,Collection.CONSTANT_ILLEGAL_INVOCATION);
};

/**
 * Returns the number of elements in this Collection. 
 */
Collection.prototype.getSize=function (){
    throw new IllegalStateException(IllegalStateException.ERROR,Collection.CONSTANT_ILLEGAL_INVOCATION);
};

/**
 * Returns the number of elements in this Collection. 
 */
Collection.prototype.size=function (){
    throw new IllegalStateException(IllegalStateException.ERROR,Collection.CONSTANT_ILLEGAL_INVOCATION);
};

/**
 * Returns an array containing all of the elements 
 * in this Collection in proper sequence. 
 */
Collection.prototype.toArray=function (){
    throw new IllegalStateException(IllegalStateException.ERROR,Collection.CONSTANT_ILLEGAL_INVOCATION);
};
