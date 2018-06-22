package com.qbaoio.qbaoio.controller;


import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("mail")
public class MailController {

    @Autowired
    private JavaMailSender javaMailSender;


    @Autowired
    private JavaMailSender mailSender; //自动注入的Bean
    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;  //自动注入


    @Value("${spring.mail.username}")
    private String  name;


    @RequestMapping("/auth/sendemail")
    public void sendEmail()
    {

            /* SimpleMailMessage message = new SimpleMailMessage();
           message.setFrom(name);
            message.setTo("feiyan.guo@aethercoder.com");
            message.setSubject("测试邮件主题");
            message.setText("测试邮件内容");
            javaMailSender.send(message);*/

        MimeMessage message = null;
        try {
            message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(name);
            helper.setTo("feiyan.guo@aethercoder.com");
            helper.setSubject("主题：模板邮件");

            Map<String, Object> model = new HashMap<>();
            model.put("username", "GUOfEIYAN");

            //修改 application.properties 文件中的读取路径
            //FreeMarkerConfigurer configurer = new FreeMarkerConfigurer();
           // configurer.setTemplateLoaderPath("classpath:/resources/templates");
            //读取 html 模板
            Template template = freeMarkerConfigurer.getConfiguration().getTemplate("mail.html");
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
            helper.setText(html, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mailSender.send(message);
    }

}
