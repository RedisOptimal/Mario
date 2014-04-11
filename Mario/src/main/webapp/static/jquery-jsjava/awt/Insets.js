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

/**  The Insets class references to java.awt.Insets of J2SE1.4 */
 
/**
 * constructor
 * @param top
 * @param left
 * @param bottom
 * @param right
 */
function Insets(top,left,bottom,right){
	this.jsjava_class="jsjava.awt.Insets";
    this.top=top;
    this.left=left;
    this.bottom=bottom;
    this.right=right;	
}

/**
 * return the Insets top
 */
Insets.prototype.getTop=function(){
    return this.top;	
};

/**
 * return the Insets left
 */
Insets.prototype.getLeft=function(){
    return this.left;	
};

/**
 * return the Insets bottom
 */
Insets.prototype.getBottom=function(){
    return this.bottom;	
};

/**
 * return the Insets right
 */
Insets.prototype.getRight=function(){
    return this.right;	
};