package com.dorby.seckill.exception;

import com.dorby.seckill.vo.RespBean;
import com.dorby.seckill.vo.RespBeanEnum;
import lombok.val;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.BindException;

/**
 * @Author: dorby
 * @Description: 全局处理异常
 * @Date: 2021/12/16 16:15
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public RespBean ExceptionHandler(Exception e){
        if(e instanceof GlobalException){
            GlobalException ex=(GlobalException) e;
            return RespBean.error(ex.getRespBeanEnum());
        }else if(e instanceof BindException){
            BindException ex=(BindException) e;
            RespBean respBean = RespBean.error(RespBeanEnum.ERROR);
            respBean.setMessage("参数校验异常:"+ex.getMessage());
            return respBean;
        }
        return RespBean.error(RespBeanEnum.BIND_ERROR);
    }
}
