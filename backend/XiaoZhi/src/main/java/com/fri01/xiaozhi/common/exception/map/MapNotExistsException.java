package com.fri01.xiaozhi.common.exception.map;

import com.fri01.xiaozhi.common.lang.StatusCode;

public class MapNotExistsException extends MapException {
    public MapNotExistsException(String s, String msg) {
        super(s, StatusCode.MAP_NOT_EXISTS.value(), msg);
    }
}
