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

/**  The Point class references to java.awt.Point of J2SE1.4 */
 
/**
 * constructor
 * @param x the x coordinate
 * @param y the y coordinate
 */
function Point(x,y){
	this.jsjava_class="jsjava.awt.Point";
	this.x=x;
	this.y=y;
}

/**
 * Returns the X coordinate of the point in double precision.
 */
Point.prototype.getX=function(){
    return this.x;
};

/**
 * Returns the Y coordinate of the point in double precision.
 */
Point.prototype.getY=function(){
    return this.y;
};

/**
 * Returns the location of this point.
 */
Point.prototype.getLocation=function(){
    return new Point(this.x,this,y);	
};

/**
 * Changes the point to have the specified location.
 * @param x the x coordinate of the new location
 * @param y the y coordinate of the new location
 */
Point.prototype.setLocation=function(x,y){
    this.x=x;	
    this.y=y;
};

/**
 * Returns the X coordinate of the bounding Rectangle in double precision.
 */
Point.prototype.translate=function(dx,dy){
    this.x+=dx;
    this.y+=dy;
};

/**
 * return a string description
 */
Point.prototype.toString=function(){
    return "{x="+this.x+",y="+this.y+"}"; 
};