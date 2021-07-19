package com.fri01.xiaozhi.common.exception;

import com.fri01.xiaozhi.common.lang.Result;
import com.fri01.xiaozhi.common.ros.RobotConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * <p>
 *     Global exception handler, catching all exceptions in runtime.
 * </p>
 *
 * @author forever518
 * @since 2021-05-03
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.ACCEPTED)
    @ExceptionHandler(value = CommonException.class)
    public static Result commonExceptionHandler(CommonException e) {
        log.error(e.toString());
        return Result.fail(e.getCode(), e.getMsg());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = RuntimeException.class)
    public static Result runtimeExceptionHandler(RuntimeException e) {
        log.error(e.toString());
        return Result.fail("运行时异常。");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = Exception.class)
    public static Result otherExceptionHandler(Exception e) {
        log.error(e.toString());
        return Result.fail("全局异常。");
    }
}