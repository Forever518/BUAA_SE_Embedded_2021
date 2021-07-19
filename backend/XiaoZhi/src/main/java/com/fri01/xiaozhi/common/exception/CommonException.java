package com.fri01.xiaozhi.common.exception;

import com.fri01.xiaozhi.common.lang.StatusCode;

/**
 * <p>
 *     This Exception class is only used in this project.
 * </p>
 *
 * @author forever518
 * @since 2021-05-03
 */
public class CommonException extends Exception {
    private final int code;
    private final String msg;

    public CommonException(String s, int code, String msg) {
        super(s);
        this.code = code;
        this.msg = msg;
    }

    public CommonException(String s, String msg) {
        super(s);
        this.code = StatusCode.COMMON_EXCEPTION.value();
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
