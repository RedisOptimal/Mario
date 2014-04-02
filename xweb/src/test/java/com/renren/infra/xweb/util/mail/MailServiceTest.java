package com.renren.infra.xweb.util.mail;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springside.modules.test.spring.SpringContextTestCase;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.GreenMailUtil;
import com.icegreen.greenmail.util.ServerSetupTest;

@DirtiesContext
@ContextConfiguration(locations = { "/applicationContext.xml", "/applicationContext-email.xml" })
@ActiveProfiles("test")
public class MailServiceTest extends SpringContextTestCase {

    /**
     * 邮件发送地址
     */
    private static final String MAIL_MESSAGE_FROM = "from@renren-inc.com";

    /**
     * 邮件接收地址
     */
    private static final String MAIL_MESSAGE_TO = "to@renren-inc.com";

    /**
     * 邮件标题
     */
    private static final String MAIL_MESSAGE_SUBJECT = "test subject";

    /**
     * 邮件内容
     */
    private static final String MAIL_MESSAGE_CONTEXT = "test context";

    /**
     * 附件文件名
     */
    private static final String EMAIL_ATTACHMENT = "/email/mailAttachment.txt";

    /**
     * 简单邮件发送服务类
     */
    @Autowired
    private SimpleMailService simpleMailService;

    /**
     * 附件邮件发送服务类
     */
    @Autowired
    private MimeMailService mimeMailService;

    /**
     * mock mail信息
     */
    private GreenMail greenMail;

    @Before
    public void setUp() {
        greenMail = new GreenMail(ServerSetupTest.SMTP);
        greenMail.setUser(MAIL_MESSAGE_TO, "toForTest", "forTest");
        greenMail.start();

    }

    @After
    public void tearDown() {
        try {
            greenMail.stop();
        } catch (NullPointerException ignored) {
            //empty
        }
    }

    /**
     * Message格式简单文本邮件发送
     * 
     * @throws MessagingException
     * @throws InterruptedException
     * @throws IOException
     */
    @Test
    public void testSendSimpleMail() throws MessagingException, InterruptedException, IOException {
        SimpleMailMessage message = buildSimpleMessage();
        simpleMailService.sendMail(message);

        greenMail.waitForIncomingEmail(2000, 1);

        Message[] msgs = greenMail.getReceivedMessages();
        assertEquals(1, msgs.length);
        assertEquals(MAIL_MESSAGE_FROM, msgs[0].getFrom()[0].toString());
        assertEquals(MAIL_MESSAGE_SUBJECT, msgs[0].getSubject());
        assertEquals(MAIL_MESSAGE_CONTEXT, GreenMailUtil.getBody(msgs[0]));
    }

    /**
     * MimeMessage格式邮件发送
     * 
     * @throws Exception
     */
    @Test
    public void testSendMail() throws Exception {
        SimpleMailMessage message = buildSimpleMessage();
        mimeMailService.sendMail(message);

        greenMail.waitForIncomingEmail(2000, 1);

        MimeMessage[] msgs = greenMail.getReceivedMessages();
        MimeMultipart mimeMultipart = (MimeMultipart) msgs[0].getContent();

        assertEquals(1, msgs.length);
        assertEquals(MAIL_MESSAGE_FROM, msgs[0].getFrom()[0].toString());
        assertEquals(MAIL_MESSAGE_SUBJECT, msgs[0].getSubject());
        assertTrue(GreenMailUtil.getBody(mimeMultipart.getBodyPart(0)).trim()
                .contains(MAIL_MESSAGE_CONTEXT));
    }

    /**
     * 邮件模板测试，不含附件
     * 
     * @throws MessagingException
     * @throws InterruptedException
     * @throws IOException
     */
    @Test
    public void testSendMailTemplate() throws MessagingException, InterruptedException, IOException {
        SimpleMailMessage message = buildSimpleMessage();
        String filename = "mailTemplate.ftl";
        Map<String, String> params = Collections.singletonMap("userName", MAIL_MESSAGE_TO);
        mimeMailService.sendMailTemplate(message, filename, params);

        greenMail.waitForIncomingEmail(2000, 1);

        MimeMessage[] msgs = greenMail.getReceivedMessages();
        MimeMultipart mimeMultipart = (MimeMultipart) msgs[0].getContent();

        assertEquals(1, msgs.length);
        assertEquals(MAIL_MESSAGE_FROM, msgs[0].getFrom()[0].toString());
        assertEquals(MAIL_MESSAGE_SUBJECT, msgs[0].getSubject());
        assertTrue(GreenMailUtil.getBody(mimeMultipart.getBodyPart(0)).trim()
                .contains(MAIL_MESSAGE_TO));
    }

    /**
     * 一个附件的邮件测试
     * 
     * @throws MessagingException
     * @throws InterruptedException
     * @throws IOException
     */
    @Test
    public void testSendMailFiles() throws MessagingException, InterruptedException, IOException {
        SimpleMailMessage message = buildSimpleMessage();
        String[] files = new String[] { EMAIL_ATTACHMENT };
        mimeMailService.sendMailFiles(message, files);

        greenMail.waitForIncomingEmail(2000, 1);

        MimeMessage[] msgs = greenMail.getReceivedMessages();
        MimeMultipart mimeMultipart = (MimeMultipart) msgs[0].getContent();

        assertEquals(1, msgs.length);
        assertEquals(MAIL_MESSAGE_FROM, msgs[0].getFrom()[0].toString());
        assertEquals(MAIL_MESSAGE_SUBJECT, msgs[0].getSubject());
        assertEquals(2, mimeMultipart.getCount());//multipart个数
        assertTrue(GreenMailUtil.getBody(mimeMultipart.getBodyPart(0)).trim()
                .contains(MAIL_MESSAGE_CONTEXT));
        assertEquals("Hello,i am a attachment.", GreenMailUtil
                .getBody(mimeMultipart.getBodyPart(1)).trim());
    }

    /**
     * 多附件邮件测试
     * 
     * @throws MessagingException
     * @throws InterruptedException
     * @throws IOException
     */
    @Test
    public void testSendMailMultiFiles() throws MessagingException, InterruptedException,
            IOException {
        SimpleMailMessage message = buildSimpleMessage();
        String[] files = new String[] { EMAIL_ATTACHMENT, EMAIL_ATTACHMENT };
        mimeMailService.sendMailFiles(message, files);

        greenMail.waitForIncomingEmail(2000, 1);

        MimeMessage[] msgs = greenMail.getReceivedMessages();
        MimeMultipart mimeMultipart = (MimeMultipart) msgs[0].getContent();

        assertEquals(1, msgs.length);
        assertEquals(MAIL_MESSAGE_FROM, msgs[0].getFrom()[0].toString());
        assertEquals(MAIL_MESSAGE_SUBJECT, msgs[0].getSubject());
        assertEquals(3, mimeMultipart.getCount());//multipart个数
        assertTrue(GreenMailUtil.getBody(mimeMultipart.getBodyPart(0)).trim()
                .contains(MAIL_MESSAGE_CONTEXT));
        assertEquals("Hello,i am a attachment.", GreenMailUtil
                .getBody(mimeMultipart.getBodyPart(1)).trim());
        assertEquals("Hello,i am a attachment.", GreenMailUtil
                .getBody(mimeMultipart.getBodyPart(2)).trim());
    }

    /**
     * 构造SimpleMessage
     * 
     * @return
     */
    private SimpleMailMessage buildSimpleMessage() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(MAIL_MESSAGE_FROM);
        message.setTo(MAIL_MESSAGE_TO);
        message.setSubject(MAIL_MESSAGE_SUBJECT);
        message.setText(MAIL_MESSAGE_CONTEXT);
        return message;
    }
}
