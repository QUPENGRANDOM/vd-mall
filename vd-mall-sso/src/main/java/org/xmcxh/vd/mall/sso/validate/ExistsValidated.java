package org.xmcxh.vd.mall.sso.validate;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.xmcxh.vd.mall.sso.validate.annotation.Exists;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by pengq on 2020/6/5 10:42.
 */
public class ExistsValidated implements ConstraintValidator<Exists, String> {
    private BaseMapper baseMapper;
    private String column;
    @Autowired
    ApplicationContext applicationContext;

    @Override
    public void initialize(Exists constraintAnnotation) {
        Class<?> clazz = constraintAnnotation.repository();
        this.baseMapper = (BaseMapper) applicationContext.getBean(clazz);
        this.column = constraintAnnotation.column();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        QueryWrapper<Object> wrapper = Wrappers.query().eq(column, value);
        Integer count = baseMapper.selectCount(wrapper);
        return count > 0;
    }
}
