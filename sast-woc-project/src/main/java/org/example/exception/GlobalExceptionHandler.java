package org.example.exception;

import lombok.extern.slf4j.Slf4j;
import org.example.pojo.Result;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler
    public Result handleBusinessException(BusinessException e) {
        log.error("(全局异常处理器)业务异常: code={}, message={}", e.getErrCode(), e.getMessage());
        return Result.error(e.getErrCode(), e.getMessage());
    }
    @ExceptionHandler
    public Result handleException(Exception e) {
        log.error("(全局异常处理器)系统异常: {}", e.getMessage());
        return Result.error(5000, "系统异常");
    }
    @ExceptionHandler
    public Result handleDuplicateKeyException(DuplicateKeyException e) {
        log.error("(全局异常处理器)运行时异常: {}", e.getMessage());
        String message = e.getMessage();
        int i=message.indexOf("Duplicate entry");
        String errMsg=message.substring(i);
        String[] arr=errMsg.split(" ");
        return Result.error(3001, arr[2]+"已存在");
    }

}
