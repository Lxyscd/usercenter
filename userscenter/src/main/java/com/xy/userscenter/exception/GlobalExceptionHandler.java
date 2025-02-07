package com.xy.userscenter.exception;

import com.xy.userscenter.common.BaseResponse;
import com.xy.userscenter.common.ErrorCode;
import com.xy.userscenter.common.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


    @ExceptionHandler(BussinessException.class)
    public BaseResponse bussinessExceptionHandler(BussinessException e) {
        //log.error("bussinessexception"+e.getMessage(), e);
        return ResultUtils.error(e.getCode(),e.getMessage(),e.getDescription());
    }

    @ExceptionHandler(RuntimeException.class)
    public BaseResponse runtimeExceptionHandler(RuntimeException e) {
        //log.error("runtimeexception", e);
        return ResultUtils.error(ErrorCode.SYSTEM_ERROR,e.getMessage(),"");
    }
}
