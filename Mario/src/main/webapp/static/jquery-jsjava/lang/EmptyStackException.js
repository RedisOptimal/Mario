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

/**  The EmptyStackException class references to java.lang.EmptyStackException of J2SE1.4 */

EmptyStackException.prototype=new Error();
EmptyStackException.prototype.constructor=EmptyStackException;

EmptyStackException.ERROR=0;

/**
 * constructor
 * @param code error code
 * @param message error message
 */
function EmptyStackException(code,message){
	this.jsjava_class="jsjava.lang.EmptyStackException";
	this.code=code;
    this.message=message;
    this.name="jsjava.lang.EmptyStackException";
}