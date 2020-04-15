package com.jeyblog.services;

import io.swagger.jaxrs.config.BeanConfig;
import javax.servlet.ServletConfig;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;
;
import java.util.HashSet;
import java.util.Set;

/**
 * The type Application services.
 * @author Jeanne
 * @version 1.0.0
 * @since 2020-06-07
 */
@ApplicationPath("/rest-api")
public class ApplicationServices extends Application {

    /**
     * Instantiates a new Application services.
     * Set the attributes of Bean Config used by Swagger to create url and documentation
     * @param servletConfig the servlet config
     */
    public ApplicationServices(@Context ServletConfig servletConfig) {
        BeanConfig beanConfig = new BeanConfig();
        //beanConfig.setVersion("1.0.0");
        //beanConfig.setTitle("Blog Post API");
        //beanConfig.setHost("3.21.54.147:8080");
        beanConfig.setHost("localhost:8080");
        beanConfig.setSchemes(new String[]{"http","https"});
        beanConfig.setBasePath("/jey-blog/rest-api");
        beanConfig.setResourcePackage("com.jeyblog.services");
        beanConfig.setScan(true);
        beanConfig.setPrettyPrint(true);
    }


    @Override
    public Set<Class<?>> getClasses() {
        HashSet classes = new HashSet<Class<?>>();
        classes.add(BlogResource.class);
        return classes;
    }
}
