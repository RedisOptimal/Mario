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

 /**  The ListIterator class references to java.util.ListIterator of J2SE1.4 */
 
/**
 * constructor
 * @param list a List object
 */
function ListIterator(list){
	this.jsjava_class="jsjava.util.ListIterator";
}

ListIterator.prototype=new Iterator();
ListIterator.prototype.constructor=ListIterator;

ListIterator.CONSTANT_ILLEGAL_INVOCATION="This is an interface method and you should use the concrete method";

/**
 * Inserts the specified element into the list.
 */
ListIterator.prototype.add=function(o){
    throw new IllegalStateException(IllegalStateException.ERROR,ListIterator.CONSTANT_ILLEGAL_INVOCATION);
};

/**
 * Returns true if the iteration has more elements.
 */
ListIterator.prototype.hasNext=function(){
    throw new IllegalStateException(IllegalStateException.ERROR,ListIterator.CONSTANT_ILLEGAL_INVOCATION);
};

/**
 * Returns true if this list iterator has more elements when traversing the list in the reverse direction.
 */
ListIterator.prototype.hasPrevious=function(){
    throw new IllegalStateException(IllegalStateException.ERROR,ListIterator.CONSTANT_ILLEGAL_INVOCATION);
};

/**
 * Returns the next element in the iteration.
 */
ListIterator.prototype.next=function(){
    throw new IllegalStateException(IllegalStateException.ERROR,ListIterator.CONSTANT_ILLEGAL_INVOCATION);
};

/**
 * Returns the index of the element that would be returned by a subsequent call to next.
 */
ListIterator.prototype.nextIndex=function(){
    throw new IllegalStateException(IllegalStateException.ERROR,ListIterator.CONSTANT_ILLEGAL_INVOCATION);
};

/**
 * Returns the previous element in the list.
 */
ListIterator.prototype.previous=function(){
    throw new IllegalStateException(IllegalStateException.ERROR,ListIterator.CONSTANT_ILLEGAL_INVOCATION);
};

/**
 * Returns the index of the element that would be returned by a subsequent call to previous.
 */
ListIterator.prototype.previousIndex=function(){
    throw new IllegalStateException(IllegalStateException.ERROR,ListIterator.CONSTANT_ILLEGAL_INVOCATION);
};

/**
 * Removes from the list the last element that was returned by next or previous.
 */
ListIterator.prototype.remove=function(index){
    throw new IllegalStateException(IllegalStateException.ERROR,ListIterator.CONSTANT_ILLEGAL_INVOCATION);	
};

/**
 * Replaces the last element returned by next or previous with the specified element.
 */
ListIterator.prototype.set=function(o){
    throw new IllegalStateException(IllegalStateException.ERROR,ListIterator.CONSTANT_ILLEGAL_INVOCATION);	
};