package com.fri01.xiaozhi.common.exception.ros;

import com.fri01.xiaozhi.common.exception.CommonException;
import com.fri01.xiaozhi.common.lang.StatusCode;

public class CommandProcessExecutedException extends CommonException {
    public CommandProcessExecutedException(String s, String msg) {
        super(s, StatusCode.LINUX_COMMAND_PROCESS_EXCEPTION.value(), msg);
    }
}
