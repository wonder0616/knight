package com.knight.email.until;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.springframework.stereotype.Service;

/**
 *
 * @author yangwj created by id 88424579 on 20190110
 *
 *	send a email for new account inform the some message
 */

public class EmailAdditionalTool{

    public static String userName="zhangertong@jiejiegao12139.cn";
    public static String passWord="huawei@123";
    public static String FromUser="zhangertong@jiejiegao12139.cn";
    public static String ToUser="258574724@qq.com";
    public static String attachmentUrl="";//附件URL地址
    public static String picpath="";//图片地址
    public static String htmlpath="";//HTML地址

    @SuppressWarnings("static-access")
    public void EmailSender(String htmlStr) throws MessagingException, Exception{
        //邮箱基本信息
        MailSenderInfo mailInfo = new MailSenderInfo();
        mailInfo.setMailServerHost("smtp.saas.huaweicloud.com");
        mailInfo.setMailServerPort("465");
        mailInfo.setValidate(true);
        mailInfo.setUserName(userName);
        mailInfo.setPassword(passWord);
        mailInfo.setFromUser(FromUser);
        mailInfo.setToUser(ToUser);
        mailInfo.setSubject("张二通测试邮件");//邮件主题

        //邮箱内容以及附件
        Multipart multipart=new MimeMultipart();
        //  multipart.addBodyPart(createContent(""));//HTML格式邮件内容
        multipart.addBodyPart(createAttachment(attachmentUrl));//文件附件
        multipart.addBodyPart(createAttachment(picpath));//图片附件
        //邮件HTML内容格式
        //    String htmlString=readHTML(htmlpath);
        multipart.addBodyPart(createContent(htmlStr));//HTML格式邮件内容
        // 这个类主要来发送邮件
        SimpleMailSender sms = new SimpleMailSender();
        //调用接口发送邮件
        sms.sendTextMail(mailInfo,multipart);
    }


    /**
     * 创建HTML格式的邮件内容
     * @param body 邮件内容
     * @return 邮件内容实体
     * @throws Exception
     */
    public static MimeBodyPart createContent(String body) throws Exception{

        MimeBodyPart htmlBodyPart=new MimeBodyPart();

        htmlBodyPart.setContent(body, "text/html;charset=UTF-8");

        return htmlBodyPart;
    }


    /**
     * 创建邮件中的附件
     * @param filepath 附件的路径
     * @return 生成附件的对象
     * @throws Exception
     */
    public static MimeBodyPart createAttachment(String filepath) throws Exception{
        /*创建一个表示附件的MimeBodyPart对象，并加入附件内容以及相应的信息*/
        MimeBodyPart attachPart=new MimeBodyPart();

        //FileDataSource用于读取文件数据，并返回代表数据的输入输出和数据的MIME类型
        FileDataSource fileDataSource=new FileDataSource(filepath);

        //DataHandler类用于封装FileDataSource对象，并为应用程序提供访问数据的接口
        attachPart.setDataHandler(new DataHandler(fileDataSource));

        //设置附件名称，MimeUtility.encodeText可以处理乱码问题
        attachPart.setFileName(MimeUtility.encodeText(fileDataSource.getName()));

        return attachPart;
    }


    /**
     * 传入页面url地址返回整个页面的转成流再变成流输出
     *
     * @param htmlpth html页面url
     * @return 返回String字符串
     * @throws IOException
     */
    public String readHTML(String htmlpth) throws IOException {

        InputStreamReader isReader = null;
        BufferedReader bufReader = null;
        StringBuffer buf = new StringBuffer();
        try {
            File file = new File(htmlpth);
            isReader = new InputStreamReader(new FileInputStream(file), "utf-8");
            bufReader = new BufferedReader(isReader, 1);
            String data;
            while((data = bufReader.readLine())!= null) {
                buf.append(data);
            }

        } catch (Exception e) {
            //TODO 处理异常
        } finally {
            //TODO 关闭流
            isReader.close();

            bufReader.close();

        }
        return buf.toString();
    }

}