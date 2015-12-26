package com.exam.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * Custom Web Application Initializer to remove web.xml from the picture
 * 
 * @author shubhankar_roy
 *
 */
public class ExamWebApplicationInitializer implements WebApplicationInitializer {

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
		context.register(AppContextConfiguration.class);
		servletContext.addListener(new WebPropertiesConfigurationListener());
		servletContext.addListener(new ContextLoaderListener(context));
		
		DomainFactory.getInstance().setContext(context);
		
		//AnnotationConfigWebApplicationContext dispatcherContext = new AnnotationConfigWebApplicationContext();
		//context.setConfigLocation("com.exam.config.WebAppContextConfiguration");
		ServletRegistration.Dynamic registration = servletContext.addServlet("dispatcher", new DispatcherServlet(context));
        registration.setLoadOnStartup(1);
        registration.addMapping("/");
	}

}
