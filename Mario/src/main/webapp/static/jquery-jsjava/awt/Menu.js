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

/**  The Menu class references to java.awt.Menu of J2SE1.4 */
 
/**
 * constructor
 * @param label
 */
function Menu(label){
	this.jsjava_class="jsjava.awt.Menu";
    this.label=label;
    this.items=new ArrayList();
    this.listeners=new ArrayList();
}

Menu.prototype=new MenuItem();
Menu.prototype.constructor=Menu;

/**
 * Adds the specified action listener to receive action events from this menu item
 * @param listener
 */
Menu.prototype.addActionListener=function(listener){
    this.listeners.add(listener);
};

/**
 * Returns an array of all the action listeners registered on this menu item
 */
Menu.prototype.getActionListeners=function(){
    return this.label;	
};

/**
 * Adds the specified menu item to this menu
 */
Menu.prototype.add=function(item){
    if(item instanceof MenuItem){
    	this.items.add(item);
    	return;
    }
    if(item instanceof String){
    	var im=new MenuItem(item);
    	this.items.add(im);
    	return;
    }
    throw new IllegalArgumentException(IllegalArgumentException.ERROR,"Invalid arguments");
};

/**
 * Delete any MenuShortcut object associated with this menu
 */
Menu.prototype.deleteShortcut=function(s){
    var nitems = this.getItemCount();
	for (var i = 0 ; i < nitems ; i++) {
	    this.getItem(i).deleteShortcut(s);
	}
};

/**
 * Gets the item located at the specified index of this menu
 * @param index
 */
Menu.prototype.getItem=function(index){
    return this.items.get(index);
};

/**
 * Gets the item located at the specified index of this menu
 * @param index
 */
Menu.prototype.getItemCount=function(){
    return this.items.size();
};

/**
 * Returns an array of all the objects currently registered as FooListeners upon this MenuItem
 */
Menu.prototype.getListeners=function(listenerType){
    return this.listeners;
};

/**
 * Inserts a menu item into this menu at the specified position
 * @param item
 * @param index
 */
Menu.prototype.insert=function(item,index){
    if(item instanceof MenuItem){
    	this.items.addIndexOf(index,item);
    	return;
    }
    if(item instanceof String){
    	var im=new MenuItem(item);
    	this.items.addIndexOf(index,im);
    	return;
    }
    throw new IllegalArgumentException(IllegalArgumentException.ERROR,"Invalid arguments");
};

/**
 * Processes action events occurring on this menu item
 * @param actionEvent
 */
Menu.prototype.processActionEvent=function(actionEvent){
    for(var i=0;i<this.listeners.size();i++){
    	var listener=this.listeners.get(i);
    	listener.actionPerformed(actionEvent);
    }
};

/**
 * Removes the specified menu item from this menu
 * @param index
 */
Menu.prototype.remove=function(item){
    this.items.remove(item);	
};

/**
 * Removes the specified action listener so it no longer receives action events from this menu item
 * @param listener
 */
Menu.prototype.removeActionListener=function(listener){
   this.listeners.remove(listener);	
};

/**
 * Removes all items from this menu
 */
Menu.prototype.removeAll=function(){
    this.items.removeAll();	
};

/**
 * Removes the menu item at the specified index from this menu
 * @param index
 */
Menu.prototype.removeIndexOf=function(index){
    this.items.removeIndexOf(index);	
};

/**
 * Gets an enumeration of all menu shortcuts this menu bar is managing
 */
Menu.prototype.shortcuts=function(){
	var _shortcuts=new Vector();
    var nitems = this.getItemCount();
    for(var i=0;i<nitems;i++){
    	var item=this.getItem(i);
    	if(item instanceof Menu){
    		var ss=item.shortcuts();
    		while(ss.hasMoreElements()){
    			var s=ss.nextElement();
    			_shortcuts.addElement(s);
    		}
    	}else if(item instanceof MenuItem){
    		var s=item.getShortcut();
    		if(s!=undefined&&s!=null){
    			_shortcuts.addElement(s);
    		}
    	}else{
    		
    	}
    }	
    return _shortcuts.elements();
};