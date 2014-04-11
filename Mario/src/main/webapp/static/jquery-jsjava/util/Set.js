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

 /**  The Set class references to java.util.Set of J2SE1.4 */
 
/**
 * constructor
 */
function Set(){
	this.jsjava_class="jsjava.util.Set";
}

Set.prototype=new Collection();
Set.prototype.constructor=Set;

Set.CONSTANT_ILLEGAL_INVOCATION="This is an interface method and you should use the concrete method";

/**
 * Increases the capacity of and internally reorganizes this 
 * set, in order to accommodate and access its entries 
 * more efficiently.
 */
Set.prototype.recapacity=function (){
    throw new IllegalStateException(IllegalStateException.ERROR,Set.CONSTANT_ILLEGAL_INVOCATION);
};

/**
 * Adds the specified element to this set if it is not already present
 * @param pvalue
 */
Set.prototype.add=function (pvalue){
    throw new IllegalStateException(IllegalStateException.ERROR,Set.CONSTANT_ILLEGAL_INVOCATION);    
};

/**
 * Adds all of the elements in the specified collection to this set if they're not already present
 * @param c
 */
Set.prototype.addAll=function (c){
    throw new IllegalStateException(IllegalStateException.ERROR,Set.CONSTANT_ILLEGAL_INVOCATION);    
};

/**
 * Removes all of the elements from this set.
 */
Set.prototype.clear=function (){
    throw new IllegalStateException(IllegalStateException.ERROR,Set.CONSTANT_ILLEGAL_INVOCATION);
};

/**
 * Returns true if this set contains the specified element.
 * @param pvalue
 */
Set.prototype.contains=function (pvalue){
    throw new IllegalStateException(IllegalStateException.ERROR,Set.CONSTANT_ILLEGAL_INVOCATION);
};

/**
 * Returns true if this set contains all of the elements of the specified collection.
 * @param c
 */
Set.prototype.containsAll=function (c){
    throw new IllegalStateException(IllegalStateException.ERROR,Set.CONSTANT_ILLEGAL_INVOCATION);
};

/**
 * Returns true if this set contains no elements.
 */
Set.prototype.isEmpty=function (){
    throw new IllegalStateException(IllegalStateException.ERROR,Set.CONSTANT_ILLEGAL_INVOCATION);
};

/**
 * Returns an iterator over the elements in this set in proper sequence.
 */
Set.prototype.iterator=function (){
    throw new IllegalStateException(IllegalStateException.ERROR,Set.CONSTANT_ILLEGAL_INVOCATION);
};

/**
 * Removes the first occurrence in this set of the specified element
 * @param pvalue
 */
Set.prototype.remove=function (pvalue){
    throw new IllegalStateException(IllegalStateException.ERROR,Set.CONSTANT_ILLEGAL_INVOCATION);
};

/**
 * Removes from this set all of its elements that are contained in the specified collection
 * @param c
 */
Set.prototype.removeAll=function (c){
    throw new IllegalStateException(IllegalStateException.ERROR,Set.CONSTANT_ILLEGAL_INVOCATION);
};

/**
 * Retains only the elements in this set that are contained in the specified collection
 * @param c
 */
Set.prototype.retainAll=function (c){
    throw new IllegalStateException(IllegalStateException.ERROR,Set.CONSTANT_ILLEGAL_INVOCATION);
};

/**
 * Returns the number of elements in this set. 
 */
Set.prototype.getSize=function (){
    throw new IllegalStateException(IllegalStateException.ERROR,Set.CONSTANT_ILLEGAL_INVOCATION);
};

/**
 * Returns the number of elements in this set. 
 */
Set.prototype.size=function (){
    throw new IllegalStateException(IllegalStateException.ERROR,Set.CONSTANT_ILLEGAL_INVOCATION);
};

/**
 * Returns an array containing all of the elements 
 * in this set in proper sequence. 
 */
Set.prototype.toArray=function (){
    throw new IllegalStateException(IllegalStateException.ERROR,Set.CONSTANT_ILLEGAL_INVOCATION);
};

/**
 * Returns a string representation of this Set object
 */
Set.prototype.toString=function (){
    throw new IllegalStateException(IllegalStateException.ERROR,Set.CONSTANT_ILLEGAL_INVOCATION); 
};
