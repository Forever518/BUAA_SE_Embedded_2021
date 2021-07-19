package com.fri01.xiaozhi.common.exception.index;

import com.fri01.xiaozhi.common.lang.StatusCode;

public class IndexFileManageException extends IndexException {
    public IndexFileManageException(String s, String msg) {
        super(s, StatusCode.INDEX_FILE_MANAGE_ERROR.value(), msg);
    }
}
