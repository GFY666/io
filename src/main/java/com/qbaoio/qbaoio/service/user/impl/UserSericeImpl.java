package com.qbaoio.qbaoio.service.user.impl;

import com.qbaoio.exception.AppException;
import com.qbaoio.exception.entity.ReqExceptionEntity;
import com.qbaoio.qbaoio.dao.user.UserDao;
import com.qbaoio.qbaoio.entity.user.User;
import com.qbaoio.qbaoio.service.user.UserService;
import com.qbaoio.util.UrlUtil;
import freemarker.template.Template;
import io.jsonwebtoken.lang.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import sun.misc.BASE64Encoder;

import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public  class UserSericeImpl  implements UserService{


    @Autowired
    private UserDao userDao;
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;

    @Value("${spring.mail.username}")
    private String  name;


    @Override
    public String saveUser(User user) {
        Assert.notNull(user.getEmail());
        Assert.notNull(user.getUserName());

        List<User> emailList = userDao.findByEmail(user.getEmail());
        if (emailList != null && !emailList.isEmpty()) {
            throw new AppException(ReqExceptionEntity.ErrorCode.USER_EMAIL_CHECK);
        }
        User u = userDao.save(user);
         if (!"".equals(u)) {
              sendEmail(user);
              return "";
             } else {
               throw new AppException(ReqExceptionEntity.ErrorCode.USER_REGISTRATION);
              }
        }


    @Override
    public String activateUser(long id) {
        try{
                User u =new User();
                u.setId(id);
                u  = userDao.findOne(u.getId());
                u.setActivateType(1);
                User userresult = userDao.save(u);
                if (!"".equals(userresult)) {
                    return "";
                } else {
                    throw new AppException(ReqExceptionEntity.ErrorCode.USER_ACTICVATE);
                }
       }catch (Exception e){
            throw  e;
        }
    }

    @Override
    public String findByUserName(String name) {
        List<User> userNanme = userDao.findByUserName(name);
        if(userNanme.size()==0){
            return "";
        }
        throw new AppException(ReqExceptionEntity.ErrorCode.USER_NAME_CHECK);
    }

    @Override
    public String findByEmail(String email) {
        List<User> emailStr = userDao.findByEmail(email);
        if(emailStr.size()==0){
            return "";
        }
        throw new AppException(ReqExceptionEntity.ErrorCode.USER_EMAIL_CHECK);
    }


    @Override


    public User findEamilAndpassword(User user) {
        Assert.notNull(user);
        User  user1 = userDao.findByEmailAndPassWord(user.getUserName(),user.getPassWord());
        return user1;
    }


    @Override
    public User findUserNameAndpassword(User user) {
        Assert.notNull(user);
       User  user1 = userDao.findByUserNameAndPassWord(user.getUserName(),user.getPassWord());
        return user1;

    }

    public  void sendEmail(User user)
    {

        MimeMessage message = null;
        try {
            message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(name);
            helper.setTo(user.getEmail());
            helper.setSubject("码以科技");
            String text=user.getId()+"";
            BASE64Encoder encoder = new BASE64Encoder();
            byte[] textByte = text.getBytes("UTF-8");
            String encodedText = UrlUtil.encodeBufferBase64(textByte);
            Map<String, Object> model = new HashMap<>();
            model.put("username", user.getUserName());
            model.put("url", "http://localhost:8080/user/auth/activateUser/"+encodedText);
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
