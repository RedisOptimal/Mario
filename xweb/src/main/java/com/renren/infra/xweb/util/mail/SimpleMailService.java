package com.renren.infra.xweb.util.mail;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

/**
 * 纯文本邮件服务类.
 * 
 * @author calvin
 * @create-time 2013-10-30
 * @revision 1.0.0
 * @E_mail yong.cao@renren-inc.com
 */
public class SimpleMailService {

    private static Logger logger = LoggerFactory.getLogger(SimpleMailService.class);

    private JavaMailSender mailSender;

    /**
     * 发送邮件
     * 
     * @param message
     */
    public void sendMail(SimpleMailMessage message) {
        try {
            mailSender.send(message);
            if (logger.isInfoEnabled()) {
                logger.info("纯文本邮件已发送至{}", StringUtils.join(message.getTo(), ","));
            }
        } catch (Exception e) {
            logger.error("发送邮件失败", e);
        }
    }

    /**
     * Spring的MailSender.
     */
    public void setMailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

}
