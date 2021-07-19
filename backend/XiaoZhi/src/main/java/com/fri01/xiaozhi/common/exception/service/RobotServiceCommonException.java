package com.fri01.xiaozhi.common.exception.service;

import com.fri01.xiaozhi.common.exception.CommonException;
import com.fri01.xiaozhi.common.lang.StatusCode;

public class RobotServiceCommonException extends CommonException {
    public RobotServiceCommonException(String s, String msg) {
        super(s, StatusCode.ROBOT_SERVICE_COMMON_EXCEPTION.value(), msg);
    }
}
