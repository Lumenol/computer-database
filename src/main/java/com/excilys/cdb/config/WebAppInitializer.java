package com.excilys.cdb.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class WebAppInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext container) {
	// Create the 'root' Spring application context
	AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
	rootContext.register(AppConfig.class);

	// Manage the lifecycle of the root application context
	container.addListener(new ContextLoaderListener(rootContext));
	// Create the dispatcher servlet's Spring application context
	AnnotationConfigWebApplicationContext dispatcherContext = new AnnotationConfigWebApplicationContext();
	dispatcherContext.register(WebMvcConfiguration.class);

	// Register and map the dispatcher servlet
	final DispatcherServlet servletDispatcher = new DispatcherServlet(dispatcherContext);
	servletDispatcher.setThrowExceptionIfNoHandlerFound(true);
	ServletRegistration.Dynamic dispatcher = container.addServlet("dispatcher", servletDispatcher);
	dispatcher.setLoadOnStartup(1);
	dispatcher.addMapping("/");
    }

}