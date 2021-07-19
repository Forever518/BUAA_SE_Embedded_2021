package com.fri01.xiaozhi.common.exception.map;

import com.fri01.xiaozhi.common.lang.StatusCode;

public class MapDeleteException extends MapException {
    public MapDeleteException(String s, String msg) {
        super(s, StatusCode.MAP_DELETE_FAIL.value(), msg);
    }
}
