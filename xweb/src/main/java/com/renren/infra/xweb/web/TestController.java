package com.renren.infra.xweb.web;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.validation.Valid;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springside.modules.mapper.JsonMapper;
import org.springside.modules.web.Servlets;

import com.renren.infra.xweb.entity.Test;
import com.renren.infra.xweb.service.TestService;
import com.renren.infra.xweb.util.Const;
import com.renren.infra.xweb.util.excel.view.DefaultExcelView;

@Controller
@RequestMapping("/test")
public class TestController {

    @Autowired
    private TestService service;

    @RequestMapping(value = { "", "/list" })
    public String list(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
            Model model, ServletRequest request) {

        Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
        Page<Test> tests = service.getTest(searchParams, pageNumber, Const.PAGE_SIZE);

        model.addAttribute("tests", tests);
        model.addAttribute("searchParams",
                Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));

        return "test/testList";
    }

    @RequestMapping("testChart")
    public String testChart() {
        return "test/testChart";
    }

    @RequestMapping(value = "create", method = RequestMethod.GET)
    public String createForm(Model model) {
        model.addAttribute("test", new Test());
        model.addAttribute("action", "create");
        return "test/testForm";
    }

    @RequestMapping(value = "create", method = RequestMethod.POST)
    public String create(@Valid Test newTest, BindingResult result, Model model,
            RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("action", "create");
            return "test/testForm";
        }
        service.saveTest(newTest);
        redirectAttributes.addFlashAttribute("message", "创建任务成功");
        return "redirect:/test/";
    }

    @RequestMapping(value = "update/{id}", method = RequestMethod.GET)
    public String updateForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("test", service.getTest(id));
        model.addAttribute("action", "update");
        return "test/testForm";
    }

    @RequestMapping(value = "update", method = RequestMethod.POST)
    public String update(@Valid @ModelAttribute("test") Test test, BindingResult result,
            Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("action", "update");
            return "test/testForm";
        }
        service.saveTest(test);
        redirectAttributes.addFlashAttribute("message", "更新任务成功");
        return "redirect:/test/";
    }

    @RequestMapping(value = "delete/{id}")
    public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        service.deleteTest(id);
        redirectAttributes.addFlashAttribute("message", "删除任务成功");
        return "redirect:/test";
    }

    @RequestMapping(value = "upload")
    @ResponseBody
    public String upload(@RequestParam("files") MultipartFile[] files,
            RedirectAttributes redirectAttributes) throws Exception {
        JsonMapper binder = JsonMapper.nonDefaultMapper();
        Map<String, Object> maps = new HashMap<String, Object>();

        List<String> fileNames = new ArrayList<String>();
        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                importToDataBase(file.getInputStream());
            } else {
                System.out.println("fail");
                maps.put("result", false);
            }
        }

        maps.put("files", fileNames);
        maps.put("result", true);

        return binder.toJson(maps);
    }

    @RequestMapping(value = "export")
    public ModelAndView export(Model model, RedirectAttributes redirectAttributes) {
        List<Test> testList = service.getAllTest();
        model.addAttribute("testList", testList);

        return new ModelAndView(new DefaultExcelView("导出Test信息", "testList", Test.class));
    }

    /**
     * 预加载test到request的attribute中
     * 
     * @param id
     * @param model
     */
    @ModelAttribute
    public void getTest(@RequestParam(value = "id", required = false) Long id, Model model) {
        if (id != null) {
            Test test = service.getTest(id);
            model.addAttribute("test", test);
        }
    }

    //TODO:import excel
    private void importToDataBase(InputStream stream) throws InvalidFormatException, IOException {
        try {
            //        Workbook workbook = new HSSFWorkbook(stream);
            Workbook workbook = WorkbookFactory.create(stream);
            Sheet sheet = workbook.getSheetAt(0);//取第一个sheet
            List<Test> testList = new ArrayList<Test>();

            int rowStart = Math.max(1, sheet.getFirstRowNum());//从第二行开始查找
            int rowEnd = Math.min(Integer.MAX_VALUE, sheet.getLastRowNum());
            for (int rownum = rowStart; rownum < rowEnd; rownum++) {
                Row row = sheet.getRow(rownum);

                int lastColumn = Math.max(row.getLastCellNum(), 1);

                Test test = new Test();
                for (int columnnum = 0; columnnum < lastColumn; columnnum++) {
                    Cell c = row.getCell(columnnum, Row.RETURN_BLANK_AS_NULL);
                    if (c == null) {//记录为空
                        continue;
                    } else {//batch
                        test.setMsg(setCellValue(c).toString());
                        testList.add(test);
                    }
                }
            }

            service.saveBatchTest(testList);
        } finally {
            try {
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private Object setCellValue(Cell cell) {
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_STRING:
                return cell.getRichStringCellValue().getString();
            case Cell.CELL_TYPE_NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue();
                } else {
                    return cell.getNumericCellValue();
                }
            case Cell.CELL_TYPE_BOOLEAN:
                return cell.getBooleanCellValue();
            case Cell.CELL_TYPE_FORMULA:
                return cell.getCellFormula();
            default:
                System.out.println();
                return "";
        }
    }

}
