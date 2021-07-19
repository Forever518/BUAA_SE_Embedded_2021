package com.fri01.xiaozhi.common.lang;

public enum StatusCode {
    SUCCESS(200),
    COMMON_EXCEPTION(400),
    MAP_SAVE_FAIL(601),
    MAP_DELETE_FAIL(602),
    MAP_NAME_DUPLICATE(603),
    MAP_NOT_EXISTS(604),
    INDEX_SAVE_FAIL(605),
    INDEX_DELETE_FAIL(606),
    INDEX_CHANGE_FAIL(607),
    INDEX_NAME_DUPLICATED(608),
    INDEX_NOT_EXISTS(609),
    SCRIPT_COMMON_EXCEPTION(610),
    WEB_NULL_POINTER_EXCEPTION(611),
    LINUX_COMMAND_PROCESS_EXCEPTION(612),
    MAP_RENAME_EXCEPTION(613),
    MAP_FILE_MANAGE_ERROR(630),
    INDEX_FILE_MANAGE_ERROR(640),
    ROBOT_SERVICE_COMMON_EXCEPTION(700);

    private int code;

    StatusCode(int code) {
        this.code = code;
    }

    public int value() {
        return this.code;
    }
}
