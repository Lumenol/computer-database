package com.excilys.cdb.web.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import com.excilys.cdb.persistence.config.PersistenceConfig;
import com.excilys.cdb.service.config.ServiceConfig;
import com.excilys.cdb.shared.config.SharedConfig;

public class WebAppInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext container) {
	// Create the 'root' Spring application context
	AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
	rootContext.register(WebConfig.class, SharedConfig.class, ServiceConfig.class, PersistenceConfig.class);

	// Manage the lifecycle of the root application context
	container.addListener(new ContextLoaderListener(rootContext));
	// Create the dispatcher servlet's Spring application context
	AnnotationConfigWebApplicationContext dispatcherContext = new AnnotationConfigWebApplicationContext();
	dispatcherContext.register(WebMvcConfiguration.class);

	// Register and map the dispatcher servlet
	ServletRegistration.Dynamic dispatcher = container.addServlet("dispatcher",
		new DispatcherServlet(dispatcherContext));
	dispatcher.setLoadOnStartup(1);
	dispatcher.addMapping("/");
    }

}