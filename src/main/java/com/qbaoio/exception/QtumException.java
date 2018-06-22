package com.qbaoio.exception;

/**
 * Created by hepengfei on 2017/8/23.
 */
public class QtumException extends RuntimeException {
    private Integer code;

    public QtumException(String message) {
        super(message);
    }

    public QtumException(Integer code) {
        super();
        setCode(code);
    }

    public QtumException(Integer code, String message) {
        super(message);
        setCode(code);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
