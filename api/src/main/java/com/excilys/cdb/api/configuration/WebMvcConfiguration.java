package com.excilys.cdb.api.configuration;

import com.excilys.cdb.shared.converter.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import java.util.Locale;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.excilys.cdb.api.controller", excludeFilters = @ComponentScan.Filter(Configuration.class))
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToOrderByFieldConverter());
        registry.addConverter(new OrderByFieldToStringConverter());
        registry.addConverter(new StringToOrderByDirectionConverter());
        registry.addConverter(new OrderByDirectionToStringConverter());

        registry.addConverter(new StringToLocalDateConverter());
        registry.addConverter(new LocalDateToStringConverter());
    }

    @Bean
    public LocaleResolver localeResolver() {
        AcceptHeaderLocaleResolver localeResolver = new AcceptHeaderLocaleResolver();
        localeResolver.setDefaultLocale(Locale.FRENCH);
        return localeResolver;
    }

}
