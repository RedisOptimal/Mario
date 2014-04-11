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

 /**  The SortedMap class references to java.util.SortedMap of J2SE1.4 */
 
/**
 * constructor
 */
function SortedMap(){
	this.jsjava_class="jsjava.util.SortedMap";
}

SortedMap.prototype=new Map();
SortedMap.prototype.constructor=SortedMap;

SortedMap.CONSTANT_ILLEGAL_INVOCATION="This is an interface method and you should use the concrete method";

/**
 * Returns the comparator associated with this sorted map, or null if it uses its keys' natural ordering.
 */
SortedMap.prototype.comparator=function(){
    throw new IllegalStateException(IllegalStateException.ERROR,SortedMap.CONSTANT_ILLEGAL_INVOCATION);
};

/**
 * Returns the first (lowest) key currently in this sorted map 
 */
SortedMap.prototype.firstKey=function(){
    throw new IllegalStateException(IllegalStateException.ERROR,SortedMap.CONSTANT_ILLEGAL_INVOCATION); 
};

/**
 * Returns a view of the portion of this sorted map whose keys are strictly less than toKey
 * @param toKey
 */
SortedMap.prototype.headMap=function (toKey){
    throw new IllegalStateException(IllegalStateException.ERROR,SortedMap.CONSTANT_ILLEGAL_INVOCATION);   
};

/**
 * Returns the last (highest) key currently in this sorted map
 */
SortedMap.prototype.lastKey=function (){
    throw new IllegalStateException(IllegalStateException.ERROR,SortedMap.CONSTANT_ILLEGAL_INVOCATION);   
};

/**
 * Returns a view of the portion of this sorted map whose keys range from fromKey, inclusive, to toKey, exclusive
 * @param fromKey
 * @param toKey
 */
SortedMap.prototype.subMap=function (fromKey,toKey){
    throw new IllegalStateException(IllegalStateException.ERROR,SortedMap.CONSTANT_ILLEGAL_INVOCATION);
};

/**
 * Returns a view of the portion of this sorted map whose keys are greater than or equal to fromKey.
 * @param fromKey
 */
SortedMap.prototype.tailMap=function(fromKey){
    throw new IllegalStateException(IllegalStateException.ERROR,SortedMap.CONSTANT_ILLEGAL_INVOCATION);
};