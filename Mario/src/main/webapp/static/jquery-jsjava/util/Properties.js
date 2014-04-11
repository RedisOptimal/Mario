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

 /**  The Properties class references to java.util.Properties of J2SE1.4 */
 
/**
 * constructor
 */
function Properties(){
	this.jsjava_class="jsjava.util.Properties";
}

Properties.prototype=new Hashtable();
Properties.prototype.constructor=Properties;

/**
 * Associates the specified value with the specified key in this properties 
 * @param pname
 * @param pvalue 
 */
Properties.prototype.setProperty=function(pname,pvalue){
    if(typeof(pname)=="string"&&typeof(pvalue)=="string"){
        this.put(pname,pvalue);	
    }
};

/**
 * Searches for the property with the specified key in this property list.
 */
Properties.prototype.getProperty=function (pname){
    var pvalue= this.get(pname);
    if(typeof(pvalue)=="string"){
    	return pvalue;
    }
    return null;
};

/**
 * add a hash to the properties
 * @param hash
 */
Properties.prototype.addProperties=function (hash){
    if(hash!=null&&hash.size()>0){
        var keys=hash.keys();
        for(var i=0;i<keys.length;i++){
            this.put(keys[i],hash.get(keys[i])); 
        } 
    } 
};