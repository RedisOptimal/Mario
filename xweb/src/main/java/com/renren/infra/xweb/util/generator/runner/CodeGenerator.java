package com.renren.infra.xweb.util.generator.runner;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.renren.infra.xweb.util.generator.config.ApplicationProperties;
import com.renren.infra.xweb.util.generator.db.SqlTable;
import com.renren.infra.xweb.util.generator.template.TemplateFactory;

import freemarker.template.TemplateException;

/**
 * 代码生成入口
 * 
 * @author yong.cao
 * 
 */
public class CodeGenerator {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(CodeGenerator.class);

	/**
	 * 生成代码主程序入口
	 * @param args
	 */
	public static void main(String[] args) {
		String schema = ApplicationProperties.getJdbcSchema();
		String tablename = ApplicationProperties.getTablename();

		LOGGER.info("main function generator code: table is " + tablename
				+ ", schema is " + schema);
		generateFilesFromOneTable(schema, tablename);

	}

	/**
	 * 根据数据库表信息生成文件
	 * 
	 * @param schema
	 * @param tablename
	 */
	private static void generateFilesFromOneTable(String schema,
			String tablename) {
		SqlTable sqlTable = getSqlTable(schema, tablename);
		generateFilesFromSqlTable(sqlTable);
	}

	/**
	 * 从SqlTable生成文件
	 * 
	 * @param sqlTable
	 */
	private static void generateFilesFromSqlTable(SqlTable sqlTable) {
		LOGGER.info("--------> Start Generation for Table:  "
				+ sqlTable.getTablename() + " <-----------");

		TemplateFactory template = TemplateFactory.getInstance();

		Properties properties = ApplicationProperties.getProperties();
		Map<String, Object> context = new HashMap<String, Object>();
		context.put("sqlTable", sqlTable);
		context.put("prop", properties);

		try {
			Properties templates = ApplicationProperties.extractProperties(
					ApplicationProperties.getProperties(), "template");

			Enumeration<?> enum1 = templates.keys();
			while (enum1.hasMoreElements()) {
				String aKey = (String) enum1.nextElement();
				String aValue = (String) templates.get(aKey);
				LOGGER.info("--------> Start Generation " + aValue
						+ " File <-----------");
				template.process(
						aValue,
						context,
						"UTF-8",
						ApplicationProperties.getFileOutputPath()
								+ outputFileType(aValue,
										sqlTable.getEntityname()));
				LOGGER.info("--------> End Generation " + aValue
						+ " File <-----------");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		}
		LOGGER.info("--------> End Generation for Table:  "
				+ sqlTable.getTablename() + " <-----------");
	}

	/**
	 * 填充SqlTable
	 * 
	 * @param schema
	 * @param tablename
	 * @return
	 */
	private static SqlTable getSqlTable(String schema, String tablename) {
		SqlTable table = new SqlTable(schema, tablename);
		return table;
	}

	/**
	 * 输出文件类型
	 * @param filename
	 * @param entityname
	 * @return
	 */
	private static String outputFileType(String filename, String entityname) {
		String suffix = ".ftl";
		if (StringUtils.contains(filename, suffix)) {
			// enetity.java.ftl
			if (filename.contains("Entity")) {
				return StringUtils.capitalize(entityname) + ".java";
			}
			// *.jsp
			if (filename.contains(".jsp")) {
				return entityname
						+ StringUtils.substring(filename,
								filename.indexOf("/") + 1,
								filename.indexOf(suffix));
			}
			// other ftl
			return StringUtils.capitalize(entityname)
					+ StringUtils
							.substring(filename, filename.indexOf("/") + 1,
									filename.indexOf(suffix));
		}
		return StringUtils.EMPTY;
	}

}
