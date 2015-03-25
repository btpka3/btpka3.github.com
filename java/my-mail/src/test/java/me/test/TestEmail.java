package me.test;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.mail.internet.MimeUtility;

import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.MultiPartEmail;
import org.apache.commons.mail.SimpleEmail;
import org.junit.Test;

// QQ 企业邮箱
// http://service.exmail.qq.com/cgi-bin/help?subtype=1&&id=28&&no=1000564

public class TestEmail {

    private String emailServer = "smtp.exmail.qq.com";
    private String userName = "noreply@xxx.com";
    private String password = "******";
    private String emailEncoding = "UTF-8";
    private String receiver = "test@xxx.com";

    /**
     * 测试发送不包含附件的简单纯文本邮件
     */
    @Test
    public void testSimpleEmail() throws EmailException {
        SimpleEmail email = new SimpleEmail();
        email.setHostName(emailServer);
        email.setSmtpPort(465);
        email.setSSLOnConnect(true);
        email.setAuthentication(userName, password);
        email.setCharset(emailEncoding);

        email.addTo(receiver, "btpka3 中文");
        email.setFrom(userName, "zll 中文");
        email.setSubject("Subjec SimpleEmail 中文");
        email.setMsg("Message SimpleEmail 中文");

        email.send();
    }

    /**
     * 测试发送包含附件的邮件
     */
    @Test
    public void testMultiPartEmail()
            throws UnsupportedEncodingException, EmailException, MalformedURLException {

        EmailAttachment att2 = new EmailAttachment();
        att2.setURL(new URL("http://www.apache.org/images/asf_logo_wide.gif"));
        att2.setDisposition(EmailAttachment.ATTACHMENT);
        att2.setDescription("attachemnt description logo 中文");
        att2.setName(MimeUtility.encodeText("logo 中文.gif"));

        MultiPartEmail email = new MultiPartEmail();
        email.setHostName(emailServer);
        email.setSmtpPort(465);
        email.setSSLOnConnect(true);
        email.setAuthentication(userName, password);
        email.setCharset(emailEncoding);

        email.addTo(receiver, "btpka3 中文");
        email.setFrom(userName, "zll 中文");
        email.setSubject("Subjec MultiPartEmail 中文");
        email.setMsg("Message MultiPartEmail 中文");

        email.attach(att2);

        email.send();
    }

    /**
     * 发送HTML格式的邮件，经测试发现：用到的图片在QQ的邮箱界面中没有附件的图标
     */
    @Test
    public void testHtmlEmail()
            throws UnsupportedEncodingException, EmailException, MalformedURLException {
        HtmlEmail email = new HtmlEmail();
        email.setHostName(emailServer);
        email.setSmtpPort(465);
        email.setSSLOnConnect(true);
        email.setAuthentication(userName, password);
        email.setCharset(emailEncoding);

        email.addTo(receiver, "btpka3 中文");
        email.setFrom(userName, "zll 中文");
        email.setSubject("Subjec HtmlEmail 中文");

        URL url = new URL("http://www.apache.org/images/asf_logo_wide.gif");
        String cid2 = email.embed(url, "logo 中文.gif");

        email.setHtmlMsg("<html>"
                + "<b>The apache logo</b> - "
                + "<img src=\"cid:" + cid2 + "\"><br></html>");
        email.setTextMsg("Your email client does not support HTML messages");

        email.send();
    }
}
