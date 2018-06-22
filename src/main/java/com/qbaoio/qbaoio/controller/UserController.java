package com.qbaoio.qbaoio.controller;


import com.qbaoio.exception.AppException;
import com.qbaoio.exception.entity.ReqExceptionEntity;
import com.qbaoio.qbaoio.entity.user.User;
import com.qbaoio.qbaoio.service.user.UserService;
import com.qbaoio.util.UrlUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;


    /***
     * Qbao_io 用户注册
     * @param user
     * @return
     */
    @RequestMapping(value = "/auth/saveUser",method = RequestMethod.POST)
    public String saveUser(User user){
        user.setActivateType(0);

        logger.debug("saveUser ", user);

        String u = userService.saveUser(user);
        return u;

    }
    /***
     * Qbao_io 用户验证激活
     * @param id
     * @return
     */
    @RequestMapping(value = "/auth/activateUser/{id}",method = RequestMethod.POST)
    public String activateUser(@PathVariable("id") String id){

        try{
            System.out.println(System.getProperty("file.encoding"));
            System.out.println(Charset.defaultCharset().name());

            logger.debug("activateUser ", id);

             byte[] b  = UrlUtil.decodeBufferBase64(id);

            String t = new String(b);
            long l = Long.parseLong(t);
            String res = userService.activateUser(l);
            return  res;
        }catch (Exception e){

            throw  new AppException("id is not activate!");
        }

    }

    /***
     * Qbao_io 检验用户是否存在
     * @param userName
     * @return
     */
    @RequestMapping(value = "/auth/checkIsUser",method = RequestMethod.POST)
    public String checkIsUser(String userName){
        logger.debug("checkIsUser ", userName);
            String res = userService.findByUserName(userName);
            return res;
    }

    /***
     * Qbao_io 检验邮箱是否存在
     * @param email
     * @return
     */
    @RequestMapping(value = "/auth/checkIsEmail",method = RequestMethod.POST)
    public Map<String, String> checkIsEmail(String email){
        logger.debug("checkIsEmail ", email);
        String res = userService.findByEmail(email);
        return new HashMap<String, String>();
    }

    /***
     * Qbao_io 用户登录
     * 1。判断是邮箱or用户名登陆
     * 2。判断该用户是否存在
     * 3。判断该用户是否激活
     * @param nameOrEmail
     * @param password
     * @return
     */
    @RequestMapping(value = "/auth/login",method = RequestMethod.POST)
    public String login(String nameOrEmail,String password){
        logger.debug("login ", nameOrEmail+","+password);
        User user = new User();
        User userRest = new User();
        user.setPassWord(password);
        boolean status = nameOrEmail.contains("@");
        if(status){
            user.setEmail(nameOrEmail);
            userRest=userService.findEamilAndpassword(user);
        }else{
            user.setUserName(nameOrEmail);
            userRest=userService.findUserNameAndpassword(user);
        }
        if(userRest==null){
            throw  new AppException(ReqExceptionEntity.ErrorCode.USER_NOT_EXIST);
        }
        if(userRest.getActivateType()==0){
            throw  new AppException(ReqExceptionEntity.ErrorCode.USER_NOT_ACTIVATE);
        }
        return "";
    }





}
