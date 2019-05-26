package com.excilys.cdb.shared.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

@Configuration
@ComponentScan(basePackages = {"com.excilys.cdb.shared.mapper", "com.excilys.cdb.shared.validator"}, excludeFilters = @ComponentScan.Filter(Configuration.class))
public class SharedConfig {
    @Bean
    public MessageSource messageSource() {
        final ResourceBundleMessageSource bundleMessage = new ResourceBundleMessageSource();
        bundleMessage.addBasenames("i18n/messages");
        return bundleMessage;
    }

}
