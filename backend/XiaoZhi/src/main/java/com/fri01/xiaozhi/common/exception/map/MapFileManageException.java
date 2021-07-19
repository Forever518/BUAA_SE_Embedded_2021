package com.fri01.xiaozhi.common.exception.map;

import com.fri01.xiaozhi.common.lang.StatusCode;

public class MapFileManageException extends MapException {
    public MapFileManageException(String s, String msg) {
        super(s, StatusCode.MAP_FILE_MANAGE_ERROR.value(), msg);
    }
}
