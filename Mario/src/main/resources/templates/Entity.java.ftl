<#--This is the entity template to generate entity java class.-->

<#assign class="${sqlTable.entityname?cap_first}">
<#assign package="${prop['project.package']}">
<#assign remark="${sqlTable.remark?default('')}">
<#assign now="${.now?date}">

package ${package}.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * ${remark}
 * 
 * @author yong.cao
 * @create-time ${now}
 * @revision 1.0.0
 * @E_mail yong.cao@renren-inc.com
 */
public class ${class} {

    <#list sqlTable.sqlColumns as column >
    /**
    * ${column.remark}
    */
    private ${column.attrType} ${column.attrName?lower_case};
    </#list>
    
    /**
    *
    * ${class} 的缺省构造方法
    *
    */
    public ${class} () {
    }
    
    <#list sqlTable.sqlColumns as column >
    /**
    *
    * ${column.remark}get方法
    *
    */
    public ${column.attrType} get${column.attrName?cap_first}(){
        return this.${column.attrName?lower_case};
    }
    /**
    *
    * ${column.remark}set方法
    *
    */
    public void set${column.attrName}(${column.attrType} ${column.attrName?lower_case}){
        this.${column.attrName?lower_case} = ${column.attrName?lower_case};
    }
	</#list>
	
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}