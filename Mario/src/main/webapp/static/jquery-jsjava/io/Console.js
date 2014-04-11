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

/**
 * constructor
 */
function Console(win,title){
	this.jsjava_class="jsjava.io.Console";
	this.win=win;
	this.state=Console.STATE_UNSTARTED;
	this.consoleWin=null;
	this.title=title;
	if(!title||title==""||title==null){
		this.title="Javascript Console";
	}
}

Console.prototype=new OutputDevice();
Console.prototype.constructor=Console;

Console.STATE_UNSTARTED=0;
Console.STATE_RUNNING=1;
Console.STATE_FINISHED=2;

var jsjava_console_instance;

/**
 * Open the console.
 * @param title console title.
 */
Console.open=function(title){
	jsjava_console_instance=new Console(window,title);
	System.console=jsjava_console_instance;
	System.out=new PrintStream(new OutputStream(jsjava_console_instance));
	System.err=new PrintStream(new OutputStream(jsjava_console_instance));
	jsjava_console_instance.start();
};

/**
 * Start the console.
 */
Console.prototype.start=function(){
	this.state=Console.STATE_RUNNING;
	var consoleWinWidth=window.screen.width/1.5;
	var consoleWinHeight=window.screen.height/2;
	this.consoleWin=window.open("about:blank","_blank","resizable=yes,menubar=no,scrollbars=yes,toolbar=no,menubar=no,width="+consoleWinWidth+",height="+consoleWinHeight);
	var cwinContent="<html>";
	cwinContent+="<head><script>";
	cwinContent+="var isIE=navigator.userAgent.indexOf('MSIE')!=-1;";
	cwinContent+="function deal_jsjava_console_event(e){if(isIE){if(event.keyCode==67&&event.ctrlKey){window.close();};}else{if(e.keyCode==67&&e.ctrlKey){window.close();}}}";
	cwinContent+="document.onkeydown=deal_jsjava_console_event;";
	cwinContent+="</script></head>";
	if(!BrowserUtils.isIE()){
		this.consoleWin.document.open();	
	}
	cwinContent+="<body leftmargin='2' topmargin='2' bgcolor='#000000' text='#FFFFFF' style='font-size:11pt;'></body></html>";
	this.consoleWin.document.write(cwinContent);
	this.consoleWin.document.title=this.title;
	if(!BrowserUtils.isIE()){
		this.consoleWin.document.close();	
	}
	this.println("Javascript Console started");
};

/**
 * Stop the console.
 */
Console.prototype.stop=function(){
	this.state=Console.STATE_FINISHED;
	this.println("Javascript Console stopped");
};

/**
 * Close the console.
 */
Console.prototype.close=function(){
	if(this.state!==Console.STATE_FINISHED){
		this.stop();
	}
	this.consoleWin.close();
};

/**
 * Print the message to the console,and then terminate the line.
 * @param msg
 */
Console.prototype.println=function(msg){
    var printStr=this.consoleTimeText(new Date())+"&nbsp;&nbsp;"+msg+"<br>";
    if(BrowserUtils.isIE()){
    	this.consoleWin.document.write(printStr);
    }else{
    	this.consoleWin.document.body.innerHTML+=printStr;
    }
};

/**
 * Print the message to the console.
 * @param msg
 */
Console.prototype.print=function(msg){
    var printStr=this.consoleTimeText(new Date())+"&nbsp;&nbsp;"+msg;
    if(BrowserUtils.isIE()){
    	this.consoleWin.document.write(printStr);
    }else{
    	this.consoleWin.document.body.innerHTML+=printStr;
    }
};

/**
 * Convert a date to a string with specialized pattern.
 * @param date
 */
Console.prototype.consoleTimeText=function(date){
	var df=new SimpleDateFormat();
    df.applyPattern("yyyy-MM-dd hh:mm:ss");
    return df.format(date);
};