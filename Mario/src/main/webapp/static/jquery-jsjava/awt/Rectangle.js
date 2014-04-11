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

/**  The Rectangle class references to java.awt.Rectangle of J2SE1.4 */
 
/**
 * constructor
 * @param width the width of the rectangle
 * @param height the height of the rectangle
 */
function Rectangle(x,y,width,height){
	this.jsjava_class="jsjava.awt.Rectangle";
	this.x=x;
	this.y=y;
	this.width=width;
	this.height=height;
}

/**
 * Adds a point, specified by the integer arguments newx and newy, to this Rectangle.
 * @param newx the x coordinate of the new point
 * @param newy the y coordinate of the new point
 */
Rectangle.prototype.add=function(newx,newy){
    var x1 = Math.min(this.x, newx);
	var x2 = Math.max(this.x + this.width, newx);
	var y1 = Math.min(this.y, newy);
	var y2 = Math.max(this.y + this.height, newy);
	x = x1;
	y = y1;
	this.width = x2 - x1;
	this.height = y2 - y1;	
};

/**
 * Checks whether or not this Rectangle contains the point at the specified location (x, y). 
 * @param X the specified x coordinate
 * @param Y the specified y coordinate
 * @param W the width of the Rectangle
 * @param H the height of the Rectangle
 */
Rectangle.prototype.contains=function(X,Y,W,H){
	if(W==undefined){
		W=0;
	}
	if(H==undefined){
		H=0;
	}
    var w = this.width;
	var h = this.height;
	if ((w | h | W | H) < 0) {
	    return false;
	}
	var x = this.x;
	var y = this.y;
	if (X < x || Y < y) {
	    return false;
	}
	w += x;
	W += X;
	if (W <= X) {
	   if (w >= x || W > w) return false;
	} else {
	    if (w >= x && W > w) return false;
	}
	h += y;
	H += Y;
	if (H <= Y) {
	    if (h >= y || H > h) return false;
	} else {
	    if (h >= y && H > h) return false;
	}
	return true;	
};

/**
 * Returns the height of the bounding Rectangle in double precision.
 */
Rectangle.prototype.getHeight=function(){
    return this.height;	
};

/**
 * Returns the width of the bounding Rectangle in double precision.
 */
Rectangle.prototype.getWidth=function(){
    return this.width;	
};

/**
 * Returns the X coordinate of the bounding Rectangle in double precision.
 */
Rectangle.prototype.getX=function(){
    return this.x;	
};

/**
 * Returns the Y coordinate of the bounding Rectangle in double precision.
 */
Rectangle.prototype.getY=function(){
    return this.y;	
};

/**
 * Resizes the Rectangle both horizontally and vertically.
 * @param h the horizontal expansion
 * @param v the vertical expansion
 */
Rectangle.prototype.grow=function(h,v){
    this.x -= h;
	this.y -= v;
	this.width += h * 2;
	this.height += v * 2;	
};

/**
 * Computes the intersection of this Rectangle with the specified Rectangle.
 * @param r the specified Rectangle
 */
Rectangle.prototype.intersection=function(r){
    var tx1 = this.x;
	var ty1 = this.y;
	var rx1 = r.x;
	var ry1 = r.y;
	var tx2 = tx1; tx2 += this.width;
	var ty2 = ty1; ty2 += this.height;
	var rx2 = rx1; rx2 += r.width;
	var ry2 = ry1; ry2 += r.height;
	if (tx1 < rx1) tx1 = rx1;
	if (ty1 < ry1) ty1 = ry1;
	if (tx2 > rx2) tx2 = rx2;
	if (ty2 > ry2) ty2 = ry2;
	tx2 -= tx1;
	ty2 -= ty1;
	if (tx2 < Integer.MIN_VALUE) tx2 = Integer.MIN_VALUE;
	if (ty2 < Integer.MIN_VALUE) ty2 = Integer.MIN_VALUE;
	return new Rectangle(tx1, ty1, tx2, ty2);	
};

/**
 * Determines whether or not this Rectangle and the specified Rectangle intersect.
 * @param r the specified Rectangle
 */
Rectangle.prototype.intersects=function(r){
	var tw = this.width;
	var th = this.height;
	var rw = r.width;
	var rh = r.height;
	if (rw <= 0 || rh <= 0 || tw <= 0 || th <= 0) {
	    return false;
	}
	var tx = this.x;
	var ty = this.y;
	var rx = r.x;
	var ry = r.y;
	rw += rx;
	rh += ry;
	tw += tx;
	th += ty;
	return ((rw < rx || rw > tx) &&
		(rh < ry || rh > ty) &&
		(tw < tx || tw > rx) &&
		(th < ty || th > ry));    	
};

/**
 * Determines whether or not this Rectangle is empty.
 */
Rectangle.prototype.isEmpty=function(r){
    return (this.width <= 0) || (this.height <= 0);
};

/**
 * Sets the bounding Rectangle of this Rectangle to the specified x, y, width, and height.
 * @param x the new x coordinate for the top-left corner of this Rectangle
 * @param y the new y coordinate for the top-left corner of this Rectangle
 * @param width the new width for this Rectangle
 * @param height the new height for this Rectangle
 */
Rectangle.prototype.setBounds=function(x,y,width,height){
    this.x = x;
	this.y = y;
	this.width = width;
	this.height = height;
};

/**
 * Moves this Rectangle to the specified location.
 * @param x the x coordinate of the new location
 * @param y the y coordinate of the new location
 */
Rectangle.prototype.setLocation=function(x,y){
    this.x = x;
	this.y = y;
};

/**
 * Sets the size of this Rectangle to the specified width and height.
 * @param width the new width for this Rectangle
 * @param height the new height for this Rectangle
 */
Rectangle.prototype.setSize=function(width,height){
    this.width=width;
    this.height=height;	
};

/**
 * Computes the union of this Rectangle with the specified Rectangle.
 * @param r the specified Rectangle
 */
Rectangle.prototype.union=function(r){
    var x1 = Math.min(this.x, r.x);
	var x2 = Math.max(this.x + this.width, r.x + r.width);
	var y1 = Math.min(this.y, r.y);
	var y2 = Math.max(this.y + this.height, r.y + r.height);
	return new Rectangle(x1, y1, x2 - x1, y2 - y1);
};

/**
 * return a string description
 */
Rectangle.prototype.toString=function(){
    return "{x="+this.x+",y="+this.y+",width="+this.width+",height="+this.height+"}"; 
};