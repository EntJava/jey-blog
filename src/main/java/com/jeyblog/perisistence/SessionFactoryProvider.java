package com.jeyblog.perisistence;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

/**
 * This file provides a SessionFactory for use with DAOs using Hibernate
 *
 * @author Jeanne
 * @version 1.0
 * @since 2020 -04-04
 */
public class SessionFactoryProvider {
    private static SessionFactory sessionFactory;
    private SessionFactoryProvider(){
    }

    /**
     * Create session factory.
     */
    public static void createSessionFactory(){
        StandardServiceRegistry standardServiceRegistry =
                new StandardServiceRegistryBuilder()
                        .configure().build();
        Metadata metadata = new MetadataSources(standardServiceRegistry)
                .getMetadataBuilder().build();
        sessionFactory = metadata.getSessionFactoryBuilder().build();
    }

    /**
     * Get session factory session factory.
     *
     * @return the session factory
     */
    public  static SessionFactory getSessionFactory(){
        if(sessionFactory == null){
            createSessionFactory();
        }
        return  sessionFactory;
    }
}
