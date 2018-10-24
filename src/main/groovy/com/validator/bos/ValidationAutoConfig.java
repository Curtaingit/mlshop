package com.validator.bos;

import com.validator.bos.core.MutationValidator;
import com.validator.bos.core.ValidatorStartedAspect;
import com.validator.bos.core.impl.MutationValidatorImpl;
import com.validator.bos.errors.MutationValidationHandler;
import com.validator.bos.events.RuleValidCheckEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.Validator;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ConditionalOnWebApplication
public class ValidationAutoConfig implements WebMvcConfigurer {

    private static final Logger LOGGER = LoggerFactory.getLogger(ValidationAutoConfig.class);

    private boolean isMutationValidator;

    @Bean
    @ConditionalOnProperty(prefix = ValidatorConditionProperties.PREFIX, name = "mutation-validator-enable",
            havingValue = "true", matchIfMissing = true)
    public ValidatorStartedAspect validateAspect() {
        return new ValidatorStartedAspect(mutationValidator());
    }

    @Bean
    @ConditionalOnProperty(prefix = ValidatorConditionProperties.PREFIX, name = "mutation-validator-enable",
            havingValue = "true", matchIfMissing = true)
    public MutationValidator mutationValidator() {
        isMutationValidator = true;
        LOGGER.info("Started MutationValidator");
        return new MutationValidatorImpl();
    }

    @Bean
    @ConditionalOnBean(ValidatorStartedAspect.class)
    public RuleValidCheckEvent ruleValidEvent() {
        return new RuleValidCheckEvent();
    }

    @Bean
    @ConditionalOnBean(ValidatorStartedAspect.class)
    public MutationValidationHandler controllerValidationHandler() {
        return new MutationValidationHandler();
    }

    @Override
    public Validator getValidator() {
        if (isMutationValidator) {
            return new MutationValidatorImpl();
        }
        return null;
    }
}
