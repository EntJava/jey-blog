package com.jeyblog.services;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/api")
public class ApplicationServices extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        HashSet classes = new HashSet<Class<?>>();
        classes.add(BlogResource.class);
        return classes;
    }
}
