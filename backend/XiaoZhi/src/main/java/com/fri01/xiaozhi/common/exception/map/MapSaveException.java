package com.fri01.xiaozhi.common.exception.map;

import com.fri01.xiaozhi.common.lang.StatusCode;

public class MapSaveException extends MapException {
    public MapSaveException(String s, String msg) {
        super(s, StatusCode.MAP_SAVE_FAIL.value(), msg);
    }
}
