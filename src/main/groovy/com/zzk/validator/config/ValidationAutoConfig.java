package com.zzk.validator.config;


import com.zzk.validator.core.MutationValidator;
import com.zzk.validator.core.ValidatorAspect;
import com.zzk.validator.core.impl.MutationValidatorImpl;
import com.zzk.validator.errors.MutationValidationHandler;
import com.zzk.validator.events.RuleValidCheckEvent;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnWebApplication
public class ValidationAutoConfig {

    @Bean
    public ValidatorAspect validateAspect() {
        return new ValidatorAspect(mutationValidator());
    }

    @Bean
    public MutationValidator mutationValidator() {
        return new MutationValidatorImpl();
    }

    @Bean
    public RuleValidCheckEvent ruleValidEvent() {
        return new RuleValidCheckEvent();
    }

    @Bean
    public MutationValidationHandler controllerValidationHandler() {
        return new MutationValidationHandler();
    }

}
