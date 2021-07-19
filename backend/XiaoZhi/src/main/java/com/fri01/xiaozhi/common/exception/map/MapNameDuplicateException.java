package com.fri01.xiaozhi.common.exception.map;

import com.fri01.xiaozhi.common.lang.StatusCode;

public class MapNameDuplicateException extends MapException {
    public MapNameDuplicateException(String s, String msg) {
        super(s, StatusCode.MAP_NAME_DUPLICATE.value(), msg);
    }
}
