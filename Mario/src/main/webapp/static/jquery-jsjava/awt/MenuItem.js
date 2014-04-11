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

/**  The MenuItem class references to java.awt.MenuItem of J2SE1.4 */
 
/**
 * constructor
 * @param label
 * @param shortCut
 */
function MenuItem(label,shortCut){
	this.jsjava_class="jsjava.awt.MenuItem";
    this.label=label;
    this.shortCut=shortCut;
    this.listeners=new ArrayList();
}

/**
 * Adds the specified action listener to receive action events from this menu item
 * @param listener
 */
MenuItem.prototype.addActionListener=function(listener){
    this.listeners.add(listener);
};

/**
 * Delete any MenuShortcut object associated with this menu item
 */
MenuItem.prototype.deleteShortcut=function(s){
    if(this.shortcut.equals(s)){
    	this.shortcut=null;
    }
};


/**
 * Gets the command name of the action event that is fired by this menu item
 */
MenuItem.prototype.getActionCommand=function(){
    return this.command;	
};

/**
 * Returns an array of all the action listeners registered on this menu item
 */
MenuItem.prototype.getActionListeners=function(){
    return this.label;	
};

/**
 * Gets the label for this menu item
 */
MenuItem.prototype.getLabel=function(){
    return this.label;	
};

/**
 * Returns an array of all the objects currently registered as FooListeners upon this MenuItem
 */
MenuItem.prototype.getListeners=function(listenerType){
    return this.listeners;
};

/**
 * Get the name of the component to the specified string
 */
MenuItem.prototype.getName=function(){
    return this.name;	
};

/**
 * Get the MenuShortcut object associated with this menu item
 */
MenuItem.prototype.getShortcut=function(){
    return this.shortCut;	
};

/**
 * Processes action events occurring on this menu item
 * @param actionEvent
 */
MenuItem.prototype.processActionEvent=function(actionEvent){
    for(var i=0;i<this.listeners.size();i++){
    	var listener=this.listeners.get(i);
    	listener.actionPerformed(actionEvent);
    }
};

/**
 * Removes the specified action listener so it no longer receives action events from this menu item
 * @param listener
 */
MenuItem.prototype.removeActionListener=function(listener){
   this.listeners.remove(listener);	
};

/**
 * Checks whether this menu item is enabled
 */
MenuItem.prototype.isEnabled=function(){
    return this.enabled;	
};

/**
 * Sets the command name of the action event that is fired by this menu item
 * @param command
 */
MenuItem.prototype.setActionCommand=function(command){
    this.command=command;	
};

/**
 * Sets whether or not this menu item can be chosen
 * @param enabled
 */
MenuItem.prototype.setEnabled=function(enabled){
    this.enabled=enabled;	
};

/**
 * Sets the label for this menu item to the specified label
 * @param label
 */
MenuItem.prototype.setLabel=function(label){
    this.label=label;	
};

/**
 * Sets the name of the component to the specified string
 * @param name
 */
MenuItem.prototype.setName=function(name){
    this.name=name;	
};

/**
 * Set the MenuShortcut object associated with this menu item
 * @param shortCut
 */
MenuItem.prototype.setShortcut=function(shortCut){
	if(shortCut instanceof MenuShortcut){
    	this.shortCut=shortCut;	
    }
};