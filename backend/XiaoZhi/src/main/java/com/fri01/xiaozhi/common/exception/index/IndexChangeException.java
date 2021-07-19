package com.fri01.xiaozhi.common.exception.index;

import com.fri01.xiaozhi.common.lang.StatusCode;

public class IndexChangeException extends IndexException {
    public IndexChangeException(String s, String msg) {
        super(s, StatusCode.INDEX_CHANGE_FAIL.value(), msg);
    }
}
