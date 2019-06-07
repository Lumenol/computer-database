package com.excilys.cdb.webapp.configuration;

import java.util.List;
import java.util.Locale;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.theme.ThemeChangeInterceptor;

import com.excilys.cdb.webapp.converter.LocalDateToStringConverter;
import com.excilys.cdb.webapp.converter.OrderByDirectionToStringConverter;
import com.excilys.cdb.webapp.converter.OrderByFieldToStringConverter;
import com.excilys.cdb.webapp.converter.StringToLocalDateConverter;
import com.excilys.cdb.webapp.converter.StringToOrderByDirectionConverter;
import com.excilys.cdb.webapp.converter.StringToOrderByFieldConverter;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.excilys.cdb.webapp.controller", excludeFilters = @ComponentScan.Filter(Configuration.class))
public class WebMvcConfiguration implements WebMvcConfigurer {
	
	@Override
	public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {
		resolvers.add(new ErrorHandler());
	}



	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addConverter(new StringToOrderByFieldConverter());
		registry.addConverter(new OrderByFieldToStringConverter());
		registry.addConverter(new StringToOrderByDirectionConverter());
		registry.addConverter(new OrderByDirectionToStringConverter());

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

	@Bean
	public LocaleResolver localeResolver() {
		CookieLocaleResolver localeResolver = new CookieLocaleResolver();
		localeResolver.setDefaultLocale(Locale.FRENCH);
		return localeResolver;
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addRedirectViewController("/", "/dashboard");
		registry.addViewController("/login").setViewName("login");
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		ThemeChangeInterceptor themeChangeInterceptor = new ThemeChangeInterceptor();
		themeChangeInterceptor.setParamName("theme");
		registry.addInterceptor(themeChangeInterceptor);

		LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
		localeChangeInterceptor.setParamName("lang");
		registry.addInterceptor(localeChangeInterceptor);
	}
}
