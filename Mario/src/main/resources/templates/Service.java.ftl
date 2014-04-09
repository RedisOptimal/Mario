<#--This is the service template to generate service java class.-->

<#assign class="${sqlTable.entityname?cap_first}">
<#assign package="${prop['project.package']}">
<#assign remark="${sqlTable.remark?default('')}">
<#assign now="${.now?date}">

package ${package}.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import ${package}.entity.${class};
import ${package}.repository.${class}MybatisDao;

@Component
@Transactional
public class ${class}Service {

    @Autowired
    private ${class}MybatisDao ${class?lower_case}Dao;

    /**
     * 创建分页查询.
     * 
     * @param filterParams
     * @param pageNumber
     * @param pageSize
     * @param sortType
     * @return Page<Test>
     */
    public Page<${class}> get${class}(Map<String, Object> filterParams, int pageNumber, int pageSize) {

        List<${class}> ${class?lower_case}s = ${class?lower_case}Dao.find(filterParams, buildRowBounds(pageNumber, pageSize));
        int totalnum = ${class?lower_case}Dao.findTotalNum(filterParams);

        return new PageImpl<${class}>(${class?lower_case}s, new PageRequest(pageNumber - 1, pageSize), totalnum);
    }

    /**
     * 查找所有的${class}.
     * 
     * @return List<${class}>
     */
    public List<${class}> getAll${class}() {
        return ${class?lower_case}Dao.findAll();
    }

    /**
     * 创建分页请求.
     * 
     * @param pageNumber
     * @param pageSize
     * @return RowBounds
     */
    private RowBounds buildRowBounds(int pageNumber, int pageSize) {
        return new RowBounds((pageNumber - 1) * pageSize, pageSize);
    }

    /**
     * 保存${class}.
     * 
     * @param new${class}
     */
    public void save${class}(${class} new${class}) {
        if (<#if sqlTable.primaryKeys?exists><#rt>
        <#list sqlTable.primaryKeys as key>
    	<#if key_index!=0>&& </#if>new${class}.get${key.attrName?cap_first}() != null <#t>
    	</#list>
    </#if>) {
            ${class?lower_case}Dao.update(new${class});
        } else {
            ${class?lower_case}Dao.insert(new${class});
        }
    }

    /**
     * 根据主键获取${class}
     * 
     * @param id
     * @return ${class}
     */
    public Test get${class}(<#if sqlTable.primaryKeys?exists><#rt>
    	<#list sqlTable.primaryKeys as key>
    	<#if key_index!=0>, </#if>${key.attrType} ${key.attrName?lower_case}<#t>
    	</#list>
    </#if>) {
        return ${class?lower_case}Dao.get${class}By<#rt>
	<#if sqlTable.primaryKeys?exists>
		<#list sqlTable.primaryKeys as key>
			<#if key_index!=0>And</#if>${key.attrName?cap_first}<#t>
		</#list>
	</#if>(<#if sqlTable.primaryKeys?exists><#rt>
<#list sqlTable.primaryKeys as key>
<#if key_index!=0>, </#if>${key.attrName?lower_case}<#rt>
</#list></#if>);
    }

    /**
     * 根据主键删除${class}
     * 
     * @param id
     */
    public void delete${class}(<#if sqlTable.primaryKeys?exists><#rt>
<#list sqlTable.primaryKeys as key>
<#if key_index!=0>, </#if>${key.attrType} ${key.attrName?lower_case}<#rt>
</#list></#if>) {
        ${class?lower_case}Dao.delete(<#if sqlTable.primaryKeys?exists><#rt>
<#list sqlTable.primaryKeys as key>
<#if key_index!=0>, </#if>${key.attrName?lower_case}<#rt>
</#list></#if>);
    }

}
