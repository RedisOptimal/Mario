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

/**  The URL class references to java.net.URL of J2SE1.4 */
 
/**
 * constructor
 * @param url
 */
function URL(url){
	this.jsjava_class="jsjava.net.URL";
    this.url=url;
    var schemePos=url.indexOf("://");
    var filePos=url.indexOf("/",schemePos+3);
    var portPos=url.indexOf(":",schemePos+1);
    if(portPos>filePos){
        portPos=-1;	
    }
    var queryPos=url.indexOf("?");
    var anchorPos=url.indexOf("#");
    this.protocol=url.substring(0,schemePos);
    this.host=url.substring(schemePos+3,filePos);
    if(portPos!=-1){
        this.host=url.substring(schemePos+3,portPos);	        
    }
    if(portPos!=-1){
        this.port=url.substring(portPos+1,filePos);	
    }else{
        this.port=getDefaultPort(this.scheme);
    }
    function getDefaultPort(protocol){
    	var defautPort=null;
        switch(protocol){
            case "http":defaultPort="80";break;	
            case "ftp":defaultPort="21";break;
            case "gopher":defaultPort="70";break;            
            default:defaultPort="80";break;
        }
        return defaultPort;
    } 
    if(anchorPos!=-1){
        this.file=url.substring(filePos,anchorPos);
        this.ref=url.substring(anchorPos+1);
    }else{
        this.file=url.substring(filePos);
         this.ref=null;
    }
    if(queryPos==-1){
        this.path=this.file;	
        this.query=null;
    }else{
        this.path=url.substring(filePos,queryPos);
        if(anchorPos!=-1){
            this.query=url.substring(queryPos+1,anchorPos);
        }else{
            this.query=url.substring(queryPos+1);   
        }
    }
    if(url.indexOf("mailto:")!=-1){
        this.protocol="mailto";
        this.host=null;
        this.file=this.path=url.substring("mailto:".length);
        this.port="-1";	
    }
}

/**
 * return the default port by protocol
 */
URL.prototype.getDefaultPort=function(){
    var defautPort=null;
    switch(this.protocol){
        case "http":defaultPort="80";break;	
        case "ftp":defaultPort="21";break;
        case "gopher":defaultPort="70";break;    
        case "file":defaultPort="-1";break;  
        case "mailto":defaultPort="-1";break;        
        default:defaultPort="80";break;
    }
    return defaultPort;
};

/**
 * return the virtual disk path
 */
URL.prototype.getFile=function(){
    return this.file;
};

/**
 * reuturn the host address
 */
URL.prototype.getHost=function(){
    return this.host;	
};

/**
 * return the virtual disk path
 */
URL.prototype.getPath=function(){
    return this.path;
};

/**
 * return the port
 */
URL.prototype.getPort=function(){
    return this.port;
};

/**
 * return the protocol
 */
URL.prototype.getProtocol=function(){
    return this.protocol;
};

/**
 * return the query string
 */
URL.prototype.getQuery=function(){
    return this.query;
};

/**
 * return url anchor
 */
URL.prototype.getRef=function(){
    return this.ref;
};

/**
 * return user info path
 */
URL.prototype.getUserInfo=function(){
    return null;
};