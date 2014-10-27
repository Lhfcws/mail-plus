package server.test.email;


import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;
import org.apache.commons.mail.SimpleEmail;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author lhfcws
 * @time 14-10-21.
 */
public class FakeApacheMailSender {


    public static void sendSingle() throws EmailException {
        SimpleEmail email = new SimpleEmail();

        email.setHostName("smtp.163.com");
        email.setAuthentication("lhfcws", "lhfcws82283086");//邮件服务器验证：用户名/密码
        email.setCharset("UTF-8");// 必须放在前面，否则乱码
        email.addTo("lhfcws@163.com");
        email.setFrom("lhfcws@163.com", "EmailTester");
        email.setSubject("subject中文");
        email.setMsg("msg中文");


        email.send();
    }

    public static void sendGroup() {

    }

    public static void sendAttachment() throws EmailException, MalformedURLException {
        MultiPartEmail email = new MultiPartEmail();


        EmailAttachment attachment = new EmailAttachment();
//        attachment.setURL(new URL(""));// 本地文件
        // attachment.setURL(new URL("http://xxx/a.gif"));//远程文件
        attachment.setDisposition(EmailAttachment.ATTACHMENT);
        attachment.setDescription("test attachment description");
        attachment.setName("test attachment name");

//        email.attach(attachment);
        email.send();
    }

    public static void main(String[] args) throws Exception {
        if (args.length == 0)
            sendSingle();
        else if (args[0].equals("group"))
            sendGroup();
        else if (args[0].equals("attach"))
            sendAttachment();
    }
}
