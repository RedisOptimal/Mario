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

/**  The Font class references to java.awt.Font of J2SE1.4 */
 
/**
 * constructor
 * @param font family
 * @param style css style
 * @param size font size
 * @param weight font weight
 */
function Font(name,style,size){
	this.jsjava_class="jsjava.awt.Font";
    this.name=name;
    this.style=style;
    this.size=size;
}

Font.PLAIN=0;
Font.BOLD=1;
Font.ITALIC=2;

/**
 * Returns the family name of this Font
 */
Font.prototype.getFamily=function(){
    return this.name;	
};

/**
 * Returns the font face name of this Font
 */
Font.prototype.getFontName=function(){
    return this.name;	
};

/**
 * return the font css style
 */
Font.prototype.getStyle=function(){
    return this.style;	
};

/**
 * return the font size
 */
Font.prototype.getSize=function(){
    return this.size;	
};