package com.fri01.xiaozhi.common.lang;

import lombok.Data;

import java.io.Serializable;

@Data
public class Result implements Serializable {

    private int code;
    private String msg;
    private Object data;

    public static Result success(int code, String msg, Object data) {
        Result r = new Result();
        r.setCode(code);
        r.setMsg(msg);
        r.setData(data);
        return r;
    }

    public static Result fail(int code, String msg, Object data) {
        Result r = new Result();
        r.setCode(code);
        r.setMsg(msg);
        r.setData(data);
        return r;
    }

    public static Result success(Object data) {
        return success(StatusCode.SUCCESS.value(), "操作成功。", data);
    }

    public static Result success(String msg) {
        return success(StatusCode.SUCCESS.value(), msg, null);
    }

    public static Result success(String msg, Object data) {
        return success(StatusCode.SUCCESS.value(), msg, data);
    }

    public static Result fail(int code) {
        return fail(code, "操作失败。", null);
    }

    public static Result fail(String msg) {
        return fail(StatusCode.COMMON_EXCEPTION.value(), msg, null);
    }

    public static Result fail(int code, String msg) {
        return fail(code, msg, null);
    }
}
