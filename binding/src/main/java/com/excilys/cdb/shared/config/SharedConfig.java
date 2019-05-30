package com.excilys.cdb.shared.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.*;
import org.springframework.context.support.ResourceBundleMessageSource;

@Configuration
@Import({SharedConfig.BeanConfig.class, SharedConfig.ValidatorConfig.class, SharedConfig.MapperConfig.class, SharedConfig.LogExceptionConfig.class})
public class SharedConfig {

    @Configuration
    public static class BeanConfig {
        @Bean
        public MessageSource messageSource() {
            final ResourceBundleMessageSource bundleMessage = new ResourceBundleMessageSource();
            bundleMessage.addBasenames("i18n/messages");
            return bundleMessage;
        }
    }

    @Configuration
    @ComponentScan("com.excilys.cdb.shared.validator")
    public static class ValidatorConfig {
    }

    @Configuration
    @ComponentScan("com.excilys.cdb.shared.mapper")
    public static class MapperConfig {
    }

    @Configuration
    @ComponentScan("com.excilys.cdb.shared.logexception")
    @EnableAspectJAutoProxy
    public static class LogExceptionConfig {
    }

}
