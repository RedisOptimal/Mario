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

 /**  The Arrays class references to java.util.Arrays of J2SE1.4 */
 
/**
 * constructor
 */
function Arrays(){
	this.jsjava_class="jsjava.util.Arrays";
}

Arrays.asList=function(arr){
	if(arr==undefined||!(arr instanceof Array)){
		return new NullPointerException(NullPointerException.ERROR,"");
	}
	function UnModifiedArrayList(){
	
	}
	UnModifiedArrayList.prototype=new ArrayList();
	UnModifiedArrayList.prototype.constructor=UnModifiedArrayList;
	UnModifiedArrayList.CONSTANT_UNSUPPORTED_OPERATION="This is an unmodified list!";
	UnModifiedArrayList.prototype.add=function(o){
		throw new UnsupportedOperationException(UnsupportedOperationException.ERROR,UnModifiedArrayList.CONSTANT_UNSUPPORTED_OPERATION);
	};
	UnModifiedArrayList.prototype.addIndexOf=function(index,o){
		throw new UnsupportedOperationException(UnsupportedOperationException.ERROR,UnModifiedArrayList.CONSTANT_UNSUPPORTED_OPERATION);
	};
	UnModifiedArrayList.prototype.addAll=function(c){
		throw new UnsupportedOperationException(UnsupportedOperationException.ERROR,UnModifiedArrayList.CONSTANT_UNSUPPORTED_OPERATION);
	};
	UnModifiedArrayList.prototype.addAllIndexOf=function(index,c){
		throw new UnsupportedOperationException(UnsupportedOperationException.ERROR,UnModifiedArrayList.CONSTANT_UNSUPPORTED_OPERATION);
	};
	UnModifiedArrayList.prototype.clear=function(){
		throw new UnsupportedOperationException(UnsupportedOperationException.ERROR,UnModifiedArrayList.CONSTANT_UNSUPPORTED_OPERATION);
	};
	UnModifiedArrayList.prototype.remove=function(o){
		throw new UnsupportedOperationException(UnsupportedOperationException.ERROR,UnModifiedArrayList.CONSTANT_UNSUPPORTED_OPERATION);
	};
	UnModifiedArrayList.prototype.removeIndexOf=function(index,o){
		throw new UnsupportedOperationException(UnsupportedOperationException.ERROR,UnModifiedArrayList.CONSTANT_UNSUPPORTED_OPERATION);
	};
	UnModifiedArrayList.prototype.removeAll=function(c){
		throw new UnsupportedOperationException(UnsupportedOperationException.ERROR,UnModifiedArrayList.CONSTANT_UNSUPPORTED_OPERATION);
	};
	UnModifiedArrayList.prototype.retainAll=function(c){
		throw new UnsupportedOperationException(UnsupportedOperationException.ERROR,UnModifiedArrayList.CONSTANT_UNSUPPORTED_OPERATION);
	};
	UnModifiedArrayList.prototype.set=function(index,o){
		throw new UnsupportedOperationException(UnsupportedOperationException.ERROR,UnModifiedArrayList.CONSTANT_UNSUPPORTED_OPERATION);
	};
	var uList=new UnModifiedArrayList();
	for(var i=0;i<arr.length;i++){
		uList._elements[uList._size++]=arr[i];
	}
	return uList;
};

Arrays.binarySearch=function(arr,key){
	if(arr==undefined||!(arr instanceof Array)){
		return new NullPointerException(NullPointerException.ERROR,"");
	}
	var low = 0;
	var high = arr.length-1;
	while (low <= high) {
	    var mid = (low + high) >> 1;
	    var midVal = a[mid];
	    var cmp = midVal.compareTo(key);
	    if (cmp < 0){
			low = mid + 1;
		}else if (cmp > 0){
			high = mid - 1;
		}else{
			return mid; 
		}
	}
	return -(low + 1);  
};

Arrays.equals=function(arr1,arr2){
	if(arr1==undefined&&arr2==undefined){
		return true;
	}
	if(!(arr1 instanceof Array)||!(arr2 instanceof Array)){
		return false;
	}
	var l1=arr1.length;
	var l2=arr2.length;
	if(l1!=l2){
		return false;
	}
	for(var i=0;i<l1;i++){
		if(arr1[i]!=arr2[i]){
			return false;
		}
	}
	return true;
};

Arrays.fill=function(arr,o){
	if(arr==undefined||!(arr instanceof Array)){
		return;
	}
	for(var i=0;i<arr.length;i++){
		arr[i]=o;
	}
};

Arrays.fillBetween=function(arr,fromIndex,toIndex,o){
	if(arr==undefined||!(arr instanceof Array)){
		return;
	}
	var aLength=arr.length;
	if(fromIndex<0){
		fromIndex=0;
	}
	if(toIndex>aLength){
		toIndex=aLength;
	}
	if(fromIndex>toIndex){
		return;
	}	
	for(var i=fromIndex;i<toIndex;i++){
		arr[i]=o;
	}
};

Arrays.sort=function(arr,c){
	function _sort(o1,o2){
		return c.compare(o1,o2);
	}
	if(arr==undefined||!(arr instanceof Array)){
		return;
	}
	if(c==undefined||!(c instanceof Comparator)){
		arr.sort();
	}else{
		arr.sort(c);
	}
};

Arrays.sortBetween=function(arr,fromIndex,toIndex,c){
	function _sort(o1,o2){
		return c.compare(o1,o2);
	}
	if(arr==undefined||!(arr instanceof Array)){
		return;
	}
	var aLength=arr.length;
	if(fromIndex<0){
		fromIndex=0;
	}
	if(toIndex>aLength){
		toIndex=aLength;
	}
	if(fromIndex>toIndex){
		return;
	}	
	var midArr=new Array();
	for(var i=fromIndex,j=0;i<toIndex;i++,j++){
		midArr[j]=arr[i];
	}
	if(c==undefined||!(c instanceof Comparator)){
		midArr.sort();
	}else{
		midArr.sort(c);
	}
	for(var i=fromIndex,j=0;i<toIndex;i++,j++){
		arr[i]=midArr[j];
	}
};