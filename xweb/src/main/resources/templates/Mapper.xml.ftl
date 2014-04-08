<#--This is the mapper template to generate Mapper xml file.-->

<#assign class="${sqlTable.entityname?cap_first}">
<#assign package="${prop['project.package']}">
<#assign remark="${sqlTable.remark?default('')}">
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" 
"http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<!-- namespace必须指向Dao接口 -->
<mapper namespace="${package}.repository.${class?lower_case}MybatisDao">

    <select id="findById" resultType="${class}">
        select 
        <#if sqlTable.sqlColumns?exists>
        <#list sqlTable.sqlColumns as column >
        <#if column_index!=0>, </#if>${column.attrName?lower_case}<#rt>
        </#list>
        </#if>
        from ${sqlTable.entityname}
        where 
        <#if sqlTable.primaryKeys?exists><#rt>
        <#list sqlTable.primaryKeys as key>
        <#if key_index!=0> and </#if>${key.attrName?lower_case} = ${r"#{"}${key.attrName?lower_case}}<#rt>
        </#list>
        </#if>
        
    </select>

    <select id="findAll" resultType="${class}">
           select 
        <#if sqlTable.sqlColumns?exists>
        <#list sqlTable.sqlColumns as column >
        <#if column_index!=0>, </#if>${column.attrName?lower_case}<#rt>
        </#list>
        </#if>
        from ${sqlTable.entityname}
        order by 
        <#if sqlTable.primaryKeys?exists><#rt>
        <#list sqlTable.primaryKeys as key>
        <#if key_index!=0>, </#if>${key.attrName?lower_case} desc<#rt>
        </#list>
        </#if>
        
    </select>

    <select id="find" parameterType="map" resultType="${class}">
        select 
        <#if sqlTable.sqlColumns?exists>
        <#list sqlTable.sqlColumns as column >
        <#if column_index!=0>, </#if>${column.attrName?lower_case}<#rt>
        </#list>
        </#if>
        from ${sqlTable.entityname}
           <where>
            <#if sqlTable.sqlColumns?exists>
            <#list sqlTable.sqlColumns as column >
            <if test="${column.attrName} != null and ${column.attrName} != ''">
                and ${column.attrName} = ${r"#{"}${column.attrName}}
            </if>
            </#list>
            </#if>
        </where>
    </select>
    
    <select id="findTotalNum" parameterType="${class}" resultType="int">
        select count(1) from ${class?lower_case}
    </select>

    <insert id="insert" parameterType="${class}">
        insert into ${class?lower_case}
        (<#if sqlTable.sqlColumns?exists>
        <#list sqlTable.sqlColumns as column >
        <#if column_index!=0>, </#if>${column.attrName?lower_case}<#rt>
        </#list>
        </#if>)
        values (<#if sqlTable.sqlColumns?exists>
        <#list sqlTable.sqlColumns as column >
        <#if column_index!=0>, </#if>${r"#{"}${column.attrName?lower_case}}<#rt>
        </#list>
        </#if>)
    </insert>

    <update id="update" parameterType="${class}">
        update ${class?lower_case}<#rt>
		set <#if sqlTable.sqlColumns?exists><#rt>
        <#list sqlTable.sqlColumns as column>
        <#if column_index!=0>, </#if>${column.attrName?lower_case} = ${r"#{"}${column.attrName?lower_case}}<#rt>
        </#list>
        </#if>
		where <#if sqlTable.primaryKeys?exists><#rt>
        <#list sqlTable.primaryKeys as key>
        <#if key_index!=0> and </#if>${key.attrName?lower_case} = ${r"#{"}${key.attrName?lower_case}}<#rt>
        </#list>
        </#if> 
        
    </update>

    <delete id="delete" parameterType="${class}">
        delete from ${class?lower_case} where <#if sqlTable.primaryKeys?exists><#rt>
        <#list sqlTable.primaryKeys as key>
        <#if key_index!=0> and </#if>${key.attrName?lower_case} = ${r"#{"}${key.attrName?lower_case}}<#rt>
        </#list>
        </#if>
        
    </delete>
</mapper> 
