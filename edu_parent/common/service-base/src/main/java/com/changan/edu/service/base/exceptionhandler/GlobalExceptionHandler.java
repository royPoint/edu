package com.changan.edu.service.base.exceptionhandler;

import com.changan.edu.commonutils.RestResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler
{

    @ExceptionHandler(Exception.class) // 指定出现了什么异常执行该方法
    @ResponseBody // 为了返回数据
    public RestResult error(Exception e)
    {
        e.printStackTrace();
        return RestResult.error().message("执行了全局异常处理...");
    }

    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    public RestResult error(ArithmeticException e){
        e.printStackTrace();
        return RestResult.error().message("执行了 ArithmeticException 异常处理...");
    }

    // 自定义异常
    @ExceptionHandler(MyException.class)
    @ResponseBody
    public RestResult error(MyException e){
        e.printStackTrace();
        return RestResult.error().code(e.getCode()).message(e.getMsg());
    }
}