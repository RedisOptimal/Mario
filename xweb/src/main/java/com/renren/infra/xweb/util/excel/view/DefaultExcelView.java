package com.renren.infra.xweb.util.excel.view;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.springframework.web.servlet.view.document.AbstractExcelView;
import org.springside.modules.utils.Reflections;

import com.google.common.collect.Lists;
import com.renren.infra.xweb.util.excel.annotation.ExcelField;

/**
 * 默认的Excel展示View
 * 
 * @author yong.cao
 * @create-time 2013-10-24
 * @revision 1.0.0
 * @E_mail yong.cao@renren-inc.com
 */
public class DefaultExcelView extends AbstractExcelView {

    //excel 数据源
    private String attributename;

    //excel sheet信息
    private Sheet sheet;

    //excel 导出文件名
    private String filename;

    //行数
    private int rownum;

    //excel注解列表，生成表头
    private List<Object[]> annotationList = Lists.newArrayList();

    /**
     * 默认构造函数
     */
    public DefaultExcelView() {
    }

    /**
     * excel构造函数
     * 
     * @param filename 文件名
     * @param cls 注解类
     */
    public DefaultExcelView(String filename, String attributename, Class<?> cls) {
        this.filename = filename;
        this.attributename = attributename;

        //annotationList
        Field[] fields = cls.getDeclaredFields();
        for (Field field : fields) {
            ExcelField excelField = field.getAnnotation(ExcelField.class);
            if (excelField != null) {
                annotationList.add(new Object[] { excelField, field });
            }
        }

        // Get annotation method
        Method[] ms = cls.getDeclaredMethods();
        for (Method m : ms) {
            ExcelField ef = m.getAnnotation(ExcelField.class);
            if (ef != null) {
                annotationList.add(new Object[] { ef, m });
            }
        }
    }

    @Override
    protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        //设置中文名
        response.setContentType("APPLICATION/OCTET-STREAM");
        response.setHeader("Content-Disposition",
                "attachment; filename=" + URLEncoder.encode(filename, "UTF-8"));
        //excel header
        buildExcelHead(filename, workbook);

        //excel data
        List<?> testList = (List<?>) model.get(attributename);

        //write excel
        for (Object object : testList) {
            Row row = sheet.createRow(rownum++);
            int colunm = 0;
            for (Object[] os : annotationList) {
                ExcelField ef = (ExcelField) os[0];
                Object val = null;
                // Get entity value
                try {
                    if (StringUtils.isNotBlank(ef.value())) {
                        val = Reflections.invokeGetter(object, ef.value());
                    } else {
                        if (os[1] instanceof Field) {
                            val = Reflections.invokeGetter(object, ((Field) os[1]).getName());
                        } else if (os[1] instanceof Method) {
                            val = Reflections.invokeMethod(object, ((Method) os[1]).getName(),
                                    new Class[] {}, new Object[] {});
                        }
                    }
                } catch (Exception ex) {
                    val = "";
                }
                addCell(row, colunm++, val, ef.align(), ef.fieldType());
            }
        }
    }

    /**
     * 构建excel的表头
     * 
     * @param filename
     * @param headerList
     */
    private void buildExcelHead(String filename, HSSFWorkbook workbook) {
        // Initialize
        List<String> headerList = Lists.newArrayList();
        for (Object[] os : annotationList) {
            String t = ((ExcelField) os[0]).title();
            headerList.add(t);
        }

        sheet = workbook.createSheet("导出数据");

        // Create header
        Row headerRow = sheet.createRow(rownum++);
        headerRow.setHeightInPoints(16);
        for (int i = 0; i < headerList.size(); i++) {
            Cell cell = headerRow.createCell(i);
            String[] ss = StringUtils.split(headerList.get(i), "**", 2);
            if (ss.length == 2) {
                cell.setCellValue(ss[0]);
                Comment comment = sheet.createDrawingPatriarch().createCellComment(
                        new HSSFClientAnchor(0, 0, 0, 0, (short) 3, 3, (short) 5, 6));
                comment.setString(new XSSFRichTextString(ss[1]));
                cell.setCellComment(comment);
            } else {
                cell.setCellValue(headerList.get(i));
            }
            sheet.autoSizeColumn(i);
        }
        for (int i = 0; i < headerList.size(); i++) {
            int colWidth = sheet.getColumnWidth(i) * 2;
            sheet.setColumnWidth(i, colWidth < 3000 ? 3000 : colWidth);
        }
    }

    /**
     * 添加 cell
     * 
     * @param row 行号
     * @param column 列号
     * @param val 值
     * @param align 对齐格式
     * @param fieldType 单元格对象
     * @return
     */
    private Cell addCell(Row row, int column, Object val, int align, Class<?> fieldType) {
        Cell cell = row.createCell(column);
        try {
            if (val == null) {
                cell.setCellValue("");
            } else if (val instanceof String) {
                cell.setCellValue((String) val);
            } else if (val instanceof Integer) {
                cell.setCellValue((Integer) val);
            } else if (val instanceof Long) {
                cell.setCellValue((Long) val);
            } else if (val instanceof Double) {
                cell.setCellValue((Double) val);
            } else if (val instanceof Float) {
                cell.setCellValue((Float) val);
            } else if (val instanceof Date) {
                cell.setCellValue((Date) val);
            } else {
                if (fieldType != Class.class) {
                    cell.setCellValue((String) fieldType.getMethod("setValue", Object.class)
                            .invoke(null, val));
                } else {
                    cell.setCellValue((String) Class
                            .forName(
                                    this.getClass()
                                            .getName()
                                            .replaceAll(
                                                    this.getClass().getSimpleName(),
                                                    "fieldtype." + val.getClass().getSimpleName()
                                                            + "Type"))
                            .getMethod("setValue", Object.class).invoke(null, val));
                }
            }
        } catch (Exception ex) {
            cell.setCellValue(val.toString());
        }
        return cell;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public List<Object[]> getAnnotationList() {
        return annotationList;
    }

    public void setAnnotationList(List<Object[]> annotationList) {
        this.annotationList = annotationList;
    }

}
