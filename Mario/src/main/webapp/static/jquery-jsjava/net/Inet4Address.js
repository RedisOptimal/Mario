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
 *  author:mathzhang
 *  Email:mathzhang8@163.com
 */

/**  The Inet4Address class references to java.net.Inet4Address of J2SE1.4 */
 
/**
 * constructor
 * @param str
 */
function Inet4Address(str){
	this.jsjava_class="jsjava.net.Inet4Address";
    this.ip=str;
}

/**
 * return the ip address
 */
Inet4Address.prototype.getAddress=function(){
    return this.ip; 
};

/**
 * return the ip address in bytes array form
 */
Inet4Address.prototype.getBytesAddress=function(){
    return this.ip.split("."); 
};

/**
 * return a string description
 */
Inet4Address.prototype.toString=function(){
    return this.ip; 
};

/**
 * judge whether the input str is  valid ip address
 * @param IPValue
 * @param strict
 */
Inet4Address.isIpAddress=function(IPvalue,strict){
    if(strict != null  && strict == ""){
  /**  */
  if (IPvalue == "0.0.0.0")
   return false;
  else if (IPvalue == "255.255.255.255")
   return false;
 };
 theName = "IPaddress";
  /** match such as 568.335.1.2 */
 var ipPattern = /^(\d{1,3})\.(\d{1,3})\.(\d{1,3})\.(\d{1,3})$/;
 var ipArray = IPvalue.match(ipPattern);
 if (ipArray == null){
  return false;
 }else {
  for (i = 1; i < 5; i++)
  {
   thisSegment = parseInt(ipArray[i]);
   if (thisSegment > 255) {
    return false;
   }
   if (i == 1 && parseInt(ipArray[1]) == 0 ) {
    return false ;
   }
  }
 }
 return true; 
};

/**
 * judge whether the mask is a ip mask address
 * @param mask
 */
Inet4Address.isIpMask=function(mask){
    var _maskTable = new Array("0.0.0.0", "128.0.0.0", "192.0.0.0", "224.0.0.0",
"240.0.0.0", "248.0.0.0", "252.0.0.0", "254.0.0.0", "255.0.0.0", "255.128.0.0",
"255.192.0.0", "255.224.0.0", "255.240.0.0", "255.248.0.0", "255.252.0.0",
"255.254.0.0", "255.255.0.0", "255.255.128.0", "255.255.192.0", "255.255.224.0",
"255.255.240.0", "255.255.248.0", "255.255.252.0", "255.255.254.0", "255.255.255.0",
"255.255.255.128", "255.255.255.192", "255.255.255.224", "255.255.255.240",
"255.255.255.248", "255.255.255.252", "255.255.255.254", "255.255.255.255");
 var i;
 for(i = 1; i< _maskTable.length -1; i++){
  if(mask == _maskTable[i]){
   return true;
  }
 }
 return(false); 
};
