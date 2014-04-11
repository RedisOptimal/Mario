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

 /**  The TreeSet class references to java.util.TreeSet of J2SE1.4 */
 
/**
 * constructor
 */
function TreeSet(comparator){
	this.jsjava_class="jsjava.util.TreeSet";
    this.capacity=50;
    this._size=0;
    this.span=10;
    this._elements=new Array(this.capacity);
    this.comparator=comparator;
}

TreeSet.prototype=new SortedSet();
TreeSet.prototype.constructor=TreeSet;

/**
 * Increases the capacity of and internally reorganizes this 
 * set, in order to accommodate and access its entries 
 * more efficiently.
 */
TreeSet.prototype.recapacity=function (){
    if(this.capacity-this._size<10){
     this.capacity+=this.span;
     var oldElements=this._elements;
        this._elements=new Array(this.capacity); 
        for(var i=0;i<this._size;i++){
            this._elements[i]=oldElements[i]; 
        }
    }
};

TreeSet.prototype.sort=function(){	
	function _sort(o1,o2){
		return c.compare(o1,o2);
	}
	var c=this.comparator;
	if(c==null){
		this._elements.sort();
		return;
	}
	this._elements.sort(_sort);
};

/**
 * Adds the specified element to this set if it is not already present
 */
TreeSet.prototype.add=function (pvalue){
    if(this.contains(pvalue)){
    	return;
    }
    this.recapacity();
    this._elements[this._size++]=pvalue;
    this.sort(); 
};

/**
 * Appends all of the elements in the specified collection to the end of this set
 * @param c
 */
TreeSet.prototype.addAll=function (c){
    if(!(c instanceof Collection)){
    	return;
    }
    var size=c.size();
    for(var i=0;i<size;i++){
    	this.add(c.get(i));
    }
    this.sort();
};

/**
 * Removes all of the elements from this set.
 */
TreeSet.prototype.clear=function (){
    this._elements=new Array(this.capacity); 
};

/**
 * Returns the comparator associated with this sorted set, or null if it uses its elements' natural ordering.
 */
TreeSet.prototype.comparator=function (){
    return this.comparator;
};

/**
 * Returns true if this set contains the specified element.
 * @param pvalue
 */
TreeSet.prototype.contains=function (pvalue){
    for(var i=0;i<this._size;i++){
     var elem=this._elements[i];
        if(elem.equals(pvalue)){
            return true; 
        } 
    } 
    return false;
};

/**
 * Returns true if this collection contains all of the elements in the specified collection.
 * @param c
 */
TreeSet.prototype.containsAll=function (c){
    if(!(c instanceof Collection)){
    	return false;
    }
    var size=c.size();
    for(var i=0;i<size;i++){
    	var elem=c.get(i);
    	if(!this.contains(elem)){
    		return false;
    	}
    }
    return true;
};

/**
 * Returns the first (lowest) element currently in this sorted set.
 * @param pvalue
 */
TreeSet.prototype.first=function (pvalue){
    return this._elements[0];
};

/**
 * Returns a view of the portion of this sorted set whose elements are strictly less than toElement.
 * @param c
 */
TreeSet.prototype.headSet=function (toElement){
    var arr=new HashSet();
    var toIndex=this.indexOf(toElement);
    for(var i=0;i<toIndex;i++){
    	var elem=this._elements[i];
    	arr[i]=elem;
    }
    return arr;
};

/**
 * Returns the element at the specified position in this set.
 * @param index
 */
TreeSet.prototype.get=function (index){
    return this._elements[index];
};

/**
 * Returns the index in this list of the first occurrence of the 
 * specified element, or -1 if this list does not contain this element.
 * @param pvalue
 */
TreeSet.prototype.indexOf=function (pvalue){
    for(var i=0;i<this._size;i++){
     var elem=this._elements[i];
        if(elem.equals(pvalue)){
            return i; 
        } 
    } 
    return -1;
};

/**
 * Returns true if this set contains no elements.
 */
TreeSet.prototype.isEmpty=function (){
    return this._size==0;
};

/**
 * Returns an iterator over the elements in this set in proper sequence.
 */
TreeSet.prototype.iterator=function (){
	function Iterator(list){		
	    this.list=list;
	    this.nextIndex=0;
	    this.size=list.getSize();
	}
	
	/**
	 * Returns true if the iteration has more elements.
	 */
	Iterator.prototype.hasNext=function(){
	    return this.nextIndex<this.size;
	};
	
	/**
	 * Returns the next element in the iteration.
	 */
	Iterator.prototype.next=function(){
	    var nextObj;
	    if(this.nextIndex<this.size){
	        nextObj=this.list.get(this.nextIndex);
	        this.nextIndex++;
	        return nextObj;
	    }
	    return null;
	};
	
	/**
	 * Move the opertion position to the specified index
	 */
	Iterator.prototype.moveTo=function(index){
	    this.nextIndex=index;	
	};
    return new Iterator(this);
};

/**
 * Returns the last (highest) element currently in this sorted set.
 */
TreeSet.prototype.last=function (){
    return this._elements[this._size-1];
};

/**
 * Removes the first occurrence in this set of the specified element
 * @param pvalue
 */
TreeSet.prototype.remove=function (pvalue){
    var arr=new Array();
    for(var i=0;i<this._size;i++){
    	var elem=this.get(i);
    	if(!elem.equals(pavalue)){
    		arr[arr.length]=elem;
    	}
    }
    this.clear();
    for(var i=0;i<arr.length;i++){
    	this.add(arr[i]);
    }
};

/**
 * Removes from this set all of its elements that are contained in the specified collection
 * @param c
 */
TreeSet.prototype.removeAll=function (c){
    if(!(c instanceof Collection)){
    	return;
    }
    var size=c.size();
    for(var i=0;i<size;i++){
    	var elem=c.get(i);
    	this.remove(elem);
    }
};

/**
 * Retains only the elements in this set that are contained in the specified collection
 * @param c
 */
TreeSet.prototype.retainAll=function (c){
    if(!(c instanceof Collection)){
    	return;
    }
    for(var i=0;i<this._size;i++){
    	var elem=this.get(i);
    	if(!c.contains(elem)){
    		this.remove(elem);
    	}
    }
};

/**
 * Returns the number of elements in this set. 
 */
TreeSet.prototype.getSize=function (){
    return this._size;
};

/**
 * Returns the number of elements in this set. 
 */
TreeSet.prototype.size=function (){
    return this._size;
};

/**
 * Returns a view of the portion of this sorted set whose elements range from fromElement, inclusive, to toElement, exclusive.
 * @param pvalue
 */
TreeSet.prototype.subSet=function (fromElement,toElement){
	var set=new TreeSet();
    var fromIndex=this.indexOf(fromElement);
    var toIndex=this.indexOf(toElement);
    if(fromIndex==-1||toIndex==-1||fromIndex>toIndex){
    	return set;
    }
    for(var i=fromIndex;i<toIndex;i++){
    	var elem=this._elements[i];
    	set.add(elem);
    }
    return set;
};

/**
 * Returns a view of the portion of this sorted set whose elements are greater than or equal to fromElement.
 * @param c
 */
TreeSet.prototype.tailSet=function (fromElement){
	var set=new HashSet();
    var fromIndex=this.indexOf(fromElement);
    if(fromIndex===-1){
    	return set;
    }
    for(var i=fromIndex;i<this._size;i++){
    	var elem=this._elements[i];
    	set.add(elem);
    }
    return set;
};

/**
 * Returns an array containing all of the elements 
 * in this set in proper sequence. 
 */
TreeSet.prototype.toArray=function (){
    var arr=new Array(this._size);
    for(var i=0;i<this._size;i++){
        arr[i]=this._elements[i]; 
    } 
    return arr;
};

/**
 * Returns a string representation of this TreeSet object
 */
TreeSet.prototype.toString=function (){
    return this.toArray().toString(); 
};