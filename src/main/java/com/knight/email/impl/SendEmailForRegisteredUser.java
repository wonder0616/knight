package com.knight.email.impl;

import com.knight.email.until.EmailAdditionalTool;
//import com.suning.secis.util.StringUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.io.*;

/**
 *
 * @author yangwj created by  ID 88424579
 * this class act on sending Email after administrator help user register suning Email
 */
@Component
public class SendEmailForRegisteredUser {

    public  String htmlUrl= "attachment/email.html";
    public  String attachmentUrl="attachment/风险监控服务操作手册 V2.0.pdf";
    public  static String name="";
    public  static String toUserEmail="";

    public EmailAdditionalTool emailadditionaltool=new EmailAdditionalTool();
    public  void renderDataInHtml(){

        try {
            File inputStream = new ClassPathResource(htmlUrl).getFile();
            File attUrl = new ClassPathResource(attachmentUrl).getFile();
            Document doc=Jsoup.parse(inputStream,"UTF-8","http://www.oschina.net/");
            Elements human_names=doc.getElementsByClass("human-name");
            for (Element element : human_names) {
                System.out.println(human_names);
                if(null!=element && !"".equals(element)){
                    element.text("风险管理中心-"+name);
                    System.out.println(element);
                }
            }

//            if(false==toUserEmail.isEmpty() && false==StringUtil.isBlack(attachmentUrl)){
            if(false==toUserEmail.isEmpty() ){

                emailadditionaltool.ToUser=toUserEmail;
                emailadditionaltool.attachmentUrl=attUrl.getPath();
            }

            try {
                System.out.println(doc.html());
                emailadditionaltool.EmailSender(doc.html().toString());
            } catch (MessagingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}