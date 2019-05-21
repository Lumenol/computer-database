package com.excilys.cdb.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.excilys.cdb.converter.LocalDateToStringConverter;
import com.excilys.cdb.converter.StringToLocalDateConverter;
import com.excilys.cdb.converter.StringToOrderByFieldConverter;
import com.excilys.cdb.converter.StringToOrderByMeaningConverter;

@Configuration
@EnableWebMvc
@ComponentScan("com.excilys.cdb.controller.web")
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
	registry.addConverter(new StringToOrderByFieldConverter());
	registry.addConverter(new StringToOrderByMeaningConverter());
	registry.addConverter(new StringToLocalDateConverter());
	registry.addConverter(new LocalDateToStringConverter());
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
	registry.addResourceHandler("/static/**").addResourceLocations("/static/");
    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
	registry.jsp("/WEB-INF/views/", ".jsp");
    }

}
