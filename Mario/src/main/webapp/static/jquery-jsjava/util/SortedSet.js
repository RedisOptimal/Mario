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

 /**  The SortedSet class references to java.util.SortedSet of J2SE1.4 */
 
/**
 * constructor
 */
function SortedSet(){
	this.jsjava_class="jsjava.util.SortedSet";
}

SortedSet.prototype=new Set();
SortedSet.prototype.constructor=SortedSet;

SortedSet.CONSTANT_ILLEGAL_INVOCATION="This is an interface method and you should use the concrete method";

/**
 * Returns the comparator associated with this sorted set, or null if it uses its elements' natural ordering.
 */
SortedSet.prototype.comparator=function (){
    throw new IllegalStateException(IllegalStateException.ERROR,SortedSet.CONSTANT_ILLEGAL_INVOCATION);
};

/**
 * Returns the first (lowest) element currently in this sorted set.
 * @param pvalue
 */
SortedSet.prototype.first=function (pvalue){
    throw new IllegalStateException(IllegalStateException.ERROR,SortedSet.CONSTANT_ILLEGAL_INVOCATION);    
};

/**
 * Returns a view of the portion of this sorted set whose elements are strictly less than toElement.
 * @param c
 */
SortedSet.prototype.headSet=function (toElement){
    throw new IllegalStateException(IllegalStateException.ERROR,SortedSet.CONSTANT_ILLEGAL_INVOCATION);    
};

/**
 * Returns the last (highest) element currently in this sorted set.
 */
SortedSet.prototype.last=function (){
    throw new IllegalStateException(IllegalStateException.ERROR,SortedSet.CONSTANT_ILLEGAL_INVOCATION);
};

/**
 * Returns a view of the portion of this sorted set whose elements range from fromElement, inclusive, to toElement, exclusive.
 * @param pvalue
 */
SortedSet.prototype.subSet=function (fromElement,toElement){
    throw new IllegalStateException(IllegalStateException.ERROR,SortedSet.CONSTANT_ILLEGAL_INVOCATION);
};

/**
 * Returns a view of the portion of this sorted set whose elements are greater than or equal to fromElement.
 * @param c
 */
SortedSet.prototype.tailSet=function (fromElement){
    throw new IllegalStateException(IllegalStateException.ERROR,SortedSet.CONSTANT_ILLEGAL_INVOCATION);
};