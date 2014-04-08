package com.renren.infra.xweb.util.generator;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import com.renren.infra.xweb.util.generator.config.ApplicationProperties;
import com.renren.infra.xweb.util.generator.db.SqlTable;
import com.renren.infra.xweb.util.generator.template.TemplateFactory;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class TemplateFactoryTest {

	private String schema = "root";
	private String tablename = "xweb_test";

	@Test
	public void testOutputFile() throws URISyntaxException {
		Configuration cfg = new Configuration();
		Writer file = null;
		try {
			URI filepath = TemplateFactoryTest.class.getClassLoader()
					.getResource("").toURI();
			cfg.setDirectoryForTemplateLoading(new File(filepath));
			Template template = cfg.getTemplate("templates/helloworld.ftl");
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("message", "Hello World!");
			List<String> countries = new ArrayList<String>();
			countries.add("India");
			countries.add("United States");
			countries.add("Germany");
			countries.add("France");
			data.put("countries", countries);
			Writer out = new OutputStreamWriter(System.out);
			template.process(data, out);
			out.flush();

			file = new FileWriter(new File("FTL_helloworld.txt"));
			template.process(data, file);
			file.flush();
			file.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		}
		assertTrue(file != null);
	}

	@Test
	public void testTemplateFacotry() throws IOException, TemplateException {
		TemplateFactory factory = TemplateFactory.getInstance();
		String path = "templates/helloworld.ftl";

		Map<String, Object> data = new HashMap<String, Object>();
		data.put("message", "Hello World!");
		List<String> countries = new ArrayList<String>();
		countries.add("India");
		countries.add("United States");
		countries.add("Germany");
		countries.add("France");
		data.put("countries", countries);

		String content = factory.process(path, data, "UTF-8");
		assertTrue(StringUtils.isNotBlank(content));
	}

	@Test
	public void testTemplateFactoryForEntity() throws IOException,
			TemplateException {
		TemplateFactory factory = TemplateFactory.getInstance();
		String path = "templates/Entity.java.ftl";

		SqlTable table = new SqlTable(schema, tablename);
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("sqlTable", table);
		Properties properties = ApplicationProperties.getProperties();
		data.put("prop", properties);

		String content = factory.process(path, data, "UTF-8");
		assertTrue(StringUtils.isNotBlank(content));
	}

	@Test
	public void testTemplateFactoryForMybatis() throws IOException,
			TemplateException {
		TemplateFactory factory = TemplateFactory.getInstance();
		String path = "templates/MybatisDao.java.ftl";

		SqlTable table = new SqlTable(schema, tablename);
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("sqlTable", table);
		Properties properties = ApplicationProperties.getProperties();
		data.put("prop", properties);

		String content = factory.process(path, data, "UTF-8");
		assertTrue(StringUtils.isNotBlank(content));

	}

	@Test
	public void testTemplateFactoryForMapperXML() throws IOException,
			TemplateException {
		TemplateFactory factory = TemplateFactory.getInstance();
		String path = "templates/Mapper.xml.ftl";

		SqlTable table = new SqlTable(schema, tablename);
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("sqlTable", table);
		Properties properties = ApplicationProperties.getProperties();
		data.put("prop", properties);

		String content = factory.process(path, data, "UTF-8");
		assertTrue(StringUtils.isNotBlank(content));

	}

	@Test
	public void testTemplateFactoryForService() throws IOException,
			TemplateException {
		TemplateFactory factory = TemplateFactory.getInstance();
		String path = "templates/Service.java.ftl";

		SqlTable table = new SqlTable(schema, tablename);
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("sqlTable", table);
		Properties properties = ApplicationProperties.getProperties();
		data.put("prop", properties);

		String content = factory.process(path, data, "UTF-8");
		assertTrue(StringUtils.isNotBlank(content));

	}

	@Test
	public void testTemplateFactoryForController() throws IOException,
			TemplateException {
		TemplateFactory factory = TemplateFactory.getInstance();
		String path = "templates/Controller.java.ftl";

		SqlTable table = new SqlTable(schema, tablename);
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("sqlTable", table);
		Properties properties = ApplicationProperties.getProperties();
		data.put("prop", properties);

		String content = factory.process(path, data, "UTF-8");
		assertTrue(StringUtils.isNotBlank(content));
	}

	@Test
	public void testTemplateFactoryForFormJSP() throws IOException,
			TemplateException {
		TemplateFactory factory = TemplateFactory.getInstance();
		String path = "templates/Form.jsp.ftl";

		SqlTable table = new SqlTable(schema, tablename);
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("sqlTable", table);
		Properties properties = ApplicationProperties.getProperties();
		data.put("prop", properties);

		String content = factory.process(path, data, "UTF-8");
		assertTrue(StringUtils.isNotBlank(content));
	}

	@Test
	public void testTemplateFactoryForListJSP() throws IOException,
			TemplateException {
		TemplateFactory factory = TemplateFactory.getInstance();
		String path = "templates/List.jsp.ftl";

		SqlTable table = new SqlTable(schema, tablename);
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("sqlTable", table);
		Properties properties = ApplicationProperties.getProperties();
		data.put("prop", properties);

		String content = factory.process(path, data, "UTF-8");
		assertTrue(StringUtils.isNotBlank(content));
	}
}
