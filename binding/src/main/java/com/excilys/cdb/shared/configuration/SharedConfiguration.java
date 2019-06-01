package com.excilys.cdb.shared.configuration;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.*;
import org.springframework.context.support.ResourceBundleMessageSource;

@Configuration
@Import({SharedConfiguration.BeanConfig.class, SharedConfiguration.ValidatorConfiguration.class, SharedConfiguration.MapperConfiguration.class, SharedConfiguration.LogExceptionConfiguration.class})
public class SharedConfiguration {

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
    public static class ValidatorConfiguration {
    }

    @Configuration
    @ComponentScan("com.excilys.cdb.shared.mapper")
    public static class MapperConfiguration {
    }

    @Configuration
    @ComponentScan("com.excilys.cdb.shared.logexception")
    @EnableAspectJAutoProxy
    public static class LogExceptionConfiguration {
    }

}