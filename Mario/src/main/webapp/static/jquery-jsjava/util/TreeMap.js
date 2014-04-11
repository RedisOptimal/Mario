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

 /**  The TreeMap class references to java.util.TreeMap of J2SE1.4 */
 
/**
 * constructor
 */
function TreeMap(comparator){
	this.jsjava_class="jsjava.util.TreeMap";
    this.capacity=50;
    this._size=0;
    this.span=10;
    this._elements=new Array(this.capacity);
    this.comparator=comparator;
}

TreeMap.prototype=new SortedMap();
TreeMap.prototype.constructor=TreeMap;

/**
 * Increases the capacity of and internally reorganizes this 
 * TreeMap, in order to accommodate and access its entries 
 * more efficiently.
 */
TreeMap.prototype.recapacity=function(){
    if(this.capacity-this._size<10){
     this.capacity+=this.span;
     var oldElements=this._elements;
        this._elements=new Array(this.capacity); 
        for(var i=0;i<this._size;i++){
            this._elements[i]=oldElements[i]; 
        }
    }
};

TreeMap.prototype.sort=function(){
	function _sort(o1,o2){
		return c.compare(o1,o2);
	}
	var c=this.comparator;
	if(c==undefined||!(c instanceof Comparator)){
		this._elements.sort();
		return;
	}
	this._elements.sort(_sort);
};

/**
 * Removes all mappings from this map 
 */
TreeMap.prototype.clear=function(){
    this.capacity=50;
    this._size=0;
    this._elements=new Array(this.capacity); 
};

/**
 * Returns the comparator associated with this sorted map, or null if it uses its keys' natural ordering.
 */
TreeMap.prototype.comparator=function(){
    return this.comparator;
};

/**
 * Returns the first (lowest) key currently in this sorted map 
 */
TreeMap.prototype.firstKey=function(){
    return this._elements[0][0];
};

/**
 * Returns a view of the portion of this sorted map whose keys are strictly less than toKey
 * @param toKey
 */
TreeMap.prototype.headMap=function (toKey){
    var keyIndex=this.indexOf(toKey);
    if(keyIndex==-1){
    	return;
    }
    var keys=this.keySet();
    var map=new TreeMap();
    for(var i=0;i<keyIndex;i++){
    	var key=keys.get(i);
    	var value=this.get(key);
    	map.put(key,value);
    }
    return map;
};

/**
 * Returns the key index
 * @param key
 */
TreeMap.prototype.indexOf=function (key){
    var keys=this.keySet();
    var keySize=keys.size();
    for(var i=0;i<keySize;i++){
    	var k=keys.get(i);
    	if(k.equals(key)){
    		return i;
    	}
    }
    return -1;
};

/**
 * Returns the last (highest) key currently in this sorted map
 */
TreeMap.prototype.lastKey=function (){
    return this._elements[this._size-1][0];
};

/**
 * Associates the specified value with the specified key in this map 
 * @param pname
 * @param pvalue 
 */
TreeMap.prototype.put=function (pname,pvalue){
	if(pname==undefined||pname==null){
		throw new NullPointerException(NullPointerException.ERROR,"");
	}
    this.recapacity();
    if(this.containsKey(pname)){
        for(var i=0;i<this._size;i++){
            var elem=this._elements[i];
            if(elem[0].equals(pname)){
                elem[1]=pvalue;
                return;
            } 
        } 
    }
    this._elements[this._size++]=[pname,pvalue];
    this.sort();
   
};

/**
 * Copies all of the mappings from the specified map to this map 
 * @param map 
 */
TreeMap.prototype.putAll=function (map){
    if(map==undefined||!(map instanceof Map)){
    	return;
    }
    var keyset=map.keySet();
    var keysize=keyset.size();
    for(var i=0;i<keysize;i++){
    	var key=keyset.get(i);
    	var value=map.get(key);
    	this.put(key,value);
    }
};

/**
 * Returns the value to which this map maps the specified key. 
 * @param pname
 */
TreeMap.prototype.get=function (pname){
    for(var i=0;i<this._size;i++){
        var elem=this._elements[i];
        if(elem[0].equals(pname)){
            return elem[1]; 
        } 
    }
};

/**
 * Returns true if this map contains a mapping for the specified key.
 * @param pname
 */
TreeMap.prototype.containsKey=function(pname){
    if(this.get(pname)==undefined){
        return false; 
    }
    return true;
};

/**
 * Returns true if this map maps one or more keys to the specified value.
 * @param pname
 */
TreeMap.prototype.containsValue=function (pvalue){
    for(var i=0;i<this._size;i++){
        var elem=this._elements[i];
        if(elem[1].equals(pvalue)){
            return true;
        } 
    }
    return false;
};

/**
 * Returns a array view of the values contained in this map.
 */
TreeMap.prototype.values=function (){
    var values=new ArrayList();
    for(var i=0;i<this._size;i++){
        var elem=this._elements[i];
        values.add(elem);
    } 
    return values;
};

/**
 * Returns a set view of the mappings contained in this map.
 */
TreeMap.prototype.entrySet=function (){
    return this._elements; 
};

/**
 * Returns true if this map contains no key-value mappings.
 */
TreeMap.prototype.isEmpty=function (){
    return this._size==0; 
};

/**
 * Returns an array of the keys in this TreeMap.
 */
TreeMap.prototype.keys=function (){
    function KeysEnueration(list){
    	if(list==undefined){
			list=new ArrayList();
		}
		this.list=list;
	    this.nextIndex=0;
	    this.size=list.getSize();
    }
    KeysEnueration.prototype=new Enumeration();
    KeysEnueration.prototype.constructor=KeysEnueration;
    KeysEnueration.prototype.hasMoreElements=function(){
	    return this.nextIndex<this.size;
	};	
	KeysEnueration.prototype.nextElement=function(){
	    var nextObj;
	    if(this.nextIndex<this.size){
	        nextObj=this.list.get(this.nextIndex);
	        this.nextIndex++;
	        return nextObj;
	    }
	    return null;
	};
    var keys=this.keySet();
    return new KeysEnueration(keys);
};

/**
 * Returns an array of the keys in this TreeMap.
 */
TreeMap.prototype.keySet=function (){
    var set=new HashSet();
    for(var i=0;i<this._size;i++){
        var elem=this._elements[i];
        set.add(elem[0]); 
    } 
    return set;
};

/**
 * Returns the number of keys in this TreeMap.
 */
TreeMap.prototype.getSize=function (){
    return this._size; 
};

/**
 * Returns the number of keys in this TreeMap.
 */
TreeMap.prototype.size=function (){
    return this._size; 
};

/**
 * Removes the key (and its corresponding value) from this TreeMap.
 * @param key
 */
TreeMap.prototype.remove=function (key){
    if(this.containsKey(key)){
        var oldElems=this._elements;
        var oldSize=this._size;
        this._elements=new Array(this.capacity);
        this._size=0;
        for(var i=0;i<oldSize;i++){
            var oldElem=oldElems[i];
            if(!oldElem[0].equals(key)){
                this.put(oldElem[0],oldElem[1]); 
            } 
        } 
    }
};

/**
 * Returns a view of the portion of this sorted map whose keys range from fromKey, inclusive, to toKey, exclusive
 * @param fromKey
 * @param toKey
 */
TreeMap.prototype.subMap=function (fromKey,toKey){
    var fromIndex=this.indexOf(fromKey);
    var toIndex=this.indexOf(toKey);
    if(fromIndex==-1){
    	fromIndex=0;
    }
    if(fromIndex>toIndex){
    	return;
    }
    var keys=this.keySet();
    var map=new TreeMap();
    for(var i=fromIndex;i<toIndex;i++){
    	var key=keys.get(i);
    	var value=this.get(key);
    	map.put(key,value);
    }
    return map;
};

/**
 * Returns a view of the portion of this sorted map whose keys are greater than or equal to fromKey.
 * @param fromKey
 */
TreeMap.prototype.tailMap=function(toKey){
    var keyIndex=this.indexOf(toKey);
    if(keyIndex==-1){
    	return;
    }
    var keys=this.keySet();
    var map=new TreeMap();
    for(var i=keyIndex;i<this._size;i++){
    	var key=keys.get(i);
    	var value=this.get(key);
    	map.put(key,value);
    }
    return map;
};

/**
 * Returns a string representation of this TreeMap 
 * object in the form of a set of entries, enclosed 
 * in braces and separated by the ASCII characters ", " 
 * (comma and space).
 */
TreeMap.prototype.toString=function (){
    return this._elements.toString(); 
};
