package com.jeyblog.services;

import io.swagger.jaxrs.config.BeanConfig;
import io.swagger.jaxrs.listing.AcceptHeaderApiListingResource;

import javax.servlet.ServletConfig;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;
;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ApplicationPath("/rest-api")
public class ApplicationServices extends Application {

    public ApplicationServices(@Context ServletConfig servletConfig) {
        BeanConfig beanConfig = new BeanConfig();
        beanConfig.setVersion("1.0.0");
        beanConfig.setTitle("Blog Post API");
        beanConfig.setSchemes(new String[]{"http"});
        beanConfig.setHost("localhost:8080");
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
//        return Stream.of(BlogResource.class,  AcceptHeaderApiListingResource.class).collect(Collectors.toSet());
    }
}
