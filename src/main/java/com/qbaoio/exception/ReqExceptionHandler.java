package com.qbaoio.exception;

import com.qbaoio.exception.entity.ReqExceptionEntity;
import com.qbaoio.util.LocaleMessageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.NoSuchMessageException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ReqExceptionHandler {

    @Autowired
    public LocaleMessageUtil localeMessageUtil;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 异常处理方法
     * 1.返回400
     */

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(AppException.class)
    @ResponseBody
    public ReqExceptionEntity handleException(AppException appException) {
        String message = null;
        try {
            message = localeMessageUtil.getLocalErrorMessage(appException.getErrorCode());
        } catch (NoSuchMessageException e) {
            message = appException.getMessage();
        }
        logger.warn("appException", appException.getErrorCode(), message, appException);
        return ReqExceptionEntity.bulid(appException.getErrorCode(), message);
    }

    /**
     * 异常处理方法
     * 2.返回500
     */

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)  // 500
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ReqExceptionEntity handleException(Exception ex) {
        logger.error("Exception", ex);
        return ReqExceptionEntity.bulid(ex.getMessage(), ex.getStackTrace());
    }


}
