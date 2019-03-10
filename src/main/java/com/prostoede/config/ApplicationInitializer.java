package com.prostoede.config;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import org.apache.logging.log4j.web.Log4jServletContextListener;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;

/**
 *
 * @author prostoede
 */
public class ApplicationInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
	
	servletContext.setInitParameter("log4jConfigLocation", "classpath:log4j2.properties");
	
	AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
	rootContext.register(ApplicationConfig.class);
	
	servletContext.addListener(new ContextLoaderListener(rootContext));
	servletContext.addListener(new Log4jServletContextListener());
	
	AnnotationConfigWebApplicationContext servletAppContext = new AnnotationConfigWebApplicationContext();
	servletAppContext.register(WebConfig.class);
	servletAppContext.register(DataBaseConfig.class);
	
	DispatcherServlet dispatcherServlet = new DispatcherServlet(servletAppContext);
	dispatcherServlet.setThrowExceptionIfNoHandlerFound(true);

	ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher", dispatcherServlet);
	dispatcher.setLoadOnStartup(1);
	dispatcher.addMapping("/");

	FilterRegistration.Dynamic encodingFilter = servletContext.addFilter("encoding-filter", new CharacterEncodingFilter());
	encodingFilter.setInitParameter("encoding", "UTF-8");
	encodingFilter.setInitParameter("forceEncoding", "true");
	encodingFilter.addMappingForUrlPatterns(null, true, "/*");

    }

}
