<#--This is the dao template to generate Mybatis dao java class.-->

<#assign class="${sqlTable.entityname?cap_first}">
<#assign package="${prop['project.package']}">
<#assign remark="${sqlTable.remark?default('')}">
<#assign now="${.now?date}">

package ${package}.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import ${package}.entity.${class};

/**
 * 通过@MapperScannerConfigurer扫描目录中的所有接口, 动态在Spring Context中生成实现.
 * 方法名称必须与Mapper.xml中保持一致.
 * 
 * @author yong.cao
 * @create-time ${now}
 * @revision 1.0.0
 * @E_mail yong.cao@renren-inc.com
 */
@MyBatisRepository
public interface ${class}MybatisDao {

    ${class} findBy<#rt>
	<#if sqlTable.primaryKeys?exists>
		<#list sqlTable.primaryKeys as key>
			<#if key_index!=0>And</#if>${key.attrName?cap_first}<#t>
		</#list>
	</#if>
	(<#if sqlTable.primaryKeys?exists><#t>
    	<#list sqlTable.primaryKeys as key>
    	<#if key_index!=0>, </#if>${key.attrType} ${key.attrName?lower_case}<#t>
    	</#list>
    </#if>);<#t>


    List<${class}> findAll();

    List<${class}> find(Map<String, Object> parameters);

    void insert(${class} ${class?lower_case});

    void update(${class} ${class?lower_case});
    
    void delete(<#rt>
    <#if sqlTable.primaryKeys?exists><#t>
    	<#list sqlTable.primaryKeys as key>
    	<#if key_index!=0>, </#if>${key.attrType} ${key.attrName?lower_case}<#t>
    	</#list>
    </#if>);<#t>

	List<${class}> find(Map<String, Object> filterParams,
			RowBounds buildRowBounds);

	int findTotalNum(Map<String, Object> filterParams);
	
	${class} get${class}By<#rt>
	<#if sqlTable.primaryKeys?exists>
		<#list sqlTable.primaryKeys as key>
			<#if key_index!=0>And</#if>${key.attrName?cap_first}<#t>
		</#list>
	</#if>
	(<#if sqlTable.primaryKeys?exists><#t>
    	<#list sqlTable.primaryKeys as key>
    	<#if key_index!=0>, </#if>${key.attrType} ${key.attrName?lower_case}<#t>
    	</#list>
    </#if>);<#t>
}
