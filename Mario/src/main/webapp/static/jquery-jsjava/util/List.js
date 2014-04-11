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

 /**  The List class references to java.util.List of J2SE1.4 */
 
/**
 * constructor
 */
function List(){
	this.jsjava_class="jsjava.util.List";
}

List.prototype=new Collection();
List.prototype.constructor=List;

List.CONSTANT_ILLEGAL_INVOCATION="This is an interface method and you should use the concrete method";

/**
 * Increases the capacity of and internally reorganizes this 
 * List, in order to accommodate and access its entries 
 * more efficiently.
 */
List.prototype.recapacity=function (){
	throw new IllegalStateException(IllegalStateException.ERROR,List.CONSTANT_ILLEGAL_INVOCATION);
};

/**
 * Appends the specified element to the end of this List
 * @param pvalue
 */
List.prototype.add=function (pvalue){
    throw new IllegalStateException(IllegalStateException.ERROR,List.CONSTANT_ILLEGAL_INVOCATION); 
};

/**
 * Inserts the specified element at the specified position in this List
 * @param index
 * @param pvalue
 */
List.prototype.addIndexOf=function (index,pvalue){
    throw new IllegalStateException(IllegalStateException.ERROR,List.CONSTANT_ILLEGAL_INVOCATION);
};

/**
 * Appends all of the elements in the specified collection to the end of this List
 * @param c
 */
List.prototype.addAll=function (c){
    throw new IllegalStateException(IllegalStateException.ERROR,List.CONSTANT_ILLEGAL_INVOCATION); 
};

/**
 * Inserts all of the elements in the specified collection into this List at the specified position
 * @param index
 * @param c
 */
List.prototype.addAllIndexOf=function (index,c){
    throw new IllegalStateException(IllegalStateException.ERROR,List.CONSTANT_ILLEGAL_INVOCATION);
};

/**
 * Removes all of the elements from this List
 */
List.prototype.clear=function (){
    throw new IllegalStateException(IllegalStateException.ERROR,List.CONSTANT_ILLEGAL_INVOCATION);
};

/**
 * Returns true if this List contains the specified element.
 * @param pvalue
 */
List.prototype.contains=function (pvalue){
    throw new IllegalStateException(IllegalStateException.ERROR,List.CONSTANT_ILLEGAL_INVOCATION);
};

/**
 * Returns true if this collection contains all of the elements in the specified collection.
 * @param c
 */
List.prototype.containsAll=function (c){
    throw new IllegalStateException(IllegalStateException.ERROR,List.CONSTANT_ILLEGAL_INVOCATION);
};

/**
 * Returns the element at the specified position in this List.
 * @param index
 */
List.prototype.get=function (index){
    throw new IllegalStateException(IllegalStateException.ERROR,List.CONSTANT_ILLEGAL_INVOCATION);
};

/**
 * Returns the index in this List of the first occurrence of the 
 * specified element, or -1 if this List does not contain this element.
 * @param pvalue
 */
List.prototype.indexOf=function (pvalue){
    throw new IllegalStateException(IllegalStateException.ERROR,List.CONSTANT_ILLEGAL_INVOCATION);
};

/**
 * Returns true if this List contains no elements.
 */
List.prototype.isEmpty=function (){
    throw new IllegalStateException(IllegalStateException.ERROR,List.CONSTANT_ILLEGAL_INVOCATION);
};

/**
 * Returns an iterator over the elements in this List in proper sequence.
 */
List.prototype.iterator=function (){
    throw new IllegalStateException(IllegalStateException.ERROR,List.CONSTANT_ILLEGAL_INVOCATION);
};

/**
 * Returns the index in this List of the last occurrence of the specified 
 * element, or -1 if this List does not contain this
 */
List.prototype.lastIndexOf=function (pvalue){
    throw new IllegalStateException(IllegalStateException.ERROR,List.CONSTANT_ILLEGAL_INVOCATION);
};

/**
 * Returns a list iterator of the elements in this list.
 * @param index 
 */
List.prototype.listIterator=function (index){
    throw new IllegalStateException(IllegalStateException.ERROR,List.CONSTANT_ILLEGAL_INVOCATION);
};

/**
 * Removes the element at the specified position in this List
 * @param index
 */
List.prototype.removeIndexOf=function (index){
    throw new IllegalStateException(IllegalStateException.ERROR,List.CONSTANT_ILLEGAL_INVOCATION);
};

/**
 * Removes the first occurrence in this List of the specified element
 * @param pvalue
 */
List.prototype.remove=function (pvalue){
    throw new IllegalStateException(IllegalStateException.ERROR,List.CONSTANT_ILLEGAL_INVOCATION);
};

/**
 * Removes all this List's elements that are also contained in the specified List
 * @param c
 */
List.prototype.removeAll=function (c){
    throw new IllegalStateException(IllegalStateException.ERROR,Collection.CONSTANT_ILLEGAL_INVOCATION);
};

/**
 * Retains only the elements in this List that are contained in the specified List
 * @param c
 */
List.prototype.retainAll=function (c){
    throw new IllegalStateException(IllegalStateException.ERROR,Collection.CONSTANT_ILLEGAL_INVOCATION);
};

/**
 * Replaces the element at the specified position in this List with the 
 * specified element
 * @param index
 * @param pvalue
 */
List.prototype.set=function (index,pvalue){
    throw new IllegalStateException(IllegalStateException.ERROR,List.CONSTANT_ILLEGAL_INVOCATION);
};

/**
 * Returns a view of the portion of this List between the specified 
 * fromIndex, inclusive, and toIndex, exclusive.
 * @param index1
 * @param index2
 */
List.prototype.subList=function (index1,index2){
    throw new IllegalStateException(IllegalStateException.ERROR,List.CONSTANT_ILLEGAL_INVOCATION);
};

/**
 * Returns the number of elements in this List. 
 */
List.prototype.getSize=function (){
    throw new IllegalStateException(IllegalStateException.ERROR,List.CONSTANT_ILLEGAL_INVOCATION);
};

/**
 * Returns the number of elements in this List. 
 */
List.prototype.size=function (){
    throw new IllegalStateException(IllegalStateException.ERROR,List.CONSTANT_ILLEGAL_INVOCATION);
};

/**
 * Returns an array containing all of the elements 
 * in this List in proper sequence. 
 */
List.prototype.toArray=function (){
    throw new IllegalStateException(IllegalStateException.ERROR,List.CONSTANT_ILLEGAL_INVOCATION);
};