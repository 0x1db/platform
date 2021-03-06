package com.wangyu.web.config;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.hibernate.validator.HibernateValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * hibernate-validate 校验配置
 *
 * @Author wangyu
 * @Date 2019-11-22 17:25
 */
@Configuration
public class ValidatorConfig {

  /**
   * hibernate的校验模式 1、普通模式（默认是这个模式） 　普通模式(会校验完所有的属性，然后返回所有的验证失败信息) 2、快速失败返回模式
   * 快速失败返回模式(只要有一个验证失败，则返回)
   * <p>
   * 设置快速返回模式
   *
   * @return
   */
  @Bean
  public Validator validator() {
    ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class)
        .configure()
        .addProperty("hibernate.validator.fail_fast", "true")
        .buildValidatorFactory();
    Validator validator = validatorFactory.getValidator();

    return validator;
  }
}
