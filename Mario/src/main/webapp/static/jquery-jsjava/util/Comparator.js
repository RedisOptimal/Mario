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
function Comparator(){
	this.jsjava_class="jsjava.util.Comparator";
}

Comparator.CONSTANT_ILLEGAL_INVOCATION="This is an interface method and you should use the concrete method";

/**
 * Compares its two arguments for order.
 * @param o1
 * @param o2
 */
Comparator.prototype.compare=function(o1,o2){
    throw new IllegalStateException(IllegalStateException.ERROR,Comparator.CONSTANT_ILLEGAL_INVOCATION);
};

/**
 * Indicates whether some other object is "equal to" this Comparator.
 * @param o 
 */
Comparator.prototype.equals=function(o){
    throw new IllegalStateException(IllegalStateException.ERROR,Comparator.CONSTANT_ILLEGAL_INVOCATION); 
};