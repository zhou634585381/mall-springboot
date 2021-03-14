package com.example.mall.exception;

import com.example.mall.utils.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * @author ZY
 */
@ControllerAdvice
@ResponseBody
public class MallExceptionHandler {

    @Autowired
    private ResultMessage resultMessage;

    @ExceptionHandler(MallException.class)
    public ResultMessage handleException(MallException e) {
        ExceptionEnum em = e.getExceptionEnum();
        resultMessage.fail(em.getCode() + "", em.getMsg());
        return resultMessage;
    }
}
