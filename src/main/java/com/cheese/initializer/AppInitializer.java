package com.cheese.initializer;

import com.cheese.database.config.DatabaseConfig;
import com.cheese.web.config.WebMvcConfig;
import com.cheese.web.servlet.CustomDispatcherServlet;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.FrameworkServlet;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.*;

public class AppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{WebMvcConfig.class, DatabaseConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    @Override
    protected FrameworkServlet createDispatcherServlet(WebApplicationContext servletAppContext) {
        return new CustomDispatcherServlet(servletAppContext, true);
    }

    @Override
    protected void customizeRegistration(ServletRegistration.Dynamic registration) {
        registration.setMultipartConfig(new MultipartConfigElement("/tmp"));
    }

    @Override
    protected Filter[] getServletFilters() {
        String errorPageFilterName = WebMvcConfig.ERROR_PAGE_FILTER_NAME;
        return new Filter[]{
                new DelegatingFilterProxy(errorPageFilterName)
        };
    }

}
