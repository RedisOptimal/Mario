package com.renren.infra.xweb.util.mail;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * MIME邮件服务类.
 * 
 * @author calvin
 * @create-time 2013-10-30
 * @revision 1.0.0
 * @E_mail yong.cao@renren-inc.com
 */
public class MimeMailService {

    private static final String DEFAULT_ENCODING = "utf-8";

    private static Logger logger = LoggerFactory.getLogger(MimeMailService.class);

    private JavaMailSender mailSender;

    private Configuration freemarkerConfiguration;

    /**
     * 发送普通邮件
     * 
     * @param message
     */
    public void sendMail(SimpleMailMessage message) {
        try {
            MimeMessageHelper helper = buildMimeMessage(message);
            mailSender.send(helper.getMimeMessage());
            logger.info("HTML版邮件已发送至" + message.getTo());
        } catch (MessagingException e) {
            logger.error("构造邮件失败", e);
        } catch (Exception e) {
            logger.error("发送邮件失败", e);
        }
    }

    /**
     * 发送带有Freemarker模板和附件的邮件
     * 
     * @param message
     * @param templatefile
     * @param params
     * @param files
     */
    public void sendMail(SimpleMailMessage message, String templatefile,
            Map<String, String> params, String[] files) {
        try {
            String text = buildTemplateText(templatefile, params);
            message.setText(text);

            MimeMessageHelper helper = buildMimeMessage(message);
            buildAttachments(files, helper);
            mailSender.send(helper.getMimeMessage());

            logger.info("HTML版邮件已发送至" + message.getTo());
        } catch (MessagingException e) {
            logger.error("构造邮件失败", e);
        } catch (Exception e) {
            logger.error("发送邮件失败", e);
        }
    }

    /**
     * 发送带有Freemarker模板的邮件
     * 
     * @param message
     * @param freemarkerConfiguration
     * @param params
     * @throws MessagingException
     */
    public void sendMailTemplate(SimpleMailMessage message, String filename,
            Map<String, String> params) {
        try {
            String text = buildTemplateText(filename, params);
            message.setText(text);
            this.sendMail(message);
        } catch (MessagingException e) {
            logger.error("构造邮件失败", e);
        } catch (Exception e) {
            logger.error("发送邮件失败", e);
        }
    }

    /**
     * 发送带附件邮件
     * 
     * @param message
     * @param files
     * @throws MessagingException
     */
    public void sendMailFiles(SimpleMailMessage message, String[] files) {
        try {
            MimeMessageHelper helper = buildMimeMessage(message);
            buildAttachments(files, helper);
            mailSender.send(helper.getMimeMessage());
        } catch (MessagingException e) {
            logger.error("构造邮件失败", e);
        } catch (Exception e) {
            logger.error("发送邮件失败", e);
        }
    }

    /**
     * 将SimpleMailMessage构建为MimeMessage
     * 
     * @param message
     * @param msg
     * @return
     * @throws MessagingException
     */
    private MimeMessageHelper buildMimeMessage(SimpleMailMessage message) throws MessagingException {
        MimeMessage msg = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg, true, DEFAULT_ENCODING);

        //邮件信息
        helper.setFrom(message.getFrom());
        helper.setTo(message.getTo());
        helper.setSubject(message.getSubject());
        helper.setText(message.getText(), true);

        return helper;
    }

    /**
     * 构建附件
     * 
     * @param files
     * @param helper
     * @throws IOException
     * @throws MessagingException
     * @throws UnsupportedEncodingException
     */
    private void buildAttachments(String[] files, MimeMessageHelper helper)
            throws MessagingException {
        //构建附件
        try {
            for (String filename : files) {
                Resource resource = new ClassPathResource(filename);
                File file = resource.getFile();
                helper.addAttachment(MimeUtility.encodeWord(file.getName()), file);//解决附件名称乱码
            }
        } catch (UnsupportedEncodingException e) {
            logger.error("构造邮件失败,附件文件编码错误", e);
            throw new MessagingException("附件文件编码", e);
        } catch (IOException e) {
            logger.error("构造邮件失败,附件文件不存在", e);
            throw new MessagingException("附件文件不存在", e);
        }
    }

    /**
     * 构建Freemarker模板信息
     * 
     * @param templatefile
     * @param params
     * @return
     * @throws IOException
     * @throws TemplateException
     */
    private String buildTemplateText(String templatefile, Map<String, String> params)
            throws MessagingException {
        try {
            if (StringUtils.isBlank(templatefile)) {
                throw new IOException("FreeMarker模板不存在");
            }
            Template template = freemarkerConfiguration.getTemplate(templatefile, DEFAULT_ENCODING);
            String text = FreeMarkerTemplateUtils.processTemplateIntoString(template, params);
            return text;
        } catch (IOException e) {
            logger.error("生成邮件内容失败, FreeMarker模板不存在", e);
            throw new MessagingException("FreeMarker模板不存在", e);
        } catch (TemplateException e) {
            logger.error("生成邮件内容失败, FreeMarker处理失败", e);
            throw new MessagingException("FreeMarker处理失败", e);
        }
    }

    /**
     * Spring的MailSender.
     * 
     * @param mailSender
     */
    public void setMailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    /**
     * 注入Freemarker引擎配置,构造Freemarker邮件内容模板.
     * 
     * @param freemarkerConfiguration
     */
    public void setFreemarkerConfiguration(Configuration freemarkerConfiguration)
            throws IOException {
        this.freemarkerConfiguration = freemarkerConfiguration;
    }

}
