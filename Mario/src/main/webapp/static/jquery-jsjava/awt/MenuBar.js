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

/**  The MenuBar class references to java.awt.MenuBar of J2SE1.4 */
 
/**
 * constructor
 */
function MenuBar(){
	this.jsjava_class="jsjava.awt.MenuBar";
    this.menus=new ArrayList();
}

/**
 * Adds the specified menu item to this menu
 */
MenuBar.prototype.add=function(menu){
    if(menu instanceof Menu){
    	this.menus.add(menu);
    	return;
    }
    throw new IllegalArgumentException(IllegalArgumentException.ERROR,"Invalid arguments");
};

/**
 * Deletes the specified menu shortcut
 * @param shortCut
 */
MenuBar.prototype.deleteShortcut=function(shortCut){
    var nmenus = this.getMenuCount();
	for (var i = 0 ; i < nmenus ; i++) {
	    this.getMenu(i).deleteShortcut(s);
    }
};

/**
 * Gets the help menu on the menu bar
 */
MenuBar.prototype.getHelpMenu=function(){
    return this.helpMenu;
};

/**
 * Gets the specified menu
 * @param index
 */
MenuBar.prototype.getMenu=function(index){
    return this.menus.get(index);
};

/**
 * Gets the number of menus on the menu bar
 */
MenuBar.prototype.getMenuCount=function(){
    return this.menus.size();
};

/**
 * Get the name of the component to the specified string
 */
MenuBar.prototype.getName=function(){
    return this.name;	
};

/**
 * Gets the instance of MenuItem associated with the specified MenuShortcut object
 * @param shortCut
 */
MenuBar.prototype.getShortcutMenuItem=function(shortCut){
    
};

/**
 * Removes the specified menu component from this menu bar
 * @param menu
 */
MenuBar.prototype.remove=function(menu){
    this.menus.remove(menu);
};

/**
 * Removes the menu located at the specified index from this menu bar
 * @param index
 */
MenuBar.prototype.removeIndexOf=function(index){
    this.menus.removeIndexOf(index);
};

/**
 * Sets the specified menu to be this menu bar's help menu
 * @param menu
 */
MenuBar.prototype.setHelpMenu=function(menu){
    this.helpMenu=menu;
};

/**
 * Sets the name of the component to the specified string
 * @param name
 */
MenuBar.prototype.setName=function(name){
    this.name=name;	
};

/**
 * Gets an enumeration of all menu shortcuts this menu bar is managing
 */
MenuBar.prototype.shortcuts=function(){
    var _shortcuts=new Vector();
    var nmenus = this.getMenuCount();
	for (var i = 0 ; i < nmenus ; i++) {
	    var e = this.getMenu(i).shortcuts();
	    while (e.hasMoreElements()) {
	        _shortcuts.addElement(e.nextElement());
	    }
	}
    return _shortcuts.elements();
};