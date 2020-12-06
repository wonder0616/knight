package com.knight.email.until;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleMailSender {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleMailSender.class);

    /**
     * 获取邮箱基本信息并进行发送(账号开通邮件通知)
     *
     * @param mailInfo  邮箱基本信息
     * @param multipart HTML+附件信息
     * @return
     */
    public static boolean sendTextMail(MailSenderInfo mailInfo, Multipart multipart) {
        // 判断是否需要身份认证
//        MonitorAuthenticator authenticator = null;
        Authenticator authenticator = null;
        Properties pro = mailInfo.getProperties();
        if (mailInfo.isValidate()) {
            // 如果需要身份认证，则创建一个密码验证器
//            authenticator = new MonitorAuthenticator(mailInfo.getUserName(), mailInfo.getPassword());

        }
        // 根据邮件会话属性和密码验证器构造一个发送邮件的session
        Session sendMailSession = Session.getDefaultInstance(pro, authenticator);
        //sendMailSession.setDebug(true); //打印javamail内部日志
        try {
            // 根据session创建一个邮件消息
            Message mailMessage = new MimeMessage(sendMailSession);
            // 创建邮件发送者地址
            String nick = javax.mail.internet.MimeUtility.encodeText("发送人名称");
            Address from = new InternetAddress(nick + " <" + mailInfo.getFromUser() + ">");
            // 设置邮件消息的发送者
            mailMessage.setFrom(from);
            // 创建邮件的接收者地址，并设置到邮件消息中
            if (null != mailInfo.getToUser() && !"".equals(mailInfo.getToUser())) {
                String[] to = mailInfo.getToUser().split(",");
                Address[] tos = new InternetAddress[to.length];
                for (int i = 0; i < to.length; i++) {
                    tos[i] = new InternetAddress(to[i]);// 收件人
                }
                mailMessage.setRecipients(Message.RecipientType.TO, tos);
            }
            if (null != mailInfo.getCopyUser() && !"".equals(mailInfo.getCopyUser())) {
                String[] copy = mailInfo.getCopyUser().split(",");
                Address[] copys = new InternetAddress[copy.length];
                for (int i = 0; i < copy.length; i++) {
                    copys[i] = new InternetAddress(copy[i]);
                }
                mailMessage.setRecipients(Message.RecipientType.CC, copys);
            }
            if (null != mailInfo.getbCopyUser() && !"".equals(mailInfo.getbCopyUser())) {
                String[] bcopy = mailInfo.getbCopyUser().split(",");
                Address[] bcopys = new InternetAddress[bcopy.length];
                for (int i = 0; i < bcopy.length; i++) {
                    bcopys[i] = new InternetAddress(bcopy[i]);
                }
                mailMessage.setRecipients(Message.RecipientType.BCC, bcopys);
            }
            /*Address to = new InternetAddress(mailInfo.getToUser());
            mailMessage.setRecipient(Message.RecipientType.TO, to);*/
            // 设置邮件消息的主题
            mailMessage.setSubject(mailInfo.getSubject());
            // 设置邮件消息发送的时间
            mailMessage.setSentDate(new Date());
            // 设置邮件消息的主要内容
            // 这里的内容主要是一些简单的字符串文字之类的，设置好放进content便可以了。这里先注释掉，大家发什么类型的邮件自己选就好了
            //mailMessage.setText(mailInfo.getContent());

            //      mailMessage.setContent(mailInfo.getContent(), "text/html;charset=UTF-8");
            //      multipart主要是放入带有附件、HTML、图片等的信息
            mailMessage.setContent(multipart, "text/html;charset=UTF-8");
            // 发送邮件
            Transport.send(mailMessage);
            return true;
        } catch (MessagingException ex) {
            LOGGER.error("exception1 in #sendTextMail," + ex.getMessage(), ex);
            //ex.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("exception2 in #sendTextMail," + e.getMessage(), e);
            //e.printStackTrace();
        } catch (Exception ep) {
            LOGGER.error("exception3 in #sendTextMail," + ep.getMessage());
            //e.printStackTrace();
        }
        return false;
    }

}