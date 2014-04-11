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

 /**  The AbstractSet class references to java.util.AbstractSet of J2SE1.4 */
 
/**
 * constructor
 */
function AbstractSet(){
	this.jsjava_class="jsjava.util.AbstractSet";
}

AbstractSet.prototype=new Set();
AbstractSet.prototype.constructor=AbstractSet;

/**
 * Increases the capacity of and internally reorganizes this 
 * set, in order to accommodate and access its entries 
 * more efficiently.
 */
AbstractSet.prototype.recapacity=function (){
    if(this.capacity-this._size<10){
     this.capacity+=this.span;
     var oldElements=this.elements;
        this.elements=new Array(this.capacity); 
        for(var i=0;i<this._size;i++){
            this.elements[i]=oldElements[i]; 
        }
    }
};

/**
 * Adds the specified element to this set if it is not already present
 */
AbstractSet.prototype.add=function (pvalue){
    if(this.contains(pvalue)){
    	return;
    }
    this.recapacity();
    this.elements[this._size++]=pvalue;    
};

/**
 * Appends all of the elements in the specified collection to the end of this set
 * @param c
 */
AbstractSet.prototype.addAll=function (c){
    if(!(c instanceof Collection)){
    	return;
    }
    var size=c.size();
    for(var i=0;i<size;i++){
    	this.add(c.get(i));
    }
};

/**
 * Removes all of the elements from this set.
 */
AbstractSet.prototype.clear=function (){
    this.elements=new Array(this.capacity); 
};

/**
 * Returns true if this set contains the specified element.
 * @param pvalue
 */
AbstractSet.prototype.contains=function (pvalue){
    for(var i=0;i<this._size;i++){
     var elem=this.elements[i];
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
AbstractSet.prototype.containsAll=function (c){
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
 * Returns the element at the specified position in this set.
 * @param index
 */
AbstractSet.prototype.get=function (index){
    return this.elements[index];
};

/**
 * Returns true if this set contains no elements.
 */
AbstractSet.prototype.isEmpty=function (){
    return this._size==0;
};

/**
 * Returns an iterator over the elements in this set in proper sequence.
 */
AbstractSet.prototype.iterator=function (){
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
 * Removes the first occurrence in this set of the specified element
 * @param pvalue
 */
AbstractSet.prototype.remove=function (pvalue){
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
AbstractSet.prototype.removeAll=function (c){
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
AbstractSet.prototype.retainAll=function (c){
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
AbstractSet.prototype.getSize=function (){
    return this._size;
};

/**
 * Returns the number of elements in this set. 
 */
AbstractSet.prototype.size=function (){
    return this._size;
};

/**
 * Returns an array containing all of the elements 
 * in this set in proper sequence. 
 */
AbstractSet.prototype.toArray=function (){
    var arr=new Array(this._size);
    for(var i=0;i<this._size;i++){
        arr[i]=this.elements[i]; 
    } 
    return arr;
};

/**
 * Returns a string representation of this AbstractSet object
 */
AbstractSet.prototype.toString=function (){
    return this.toArray().toString(); 
};
