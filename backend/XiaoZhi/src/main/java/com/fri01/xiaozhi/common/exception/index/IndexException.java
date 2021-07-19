package com.fri01.xiaozhi.common.exception.index;

import com.fri01.xiaozhi.common.exception.CommonException;

public class IndexException extends CommonException {
    IndexException(String s, int code, String msg) {
        super(s, code, msg);
    }
}
