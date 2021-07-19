package com.fri01.xiaozhi.common.exception.index;

import com.fri01.xiaozhi.common.lang.StatusCode;

public class IndexNotExistsException extends IndexException {
    public IndexNotExistsException(String s, String msg) {
        super(s, StatusCode.INDEX_NOT_EXISTS.value(), msg);
    }
}
