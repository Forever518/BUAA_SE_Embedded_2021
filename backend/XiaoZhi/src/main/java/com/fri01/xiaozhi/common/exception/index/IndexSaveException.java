package com.fri01.xiaozhi.common.exception.index;

import com.fri01.xiaozhi.common.lang.StatusCode;

public class IndexSaveException extends IndexException {
    public IndexSaveException(String s, String msg) {
        super(s, StatusCode.INDEX_SAVE_FAIL.value(), msg);
    }
}
