package com.excilys.cdb.web.configuration;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import com.excilys.cdb.persistence.configuration.PersistenceConfiguration;
import com.excilys.cdb.service.configuration.ServiceConfiguration;
import com.excilys.cdb.shared.configuration.SharedConfiguration;

public class WebApplicationInitializer implements org.springframework.web.WebApplicationInitializer {

	@Override
	public void onStartup(ServletContext container) {
		// Create the 'root' Spring application context
		AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
		rootContext.register(WebConfiguration.class, SharedConfiguration.class, ServiceConfiguration.class,
				PersistenceConfiguration.class, WebSecurityConfiguration.class);

		// Manage the lifecycle of the root application context
		container.addListener(new ContextLoaderListener(rootContext));

		// Register and map the mvc dispatcher servlet
		AnnotationConfigWebApplicationContext mvcDispatcherContext = new AnnotationConfigWebApplicationContext();
		mvcDispatcherContext.register(WebMvcConfiguration.class);

		final DispatcherServlet mvcServlet = new DispatcherServlet(mvcDispatcherContext);
		mvcServlet.setThrowExceptionIfNoHandlerFound(true);
		ServletRegistration.Dynamic mvcDispatcher = container.addServlet("mvc-dispatcher", mvcServlet);
		mvcDispatcher.setLoadOnStartup(1);
		mvcDispatcher.addMapping("/");

		// Register and map the rest dispatcher servlet
		AnnotationConfigWebApplicationContext restDispatcherContext = new AnnotationConfigWebApplicationContext();
		restDispatcherContext.register(RestConfiguration.class);

		ServletRegistration.Dynamic restDispatcher = container.addServlet("rest-dispatcher",
				new DispatcherServlet(restDispatcherContext));
		restDispatcher.setLoadOnStartup(1);
		restDispatcher.addMapping("/api/");

	}

}