package com.fri01.xiaozhi.common.exception.map;

import com.fri01.xiaozhi.common.exception.CommonException;

public class MapException extends CommonException {
    MapException(String s, int code, String msg) {
        super(s, code, msg);
    }
}
