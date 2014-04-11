package com.renren.infra.xweb.util.generator.template;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URISyntaxException;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * Freemarker模板工厂
 * 
 * @author yong.cao
 * 
 */
public class TemplateFactory {

	private static TemplateFactory instance = new TemplateFactory();

	private Configuration conf;

	private File file;

	private boolean init;

	private TemplateFactory() {
		conf = new Configuration();
		try {
			file = new File(TemplateFactory.class.getClassLoader()
					.getResource("").toURI());
			conf.setDirectoryForTemplateLoading(file);
			init = true;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}

	public static TemplateFactory getInstance() {
		return instance;
	}

	public String process(String path, Object context) throws IOException,
			TemplateException {
		return this.process(path, context, null);
	}

	public String process(String path, Object context, String encoding)
			throws IOException, TemplateException {
		if (!init) {
			conf.setDirectoryForTemplateLoading(file);
			init = true;
		}

		Template template = conf.getTemplate(path);
		if (StringUtils.isBlank(encoding)) {
			template.setEncoding(encoding);
		}
		StringWriter sw = new StringWriter();
		template.process(context, sw);
		return sw.toString();
	}

	/**
	 * 输出信息到路径
	 * @param templatepath
	 * @param context
	 * @param encoding
	 * @param outputpath
	 * @throws IOException
	 * @throws TemplateException
	 */
	public void process(String templatepath, Object context, String encoding,
			String outputpath) throws IOException, TemplateException {
		if (!init) {
			conf.setDirectoryForTemplateLoading(file);
			init = true;
		}

		Template template = conf.getTemplate(templatepath);
		if (StringUtils.isBlank(encoding)) {
			template.setEncoding(encoding);
		}
		// File output
		Writer file = new FileWriter(new File(outputpath));
		template.process(context, file);
		file.flush();
		file.close();
	}

	public StringWriter getStringWriter(String ftl, Map<String, Object> map)
			throws IOException, TemplateException {
		Template template = new Template(System.currentTimeMillis() + "",
				new StringReader(ftl), conf);
		StringWriter sw = new StringWriter();
		template.process(map, sw);
		return sw;
	}

}
