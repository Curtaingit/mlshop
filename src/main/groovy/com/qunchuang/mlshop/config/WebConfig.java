package com.qunchuang.mlshop.config;


import com.validator.bos.core.impl.MutationValidatorImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.Validator;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public Validator getValidator() {
        return new MutationValidatorImpl();
    }
}
