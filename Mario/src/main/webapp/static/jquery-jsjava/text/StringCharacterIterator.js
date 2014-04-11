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

/**  The StringCharacterIterator class references to java.text.StringCharacterIterator of J2SE1.4 */
 
/**
 * constructor
 * @param str
 */
function StringCharacterIterator(str){
	this.jsjava_class="jsjava.text.StringCharacterIterator";
    this.str=str;
    if(str==null){
        this.str="";	
    }
    this.list=new ArrayList();
    var length=str.length;
    for(var i=0;i<length;i++){
        this.list.add(str.charAt(i));	
    }
    this.nextIndex=0;
    this.prevIndex=this.list.getSize()-1;
    this.currentIndex=0;
}

/**
 * judge whether the iterator has more elements
 */
StringCharacterIterator.prototype.hasMore=function(){
    if(this.nextIndex==this.list.getSize()+1||this.prevIndex==-2){
    	return false;
    }
    return true;	
};

/**
 * return the current char
 */
StringCharacterIterator.prototype.current=function(){
    return this.list.get(this.currentIndex);
};

/**
 * return the first char
 */
StringCharacterIterator.prototype.first=function(){
    this.nextIndex=1;
    this.currentIndex=this.nextIndex-1;
    return this.list.get(0);	
};

/**
 * return the last char
 */
StringCharacterIterator.prototype.last=function(){
    this.prevIndex=this.list.getSize()-2;
    this.currentIndex=this.prevIndex+1;
    return this.list.get(this.list.getSize()-1);	
};

/**
 * return the next char
 */
StringCharacterIterator.prototype.next=function(){
    this.currentIndex=this.nextIndex;
    return this.list.get(this.nextIndex++);	
};

/**
 * return the previous char
 */
StringCharacterIterator.prototype.previous=function(){
    this.currentIndex=this.prevIndex;
    return this.list.get(this.prevIndex--);	
};

/**
 * return the begin index from where the iterator start
 */
StringCharacterIterator.prototype.getBeginIndex=function(){
    return 0;	
};

/**
 * return the end index where the iterator stop
 */
StringCharacterIterator.prototype.getEndIndex=function(){
    return this.list.getSize()-1;
};

/**
 * return the current index
 */
StringCharacterIterator.prototype.getIndex=function(){
    return this.currentIndex;
};

/**
 * set the current index
 * @param index
 */
StringCharacterIterator.prototype.setIndex=function(index){
    this.currentIndex=index;
    this.nextIndex=index+1;
    this.prevIndex=index-1;	
};

/**
 * set the string
 * @param text
 */
StringCharacterIterator.prototype.setText=function(text){
    this.str=text;
    if(text==null){
        this.str="";	
    }
    this.list=new ArrayList();
    var length=text.length;
    for(var i=0;i<length;i++){
        this.list.add(text.charAt(i));	
    }
};
