<#--This is the controller template to generate controller class file.-->

<#assign class="${sqlTable.entityname?cap_first}">
<#assign package="${prop['project.package']}">
<#assign remark="${sqlTable.remark?default('')}">

package ${package}.web;

import java.util.Map;

import javax.servlet.ServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springside.modules.web.Servlets;

import ${package}.entity.${class};
import ${package}.service.${class}Service;
import ${package}.util.Const;

@Controller
@RequestMapping("/${class?lower_case}")
public class ${class}Controller {

    @Autowired
    private ${class}Service service;

    @RequestMapping(value = { "", "/list" })
    public String list(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
            Model model, ServletRequest request) {

        Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
        Page<${class}> ${class?lower_case}s = service.get${class}(searchParams, pageNumber, Const.PAGE_SIZE);

        model.addAttribute("${class?lower_case}s", ${class?lower_case}s);
        model.addAttribute("searchParams",
                Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));

        return "${class?lower_case}/${class?lower_case}List";
    }

    @RequestMapping(value = "create", method = RequestMethod.GET)
    public String createForm(Model model) {
        model.addAttribute("${class?lower_case}", new ${class}());
        model.addAttribute("action", "create");
        return "${class?lower_case}/${class?lower_case}Form";
    }

    @RequestMapping(value = "create", method = RequestMethod.POST)
    public String create(@Valid ${class} new${class}, RedirectAttributes redirectAttributes) {
        service.save${class}(new${class});
        redirectAttributes.addFlashAttribute("message", "创建${remark}成功");
        return "redirect:/${class?lower_case}/";
    }

    @RequestMapping(value = "update/<#if sqlTable.primaryKeys?exists><#rt>
<#list sqlTable.primaryKeys as key>
<#if key_index!=0>/</#if>{${key.attrName?lower_case}}<#rt>
</#list></#if>", method = RequestMethod.GET)
    public String updateForm(<#if sqlTable.primaryKeys?exists><#rt>
<#list sqlTable.primaryKeys as key>
<#if key_index!=0>, </#if> @PathVariable("${key.attrName?lower_case}") ${key.attrType} ${key.attrName?lower_case}<#rt>
</#list></#if>, Model model) {
        model.addAttribute("${class?lower_case}", service.get${class}(<#if sqlTable.primaryKeys?exists><#rt>
<#list sqlTable.primaryKeys as key>
<#if key_index!=0>, </#if>${key.attrName?lower_case}<#rt>
</#list></#if>));
        model.addAttribute("action", "update");
        return "${class?lower_case}/${class?lower_case}Form";
    }

    @RequestMapping(value = "update", method = RequestMethod.POST)
    public String update(@Valid @ModelAttribute("preload${class}") ${class} ${class?lower_case},
            RedirectAttributes redirectAttributes) {
        service.save${class}(${class?lower_case});
        redirectAttributes.addFlashAttribute("message", "更新${remark}成功");
        return "redirect:/${class?lower_case}/";
    }

    @RequestMapping(value = "delete/<#if sqlTable.primaryKeys?exists><#rt>
<#list sqlTable.primaryKeys as key>
<#if key_index!=0>/</#if>{${key.attrName?lower_case}}<#rt>
</#list></#if>")
    public String delete(<#if sqlTable.primaryKeys?exists><#rt>
<#list sqlTable.primaryKeys as key>
<#if key_index!=0>, </#if> @PathVariable("${key.attrName?lower_case}") ${key.attrType} ${key.attrName?lower_case}<#rt>
</#list></#if>, RedirectAttributes redirectAttributes) {
        service.delete${class}(<#if sqlTable.primaryKeys?exists><#rt>
<#list sqlTable.primaryKeys as key>
<#if key_index!=0>, </#if>${key.attrName?lower_case}<#rt>
</#list></#if>);
        redirectAttributes.addFlashAttribute("message", "删除${remark}成功");
        return "redirect:/${class?lower_case}";
    }

    @ModelAttribute("preload${class}")
    public ${class} get${class}(<#if sqlTable.primaryKeys?exists><#rt>
<#list sqlTable.primaryKeys as key>
<#if key_index!=0>, </#if> @RequestParam(value = "${key.attrName?lower_case}", required = false) ${key.attrType} ${key.attrName?lower_case}<#rt>
</#list></#if>) {
        if (id != null) {
            return service.get${class}(<#if sqlTable.primaryKeys?exists><#rt>
<#list sqlTable.primaryKeys as key>
<#if key_index!=0>, </#if>${key.attrName?lower_case}<#rt>
</#list></#if>);
        }
        return null;
    }

}
