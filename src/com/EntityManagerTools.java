package com;

import com.google.common.reflect.ClassPath;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import javax.persistence.EntityManager;

/**
 * @author wanli zhou
 * @created 2017-06-14 10:56 PM.
 */
public class EntityManagerTools {
    private static final SessionFactory ourSessionFactory;
    private static final ServiceRegistry serviceRegistry;
    private static Session session;
    private static EntityManager em;
    static {
        try {
            Configuration configuration = new Configuration();
            configuration.configure("hibernate.cfg.xml");
            ClassPath cp =  ClassPath.from(EntityManagerTools.class.getClassLoader());
            for(ClassPath.ClassInfo ci : cp.getTopLevelClasses("com.entites")) {
                configuration.addAnnotatedClass(Class.forName(ci.getName()));
            }
            configuration.configure();
            serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
            ourSessionFactory = configuration.buildSessionFactory(serviceRegistry);
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static Session getSession() throws HibernateException {
        if(session == null){
            session = ourSessionFactory.openSession();
        }
        return session;
    }

    public static EntityManager getEntityManager(){
        if(em == null){
            em = ourSessionFactory.createEntityManager();
        }
        return em;
    }

}
