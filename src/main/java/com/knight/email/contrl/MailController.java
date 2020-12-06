package com.knight.email.contrl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@RestController
public class MailController {

    @Autowired
    private JavaMailSender javaMailSender;

    @GetMapping("/knight/{to}/{subject}/{text}")
    public Boolean sendMail(@PathVariable String to, @PathVariable String subject, @PathVariable String text) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
//        simpleMailMessage.setFrom("258574726@qq.com");
        simpleMailMessage.setFrom("zhangertong@jiejiegao12139.cn");
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(text);
        javaMailSender.send(simpleMailMessage);

        return Boolean.TRUE;
    }

    @GetMapping("/knight/{to}/{subject}/{text}")
    public Boolean sendMail2(@PathVariable String to, @PathVariable String subject,
                             @PathVariable String text, @RequestParam("file") MultipartFile file) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

        mimeMessageHelper.setFrom("zhangertong@jiejiegao12139.cn");
        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(text);
        mimeMessageHelper.addAttachment("zet.pdf", file);
        javaMailSender.send(mimeMessage);

        return Boolean.TRUE;
    }

}
