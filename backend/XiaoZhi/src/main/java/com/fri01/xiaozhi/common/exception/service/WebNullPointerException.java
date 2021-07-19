package com.fri01.xiaozhi.common.exception.service;

import com.fri01.xiaozhi.common.exception.CommonException;
import com.fri01.xiaozhi.common.lang.StatusCode;

public class WebNullPointerException extends CommonException {
    public WebNullPointerException(String s, String msg) {
        super(s, StatusCode.WEB_NULL_POINTER_EXCEPTION.value(), msg);
    }
}
