package com.excilys.cdb.shared.configuration;

import com.excilys.cdb.shared.paging.PagingParameters;
import com.excilys.cdb.shared.paging.SortingParameters;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.*;
import org.springframework.context.support.ResourceBundleMessageSource;

@Configuration
@Import({SharedConfiguration.BeanConfig.class, SharedConfiguration.ValidatorConfiguration.class,
        SharedConfiguration.MapperConfiguration.class, SharedConfiguration.LogExceptionConfiguration.class})
public class SharedConfiguration {

    @Configuration
    public static class BeanConfig {
        @Bean
        public MessageSource messageSource() {
            final ResourceBundleMessageSource bundleMessage = new ResourceBundleMessageSource();
            bundleMessage.addBasenames("i18n/messages");
            bundleMessage.setUseCodeAsDefaultMessage(true);
            return bundleMessage;
        }

        @Bean
        public PagingParameters paginationParameters() {
            final String PARAMETER_NEXT = "next";
            final String PARAMETER_PAGE = "page";
            final String PARAMETER_PAGES = "pages";
            final String PARAMETER_PREVIOUS = "previous";
            final String PARAMETER_SIZE = "size";

            return new PagingParameters(PARAMETER_SIZE, PARAMETER_PAGE, PARAMETER_PREVIOUS, PARAMETER_NEXT,
                    PARAMETER_PAGES);
        }

        @Bean
        public SortingParameters sortingParameters() {
            final String PARAMETER_ORDER_BY = "order-by";
            final String PARAMETER_DIRECTION = "direction";
            final String PARAMETER_ORDER_BY_UTILS = "order-utils";

            return new SortingParameters(PARAMETER_ORDER_BY, PARAMETER_DIRECTION, PARAMETER_ORDER_BY_UTILS);
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
