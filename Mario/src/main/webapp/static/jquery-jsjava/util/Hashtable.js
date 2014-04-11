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

 /**  The Hashtable class references to java.util.Hashtable of J2SE1.4 */
 
/**
 * constructor
 */
function Hashtable(){
	this.jsjava_class="jsjava.util.Hashtable";
	this.capacity=50;
    this._size=0;
    this.span=10;
    this._elements=new Array(this.capacity);
}

Hashtable.prototype=new AbstractMap();
Hashtable.prototype.constructor=Hashtable;

/**
 * Associates the specified value with the specified key in this map 
 * @param pname
 * @param pvalue 
 */
Hashtable.prototype.put=function (pname,pvalue){
	if(pname==undefined||pname==null||pvalue==undefined||pvalue==null){
		throw new NullPointerException(NullPointerException.ERROR,"key and value can not be null");
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
};

Hashtable.prototype.elements=function(){
	function _Enumeration(hash){
    	if(hash==undefined){
			hash=new Hashtable();
		}
		this.list=hash.values();
	    this.nextIndex=0;
	    this.size=hash.getSize();
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
	return new _Enumeration(this);
};