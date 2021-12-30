package com.dorby.seckill.vo;

import com.dorby.seckill.utils.ValidatorUtil;
import com.dorby.seckill.validator.IsMobile;
import org.thymeleaf.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @Author: dorby
 * @Description: 手机号码校验规则
 * @Date: 2021/12/16 16:01
 */
public class isMobileValidator implements ConstraintValidator<IsMobile,String> {
    private boolean required=false;

    @Override
    public void initialize(IsMobile constraintAnnotation) {
        required=constraintAnnotation.required();
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if(required){
            return ValidatorUtil.isMobile(s);
        }else{
            if(StringUtils.isEmpty(s)){
                return false;
            }else{
                return ValidatorUtil.isMobile(s);
            }
        }
    }
}
