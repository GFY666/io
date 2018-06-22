package com.qbaoio.util;

import com.qbaoio.exception.entity.ReqExceptionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class LocaleMessageUtil {


    @Autowired
    private MessageSource messageSource;

    public String getLocalErrorMessage(String errorCode) {
        Locale locale = LocaleContextHolder.getLocale();
        String errorMessage = messageSource.getMessage(errorCode, null, locale);
        return errorMessage;
    }


    public String getLocalErrorMessage(ReqExceptionEntity.ErrorCode errorCode) {
        return this.getLocalErrorMessage(errorCode.name());
    }

    }
