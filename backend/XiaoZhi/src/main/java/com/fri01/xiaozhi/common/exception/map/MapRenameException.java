package com.fri01.xiaozhi.common.exception.map;

import com.fri01.xiaozhi.common.lang.StatusCode;

public class MapRenameException extends MapException {
    public MapRenameException(String s, String msg) {
        super(s, StatusCode.MAP_RENAME_EXCEPTION.value(), msg);
    }
}
