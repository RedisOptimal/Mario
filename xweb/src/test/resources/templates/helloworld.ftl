FreeMarker Template example: ${message}  
${r"${foo}"}
=======================
===  County List   ====
=======================
<#list countries as country>
	${country_index + 1}. ${country}
</#list>