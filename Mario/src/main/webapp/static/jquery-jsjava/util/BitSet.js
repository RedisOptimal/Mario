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
 
 /**  The BitSet class references to java.util.BitSet of J2SE1.4 */
 
/**
 * constructor
 */
 function BitSet(){
 	 this.jsjava_class="jsjava.util.BitSet";
     this.elements=new Array();
 }
 
/**
 * Sets the bit at the specified index to true.
 * @param bitIndex
 */
 BitSet.prototype.set=function(bitIndex){
     this.elements[bitIndex]=true;
 };
 
/**
 * Sets the bit at the specified index to the specified value.
 * @param bitIndex
 * @param value
 */
 BitSet.prototype.setByValue=function(bitIndex,value){
     if(value!=true&&value!=false){
         value=false;	
     }
     this.elements[bitIndex]=value;
 };
 
/**
 * Sets the bits from the specified fromIndex(inclusive) to the 
 * specified toIndex(exclusive) to true.
 * @param fromIndex
 * @param toIndex
 */
 BitSet.prototype.setBetween=function(fromIndex,toIndex){
     for(var i=fromIndex;i<toIndex;i++){
         this.elements[i]=true;
     }
 };
 
/**
 * Sets the bits from the specified fromIndex(inclusive) to the 
 * specified toIndex(exclusive) to the specified value.
 * @param fromIndex
 * @param toIndex
 * @param value
 */
 BitSet.prototype.setBetweenByValue=function(fromIndex,toIndex,value){
     if(value!=true&&value!=false){
         value=false;	
     }
     for(var i=fromIndex;i<toIndex;i++){
         this.elements[i]=value;
     }
 };
 
/**
 * Returns the value of the bit with the specified index.
 * @param bitIndex
 */
 BitSet.prototype.get=function(bitIndex){
     var value=this.elements[bitIndex];
     if(value){
         return value;
     }
     return false;
 };

/**
 * Returns a new BitSet composed of bits from this BitSet from 
 * fromIndex(inclusive) to toIndex(exclusive).
 * @param fromIndex
 * @param toIndex
 */ 
 BitSet.prototype.getBetween=function(fromIndex,toIndex){
     var bs=new BitSet();
     for(var i=fromIndex;i<toIndex;i++){
         bs.setByValue(i,this.get(i));	
     }
     return bs;
 };

/**
 * Returns a string representation of this bit set. 
 */
 BitSet.prototype.toString=function(){
 	 var elems=new Array();
 	 for(var i=0;i<this.elements.length;i++){
 	     var value=this.elements[i];
 	     if(value){
 	         elems[i]=true;
 	     }else{
 	         elems[i]=false;
 	     }
 	 }
     return elems.toString();	
 };

/**
 * Returns the number of bits of space actually in use by 
 * this BitSet to represent bit values. 
 */ 
 BitSet.prototype.size=function(){
     return this.elements.length;	
 };

/**
 * Returns the "logical size" of this BitSet: the index of the 
 * highest set bit in the BitSet plus one.
 */ 
 BitSet.prototype.length=function(){
     return this.size();	
 };

/**
 * Sets all of the bits in this BitSet to false.
 */ 
 BitSet.prototype.clear=function(){
     for(var i=0;i<this.size();i++){
         this.elements[i]=false;	
     }	
 };

/**
 * Sets the bits from the specified fromIndex(inclusive) to 
 * the specified toIndex(exclusive) to false.
 * @param fromIndex
 * @param toIndex
 */ 
 BitSet.prototype.clearBetween=function(fromIndex,toIndex){
     for(var i=fromIndex;i<toIndex;i++){
         this.elements[i]=false;	
     }	
 };

/**
 * Returns true if this BitSet contains no bits that are set to true.
 */ 
 BitSet.prototype.isEmpty=function(){
     for(var i=0;i<this.size();i++){
         if(this.get(i)){
             return false;
         }
     }	
     return true;
 };

/**
 * Sets the bit at the specified index to to the complement of its current value.
 * @param bitIndex
 */ 
 BitSet.prototype.flip=function(bitIndex){
     var value=this.elements[bitIndex];
     if(value!=true&&value!=false){
         value=false;	
     }
     var rValue=false;
     if(!value){
         rValue=true;	
     }
     this.setByValue(bitIndex,rValue);
 };

/**
 * Sets each bit from the specified fromIndex(inclusive) to the 
 * specified toIndex(exclusive) to the complement of its current value.
 * @param fromIndex
 * @param toIndex
 */
 BitSet.prototype.flipBetween=function(fromIndex,toIndex){
     for(var i=fromIndex;i<toIndex;i++){
         this.flip(i);	
     }
 };

/**
 * Performs a logical AND of this target bit set with the 
 * argument bit set.
 * @param bs a BitSet object
 */ 
 BitSet.prototype.and=function(bs){
     var size1=this.size();
     var size2=bs.size();
     var size=size1>size2?size1:size2;
     for(var i=0;i<size;i++){
         var value1=this.get(i);
         var value2=bs.get(i);
         var value=value1&&value2;
         this.setByValue(i,value);
     }	
 };

/**
 * Performs a logical OR of this bit set with the bit set argument.
 * @param bs a BitSet object
 */ 
 BitSet.prototype.or=function(bs){
     var size1=this.size();
     var size2=bs.size();
     var size=size1>size2?size1:size2;
     for(var i=0;i<size;i++){
         var value1=this.get(i);
         var value2=bs.get(i);
         var value=value1||value2;
         this.setByValue(i,value);
     }	
 };

/**
 * Performs a logical XOR of this bit set with the bit set argument.
 * @param bs a BitSet object
 */  
 BitSet.prototype.xor=function(bs){
     var size1=this.size();
     var size2=bs.size();
     var size=size1>size2?size1:size2;
     for(var i=0;i<size;i++){
         var value1=this.get(i);
         var value2=bs.get(i);
         var value=value1^value2;
         value=value==1?true:false;
         this.setByValue(i,value);
     }	
 };

/**
 * Clears all of the bits in this BitSet whose corresponding bit is 
 * set in the specified BitSet.
 * @param bs a BitSet object
 */ 
 BitSet.prototype.andNot=function(bs){
     var size1=this.size();
     var size2=bs.size();
     var size=size1>size2?size1:size2;
     for(var i=0;i<size;i++){
         var value1=this.get(i);
         var value2=bs.get(i);
         var value=value2==true?false:value1;
         this.setByValue(i,value);
     }	
 };

/**
 * Returns the number of bits set to true in this BitSet.
 */ 
 BitSet.prototype.cardinality=function(){
     var value=0;
     for(var i=0;i<this.size();i++){
         if(this.get(i)){
             value++;	
         }	
     }	
     return value;
 };