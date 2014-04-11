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

 /**  The AbstractMap class references to java.util.AbstractMap of J2SE1.4 */
 
/**
 * constructor
 */
function AbstractMap(){
	this.jsjava_class="jsjava.util.AbstractMap";
}

AbstractMap.prototype=new Map();
AbstractMap.prototype.constructor=AbstractMap;

/**
 * Increases the capacity of and internally reorganizes this 
 * AbstractMap, in order to accommodate and access its entries 
 * more efficiently.
 */
AbstractMap.prototype.recapacity=function(){
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
 * Removes all mappings from this map 
 */
AbstractMap.prototype.clear=function(){
    this.capacity=50;
    this._size=0;
    this._elements=new Array(this.capacity); 
};

/**
 * Associates the specified value with the specified key in this map 
 * @param pname
 * @param pvalue 
 */
AbstractMap.prototype.put=function (pname,pvalue){
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
};

/**
 * Copies all of the mappings from the specified map to this map 
 * @param map 
 */
AbstractMap.prototype.putAll=function (map){
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
AbstractMap.prototype.get=function (pname){
    for(var i=0;i<this._size;i++){
        var elem=this._elements[i];
        if(elem[0]==undefined||elem[0]==null){
        	if(elem[0]==pname){
        		return elem[1];
        	}
        }else if(elem[0].equals(pname)){
            return elem[1]; 
        } 
    }
};

/**
 * Returns true if this map contains a mapping for the specified key.
 * @param pname
 */
AbstractMap.prototype.containsKey=function(pname){
    if(this.get(pname)==undefined){
        return false; 
    }
    return true;
};

/**
 * Returns true if this map maps one or more keys to the specified value.
 * @param pname
 */
AbstractMap.prototype.containsValue=function (pvalue){
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
AbstractMap.prototype.values=function (){
    var values=new ArrayList();
    for(var i=0;i<this._size;i++){
        var elem=this._elements[i];
        values.add(elem[1]);
    } 
    return values;
};

/**
 * Returns a set view of the mappings contained in this map.
 */
AbstractMap.prototype.entrySet=function (){
	Map._Entry=function(key,value){
		this.key=key;
		this.value=value;
		this.equals=function(o){
			if(!(o instanceof Map.Entry)){
				return true;
			}
			if(this.key.equals(o.key)&&this.value.equals(o.value)){
				return true;
			}
			return false;
		};
		this.getKey=function(){
			return this.key;
		};
		this.getValue=function(){
			return this.value;
		};
		this.setValue=function(value){
			this.value=value;
		};
		this.toString=function(){
			return "("+this.key+","+this.value+")";
		};
	};
	Map._Entry.prototype=new Map.Entry();
	Map._Entry.prototype.constructor=Map._Entry;
	var es=new HashSet();
	for(var i=0;i<this._size;i++){
		var elem=this._elements[i];
		var me=new Map._Entry(elem[0],elem[1]);
		es.add(me);
	}
    return es; 
};

/**
 * Returns true if this map contains no key-value mappings.
 */
AbstractMap.prototype.isEmpty=function (){
    return this._size==0; 
};

/**
 * Returns an array of the keys in this AbstractMap.
 */
AbstractMap.prototype.keys=function (){
    function _Enumeration(map){
    	if(map==undefined){
			map=new HashMap();
		}
		this.list=map.keySet();
	    this.nextIndex=0;
	    this.size=map.getSize();
    }
    _Enumeration.prototype=new Enumeration();
    _Enumeration.prototype.constructor=_Enumeration;
    _Enumeration.prototype.hasMoreElements=function(){
	    return this.nextIndex<this.size;
	};	
	_Enumeration.prototype.nextElement=function(){
	    var nextObj;
	    if(this.nextIndex<this.size){
	        nextObj=this.list.get(this.nextIndex);
	        this.nextIndex++;
	        return nextObj;
	    }
	    return null;
	};
    var keys=this.keySet();
    return new _Enumeration(this);
};

/**
 * Returns an array of the keys in this AbstractMap.
 */
AbstractMap.prototype.keySet=function (){
    var set=new HashSet();
    for(var i=0;i<this._size;i++){
        var elem=this._elements[i];
        set.add(elem[0]); 
    } 
    return set;
};

/**
 * Returns the number of keys in this AbstractMap.
 */
AbstractMap.prototype.getSize=function (){
    return this._size; 
};

/**
 * Returns the number of keys in this AbstractMap.
 */
AbstractMap.prototype.size=function (){
    return this._size; 
};

/**
 * Removes the key (and its corresponding value) from this AbstractMap.
 * @param key
 */
AbstractMap.prototype.remove=function (key){
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
 * Returns a string representation of this AbstractMap 
 * object in the form of a set of entries, enclosed 
 * in braces and separated by the ASCII characters ", " 
 * (comma and space).
 */
AbstractMap.prototype.toString=function (){
	var str="";
    for(var i=0;i<this._size;i++){
    	var elem=this._elements[i];
    	str+=",{"+elem[0]+","+elem[1]+"}";
    } 
    if(str.charAt(0)==","){
    	str=str.substring(1);
    }
    str="{"+str+"}";
    return str;
};
