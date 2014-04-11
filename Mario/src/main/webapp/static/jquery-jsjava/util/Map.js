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

 /**  The Map class references to java.util.Map of J2SE1.4 */
 
/**
 * constructor
 */
function Map(){
	this.jsjava_class="jsjava.util.Map";
}

Map.CONSTANT_ILLEGAL_INVOCATION="This is an interface method and you should use the concrete method";

/**
 * Increases the capacity of and internally reorganizes this 
 * Map, in order to accommodate and access its entries 
 * more efficiently.
 */
Map.prototype.recapacity=function(){
    throw new IllegalStateException(IllegalStateException.ERROR,Map.CONSTANT_ILLEGAL_INVOCATION);
};

/**
 * Removes all mappings from this map 
 */
Map.prototype.clear=function(){
    throw new IllegalStateException(IllegalStateException.ERROR,Map.CONSTANT_ILLEGAL_INVOCATION); 
};

/**
 * Associates the specified value with the specified key in this map 
 * @param pname
 * @param pvalue 
 */
Map.prototype.put=function (pname,pvalue){
    throw new IllegalStateException(IllegalStateException.ERROR,Map.CONSTANT_ILLEGAL_INVOCATION);
   
};

/**
 * Copies all of the mappings from the specified map to this map 
 * @param map 
 */
Map.prototype.putAll=function (map){
    throw new IllegalStateException(IllegalStateException.ERROR,Map.CONSTANT_ILLEGAL_INVOCATION);
   
};

/**
 * Returns the value to which this map maps the specified key. 
 * @param pname
 */
Map.prototype.get=function (pname){
    throw new IllegalStateException(IllegalStateException.ERROR,Map.CONSTANT_ILLEGAL_INVOCATION);
};

/**
 * Returns true if this map contains a mapping for the specified key.
 * @param pname
 */
Map.prototype.containsKey=function(pname){
    throw new IllegalStateException(IllegalStateException.ERROR,Map.CONSTANT_ILLEGAL_INVOCATION);
};

/**
 * Returns true if this map maps one or more keys to the specified value.
 * @param pname
 */
Map.prototype.containsValue=function (pvalue){
    throw new IllegalStateException(IllegalStateException.ERROR,Map.CONSTANT_ILLEGAL_INVOCATION);
};

/**
 * Returns a array view of the values contained in this map.
 */
Map.prototype.values=function (){
    throw new IllegalStateException(IllegalStateException.ERROR,Map.CONSTANT_ILLEGAL_INVOCATION);
};

/**
 * Returns a set view of the mappings contained in this map.
 */
Map.prototype.entrySet=function (){
    throw new IllegalStateException(IllegalStateException.ERROR,Map.CONSTANT_ILLEGAL_INVOCATION);
};

/**
 * Returns true if this map contains no key-value mappings.
 */
Map.prototype.isEmpty=function (){
    throw new IllegalStateException(IllegalStateException.ERROR,Map.CONSTANT_ILLEGAL_INVOCATION);
};

/**
 * Returns an array of the keys in this Map.
 */
Map.prototype.keySet=function (){
    throw new IllegalStateException(IllegalStateException.ERROR,Map.CONSTANT_ILLEGAL_INVOCATION);
};

/**
 * Returns the number of keys in this Map.
 */
Map.prototype.getSize=function (){
    throw new IllegalStateException(IllegalStateException.ERROR,Map.CONSTANT_ILLEGAL_INVOCATION); 
};

/**
 * Returns the number of keys in this Map.
 */
Map.prototype.size=function (){
    throw new IllegalStateException(IllegalStateException.ERROR,Map.CONSTANT_ILLEGAL_INVOCATION); 
};

/**
 * Removes the key (and its corresponding value) from this Map.
 * @param key
 */
Map.prototype.remove=function (key){
    throw new IllegalStateException(IllegalStateException.ERROR,Map.CONSTANT_ILLEGAL_INVOCATION);
};

Map.Entry=function(){
	this.jsjava_class="jsjava.util.Map.Entry";
	this.equals=function(o){
		throw new IllegalStateException(IllegalStateException.ERROR,Map.CONSTANT_ILLEGAL_INVOCATION); 
	};
	this.getKey=function(){
		throw new IllegalStateException(IllegalStateException.ERROR,Map.CONSTANT_ILLEGAL_INVOCATION); 
	};
	this.getValue=function(){
		throw new IllegalStateException(IllegalStateException.ERROR,Map.CONSTANT_ILLEGAL_INVOCATION); 
	};
	this.setValue=function(value){
		throw new IllegalStateException(IllegalStateException.ERROR,Map.CONSTANT_ILLEGAL_INVOCATION); 
	};
};