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

/**  The Color class references to java.awt.Color of J2SE1.4 */
 
/**
 * constructor
 * @param r the red component
 * @param g the green component
 * @param b the blue component
 * @param a the alpha component
 */
function Color(r, g, b, a){
	this.jsjava_class="jsjava.awt.Color";
	r=init(r);
	g=init(g);
	b=init(b);
	a=init(a);
    this.value = ((a & 0xFF) << 24) |
                ((r & 0xFF) << 16) |
                ((g & 0xFF) << 8)  |
                ((b & 0xFF) << 0);	
    function init(c){
    	if(c==undefined||isNaN(c))
    		c=255;
    	c=parseInt(c);
    	if(c>255){
    		c=255;
    	}
    	return c;
    }
}

Color.FACTOR=0.7;
Color.white= new Color(255, 255, 255);
Color.WHITE = Color.white;
Color.lightGray = new Color(192, 192, 192);
Color.LIGHT_GRAY = Color.lightGray;
Color.gray = new Color(128, 128, 128);
Color.GRAY = Color.gray;
Color.darkGray = new Color(64, 64, 64);
Color.DARK_GRAY = Color.darkGray;
Color.black = new Color(0, 0, 0);
Color.BLACK = Color.black;
Color.red = new Color(255, 0, 0);
Color.RED = Color.red;
Color.pink = new Color(255, 175, 175);
Color.PINK = Color.pink;
Color.orange = new Color(255, 200, 0);
Color.ORANGE = Color.orange;
Color.yellow = new Color(255, 255, 0);
Color.YELLOW = Color.yellow;
Color.green = new Color(0, 255, 0);
Color.GREEN = Color.green;
Color.magenta = new Color(255, 0, 255);
Color.MAGENTA = Color.magenta;
Color.cyan = new Color(0, 255, 255);
Color.CYAN = Color.cyan;
Color.blue = new Color(0, 0, 255);
Color.BLUE = Color.blue;

/**
 * Returns the RGB value representing the color in the default sRGB ColorModel.
 * (Bits 24-31 are alpha, 16-23 are red, 8-15 are green, 0-7 are blue).  
 */
Color.prototype.getRGB=function(){
    return this.value;	
};

/**
 * Returns the red component in the range 0-255 in the default sRGB space
 */
Color.prototype.getRed=function(){
    return (this.getRGB() >> 16) & 0xFF;	
};

/**
 * Returns the green component in the range 0-255 in the default sRGB space
 */
Color.prototype.getGreen=function(){
    return (this.getRGB() >> 8) & 0xFF;	
};

/**
 * Returns the blue component in the range 0-255 in the default sRGB space
 */
Color.prototype.getBlue=function(){
    return (this.getRGB() >> 0) & 0xFF;	
};

/**
 * Returns the alpha component in the range 0-255
 */
Color.prototype.getAlpha=function(){
    return (this.getRGB() >> 24) & 0xFF;	
};

/**
 * Creates a new Color that is a brighter version of this Color
 */
Color.prototype.brighter=function(){
    var r = this.getRed();
    var g = this.getGreen();
    var b = this.getBlue();

    var i = parseInt(1.0/(1.0-Color.FACTOR));
    if ( r == 0 && g == 0 && b == 0) {
       return new Color(i, i, i);
    }
    if ( r > 0 && r < i ) r = i;
    if ( g > 0 && g < i ) g = i;
    if ( b > 0 && b < i ) b = i;

    return new Color(Math.min(parseInt(r/Color.FACTOR), 255),
                     Math.min(parseInt(g/Color.FACTOR), 255),
                     Math.min(parseInt(b/Color.FACTOR), 255));	
};

/**
 * Creates a new Color that is a darker version of this Color
 */
Color.prototype.darker=function(){
    return new Color(Math.max(parseInt(this.getRed()*Color.FACTOR), 0), 
			 Math.max(parseInt(this.getGreen()*Color.FACTOR), 0),
			 Math.max(parseInt(this.getBlue()*Color.FACTOR), 0));	
};

/**
 * Get the hex color value
 */
Color.prototype.toHexValue=function(){
	var redValue=this.getRed();
	var greenValue=this.getGreen();
	var blueValue=this.getBlue();
	var redHexValue=Integer.toHexString(redValue);
	if(redHexValue.length==1){
		redHexValue="0"+redHexValue;
	}
	var greenHexValue=Integer.toHexString(greenValue);
	if(greenHexValue.length==1){
		greenHexValue="0"+greenHexValue;
	}
	var blueHexValue=Integer.toHexString(blueValue);
	if(blueHexValue.length==1){
		blueHexValue="0"+blueHexValue;
	}
	var hexValue=redHexValue+greenHexValue+blueHexValue;
	return hexValue;	
};

/**
 * return a string description
 */
Color.prototype.toString=function(){
    return "[red="+this.getRed()+",green="+this.getGreen()+",blue="+this.getBlue()+",alpha="+this.getAlpha()+"]"; 
};