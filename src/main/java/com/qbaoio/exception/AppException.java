package com.qbaoio.exception;

import com.qbaoio.exception.entity.ReqExceptionEntity;

/**
 * Created by hepengfei on 2017/8/16.
 */
//@ResponseStatus(value = HttpStatus.NOT_FOUND,reason = "No such ...")
public class AppException extends RuntimeException {

    private ReqExceptionEntity.ErrorCode errorCode;

    public AppException(ReqExceptionEntity.ErrorCode errorCode) {
        super();
        setErrorCode(errorCode);
    }

    public AppException(String message) {
        super(message);
    }

    public AppException(String message, ReqExceptionEntity.ErrorCode errorCode) {
        super(message);
        setErrorCode(errorCode);
    }

    public ReqExceptionEntity.ErrorCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ReqExceptionEntity.ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
