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

 /**  The AbstractList class references to java.util.AbstractList of J2SE1.4 */
 
/**
 * constructor
 */
function AbstractList(){
	this.jsjava_class="jsjava.util.AbstractList";    
}

AbstractList.prototype=new List();
AbstractList.prototype.constructor=AbstractList;

AbstractList.CONSTANT_ILLEGAL_INVOCATION="This is an abstract method and you should use the concrete method";

/**
 * Increases the capacity of and internally reorganizes this 
 * list, in order to accommodate and access its entries 
 * more efficiently.
 */
AbstractList.prototype.recapacity=function (){
    if(this.capacity-this._size<10){
     this.capacity+=this.span;
     var oldElements=this._elements;
        this._elements=new Array(this.capacity); 
        for(var i=0;i<this._size;i++){
            this._elements[i]=oldElements[i]; 
        }
    }
};

/**
 * Appends the specified element to the end of this list
 * @param pvalue
 */
AbstractList.prototype.add=function (pvalue){
    this.recapacity();
    this._elements[this._size++]=pvalue; 
};

/**
 * Inserts the specified element at the specified position in this list
 * @param index
 * @param pvalue
 */
AbstractList.prototype.addIndexOf=function (index,pvalue){
    this.recapacity();
    if(index>=this._size){
        this._elements[this._size++]=pvalue;
        return; 
    }
    this._size++;      
    for(var i=this._size-1;i>index;i--){
     this._elements[i]=this._elements[i-1];
    } 
    this._elements[index]=pvalue;
};

/**
 * Appends all of the elements in the specified collection to the end of this list
 * @param c
 */
AbstractList.prototype.addAll=function (c){
    if(!(c instanceof Collection)){
    	return;
    }
    var size=c.size();
    for(var i=0;i<size;i++){
    	this.add(c.get(i));
    }
};

/**
 * Inserts all of the elements in the specified collection into this list at the specified position
 * @param index
 * @param c
 */
AbstractList.prototype.addAllIndexOf=function (index,c){
    if(!(c instanceof Collection)){
    	return;
    }
    var size=c.size();
    for(var i=0;i<size;i++){
    	this.addIndexOf(index++,c.get(i));
    }
};

/**
 * Removes all of the elements from this list
 */
AbstractList.prototype.clear=function (){
    this._elements=new Array(this.capacity); 
};

/**
 * Returns true if this list contains the specified element.
 * @param pvalue
 */
AbstractList.prototype.contains=function (pvalue){
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
AbstractList.prototype.containsAll=function (c){
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
 * Returns the element at the specified position in this list.
 * @param index
 */
AbstractList.prototype.get=function (index){
    return this._elements[index];
};

/**
 * Returns the index in this list of the first occurrence of the 
 * specified element, or -1 if this list does not contain this element.
 * @param pvalue
 */
AbstractList.prototype.indexOf=function (pvalue){
    for(var i=0;i<this._size;i++){
     var elem=this._elements[i];
        if(elem.equals(pvalue)){
            return i; 
        } 
    } 
    return -1;
};

/**
 * Returns true if this list contains no elements.
 */
AbstractList.prototype.isEmpty=function (){
    return this._size==0;
};

/**
 * Returns an iterator over the elements in this list in proper sequence.
 */
AbstractList.prototype.iterator=function (){
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
 * Returns the index in this list of the last occurrence of the specified 
 * element, or -1 if this list does not contain this
 */
AbstractList.prototype.lastIndexOf=function (pvalue){
    for(var i=this._size-1;i>=0;i--){
     var elem=this._elements[i];
        if(elem.equals(pvalue)){
            return i; 
        } 
    } 
    return -1;
};

/**
 * Returns a list iterator of the elements in this list.
 * @param index 
 */
AbstractList.prototype.listIterator=function (index){
    function _ListIterator(list){
		this.list=list;		
	    this.size=list.getSize();
	    if(isNaN(index)||index<0){
	    	index=0;
	    }else if(index>this.size){
	    	index = this.size;
	    }
	    this.nextIndex=index;
	}	
	_ListIterator.prototype=new ListIterator();
	_ListIterator.prototype.constructor=_ListIterator;
	_ListIterator.prototype.add=function(o){
	    this.list.add(this.nextIndex++)=o;
	};
	_ListIterator.prototype.hasNext=function(){
	    return this.nextIndex<this.size;
	};
	_ListIterator.prototype.hasPrevious=function(){
	    return this.nextIndex>0;
	};
	_ListIterator.prototype.next=function(){
	    var nextObj;
	    if(this.nextIndex<this.size){
	        nextObj=this.list.get(this.nextIndex);
	        this.nextIndex++;
	        return nextObj;
	    }
	    return null;
	};
	_ListIterator.prototype.nextIndex=function(){
	    return this.nextIndex;
	};
	_ListIterator.prototype.previous=function(){
	    var preObj;
	    if(this.nextIndex>0){
	        preObj=this.list.get(--this.nextIndex);
	        return preObj;
	    }
	    return null;
	};
	_ListIterator.prototype.previousIndex=function(){
	    return this.nextIndex;
	};
	_ListIterator.prototype.remove=function(){
	    return;
	};
	_ListIterator.prototype.set=function(o){
	    return;
	};
	return new _ListIterator(this);
};

/**
 * Removes the element at the specified position in this list
 * @param index
 */
AbstractList.prototype.removeIndexOf=function (index){
    if(index>-1&&index<this._size){
     var oldElems=this._elements;
     this._elements=new Array(this.capacity);
     this._size--;
     for(var i=0;i<this._size;i++){
         if(i<index){
             this._elements[i]=oldElems[i]; 
         }else{
             this._elements[i]=oldElems[i+1];
         } 
        
     }
    }
};

/**
 * Removes the first occurrence in this list of the specified element
 * @param pvalue
 */
AbstractList.prototype.remove=function (pvalue){
    this.removeIndexOf(this.indexOf(pvalue));
};

/**
 * Removes all this list's elements that are also contained in the specified list
 * @param c
 */
AbstractList.prototype.removeAll=function (c){
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
 * Retains only the elements in this list that are contained in the specified list
 * @param c
 */
AbstractList.prototype.retainAll=function (c){
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
 * Replaces the element at the specified position in this list with the 
 * specified element
 * @param index
 * @param pvalue
 */
AbstractList.prototype.set=function (index,pvalue){
    this._elements[index]=pvalue;
};

/**
 * Returns a view of the portion of this list between the specified 
 * fromIndex, inclusive, and toIndex, exclusive.
 * @param index1
 * @param index2
 */
AbstractList.prototype.subList=function (index1,index2){
    var l=new AbstractList();
    for(var i=index1;i<index2;i++){
        l.add(this._elements[i]); 
    }
    return l;
};

/**
 * Returns the number of elements in this list. 
 */
AbstractList.prototype.getSize=function (){
    return this._size;
};

/**
 * Returns the number of elements in this list. 
 */
AbstractList.prototype.size=function (){
    return this._size;
};

/**
 * Returns an array containing all of the elements 
 * in this list in proper sequence. 
 */
AbstractList.prototype.toArray=function (){
    var arr=new Array(this._size);
    for(var i=0;i<this._size;i++){
        arr[i]=this._elements[i]; 
    } 
    return arr;
};

/**
 * Returns a string representation of this AbstractList object
 */
AbstractList.prototype.toString=function (){
    return this.toArray().toString(); 
};
