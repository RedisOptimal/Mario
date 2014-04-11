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

 /**  The Locale class references to java.util.Locale of J2SE1.4 */

/**
 * Construct a locale from language, country, variant.
 */
function Locale(language,country,variant){
	this.jsjava_class="jsjava.util.Locale";
	this.language=language;
	this.country=country;
	this.variant=variant;
	this.displayLanguage="";
	this.displayCountry="";
	this.displayVariant="";
}
Locale.locales=[["ar","Arabic","","","",""],["ar","Arabic","AE","United Arab Emirates","",""],["ar","Arabic","BH","Bahrain","",""],["ar","Arabic","DZ","Algeria","",""],["ar","Arabic","EG","Egypt","",""],["ar","Arabic","IQ","Iraq","",""],["ar","Arabic","JO","Jordan","",""],["ar","Arabic","KW","Kuwait","",""],["ar","Arabic","LB","Lebanon","",""],["ar","Arabic","LY","Libya","",""],["ar","Arabic","MA","Morocco","",""],["ar","Arabic","OM","Oman","",""],["ar","Arabic","QA","Qatar","",""],["ar","Arabic","SA","Saudi Arabia","",""],["ar","Arabic","SD","Sudan","",""],["ar","Arabic","SY","Syria","",""],["ar","Arabic","TN","Tunisia","",""],["ar","Arabic","YE","Yemen","",""],["hi","Hindi","IN","India","",""],["iw","Hebrew","","","",""],["iw","Hebrew","IL","Israel","",""],["ja","Japanese","","","",""],["ja","Japanese","JP","Japan","",""],["ko","Korean","","","",""],["ko","Korean","KR","South Korea","",""],["th","Thai","","","",""],["th","Thai","TH","Thailand","",""],["th","Thai","TH","Thailand","TH","TH"],["zh","Chinese","","","",""],["zh","Chinese","CN","China","",""],["zh","Chinese","HK","Hong Kong","",""],["zh","Chinese","TW","Taiwan","",""],["be","Byelorussian","","","",""],["be","Byelorussian","BY","Belarus","",""],["bg","Bulgarian","","","",""],["bg","Bulgarian","BG","Bulgaria","",""],["ca","Catalan","","","",""],["ca","Catalan","ES","Spain","",""],["cs","Czech","","","",""],["cs","Czech","CZ","Czech Republic","",""],["da","Danish","","","",""],["da","Danish","DK","Denmark","",""],["de","German","","","",""],["de","German","AT","Austria","",""],["de","German","CH","Switzerland","",""],["de","German","DE","Germany","",""],["de","German","LU","Luxembourg","",""],["el","Greek","","","",""],["el","Greek","GR","Greece","",""],["en","English","AU","Australia","",""],["en","English","CA","Canada","",""],["en","English","GB","United Kingdom","",""],["en","English","IE","Ireland","",""],["en","English","IN","India","",""],["en","English","NZ","New Zealand","",""],["en","English","ZA","South Africa","",""],["es","Spanish","","","",""],["es","Spanish","AR","Argentina","",""],["es","Spanish","BO","Bolivia","",""],["es","Spanish","CL","Chile","",""],["es","Spanish","CO","Colombia","",""],["es","Spanish","CR","Costa Rica","",""],["es","Spanish","DO","Dominican Republic","",""],["es","Spanish","EC","Ecuador","",""],["es","Spanish","ES","Spain","",""],["es","Spanish","GT","Guatemala","",""],["es","Spanish","HN","Honduras","",""],["es","Spanish","MX","Mexico","",""],["es","Spanish","NI","Nicaragua","",""],["es","Spanish","PA","Panama","",""],["es","Spanish","PE","Peru","",""],["es","Spanish","PR","Puerto Rico","",""],["es","Spanish","PY","Paraguay","",""],["es","Spanish","SV","El Salvador","",""],["es","Spanish","UY","Uruguay","",""],["es","Spanish","VE","Venezuela","",""],["et","Estonian","","","",""],["et","Estonian","EE","Estonia","",""],["fi","Finnish","","","",""],["fi","Finnish","FI","Finland","",""],["fr","French","","","",""],["fr","French","BE","Belgium","",""],["fr","French","CA","Canada","",""],["fr","French","CH","Switzerland","",""],["fr","French","FR","France","",""],["fr","French","LU","Luxembourg","",""],["hr","Croatian","","","",""],["hr","Croatian","HR","Croatia","",""],["hu","Hungarian","","","",""],["hu","Hungarian","HU","Hungary","",""],["is","Icelandic","","","",""],["is","Icelandic","IS","Iceland","",""],["it","Italian","","","",""],["it","Italian","CH","Switzerland","",""],["it","Italian","IT","Italy","",""],["lt","Lithuanian","","","",""],["lt","Lithuanian","LT","Lithuania","",""],["lv","Latvian (Lettish)","","","",""],["lv","Latvian (Lettish)","LV","Latvia","",""],["mk","Macedonian","","","",""],["mk","Macedonian","MK","Macedonia","",""],["nl","Dutch","","","",""],["nl","Dutch","BE","Belgium","",""],["nl","Dutch","NL","Netherlands","",""],["no","Norwegian","","","",""],["no","Norwegian","NO","Norway","",""],["no","Norwegian","NO","Norway","NY","Nynorsk"],["pl","Polish","","","",""],["pl","Polish","PL","Poland","",""],["pt","Portuguese","","","",""],["pt","Portuguese","BR","Brazil","",""],["pt","Portuguese","PT","Portugal","",""],["ro","Romanian","","","",""],["ro","Romanian","RO","Romania","",""],["ru","Russian","","","",""],["ru","Russian","RU","Russia","",""],["sh","Serbo-Croatian","","","",""],["sh","Serbo-Croatian","YU","Yugoslavia","",""],["sk","Slovak","","","",""],["sk","Slovak","SK","Slovakia","",""],["sl","Slovenian","","","",""],["sl","Slovenian","SI","Slovenia","",""],["sq","Albanian","","","",""],["sq","Albanian","AL","Albania","",""],["sr","Serbian","","","",""],["sr","Serbian","YU","Yugoslavia","",""],["sv","Swedish","","","",""],["sv","Swedish","SE","Sweden","",""],["tr","Turkish","","","",""],["tr","Turkish","TR","Turkey","",""],["uk","Ukrainian","","","",""],["uk","Ukrainian","UA","Ukraine","",""],["en","English","","","",""],["en","English","US","United States","",""]];

/**
 * Returns a list of all installed locales.
 */
Locale.getAvailableLocales=function(){
	var length=Locale.locales.length;
	var larr=new Array(length);
	for(var i=0;i<length;i++){
		var arr=Locale.locales[i];
		var locale=new Locale(arr[0],arr[2],arr[4]);
		locale.displayLanguage=arr[1];
		locale.displayCountry=arr[3];
		locale.displayVariant=arr[5];
		larr[i]=locale;
	}
	return larr;
};

/**
 * Returns the country/region code for this locale, which will either be the 
 * empty string or an upercase ISO 3166 2-letter code.
 */
Locale.prototype.getCountry=function(){
	return this.country;
};

/**
 * Returns a name for the locale's country that is appropriate for display to the user.
 */
Locale.prototype.getDisplayCountry=function(){
	return this.displayCountry;
};

/**
 * Returns the language code for this locale, which will either be the empty string or a lowercase ISO 639 code.
 */
Locale.prototype.getLanguage=function(){
	return this.language;
};

/**
 * Returns a name for the locale's language that is appropriate for display to the user.
 */
Locale.prototype.getDisplayLanguage=function(){
	return this.displayLanguage;
};

/**
 * Returns the variant code for this locale.
 */
Locale.prototype.getVariant=function(){
	return this.variant;
};

/**
 * Returns a name for the locale's variant code that is appropriate for display to the user.
 */
Locale.prototype.getDisplayVariant=function(){
	return this.displayVariant;
};

/**
 * Getter for the programmatic name of the entire locale, with the language, country and variant separated by underbars.
 */
Locale.prototype.toString=function(){
	var str="";
	var language=this.language;
	if(language!=""){
		str+=language;
	}
	var country=this.country;
	if(country!=""){
		str+="-"+country;
	}
	return str;
};

/**
 * Returns true if this Locale is equal to another object.
 */
Locale.prototype.equals=function(o){
	if(!o){
		return false;
	}
	if(o.jsjava_class&&o.jsjava_class=="jsjava.util.Locale"){
        if(this.language==o.language&&this.country==o.country&&this.variant==o.variant){
			return true;
		}	
	}
	return false;
};