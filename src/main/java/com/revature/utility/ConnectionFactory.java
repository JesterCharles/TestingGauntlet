package com.revature.utility;

import com.revature.member.Member;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.io.IOException;
import java.util.Properties;

public class ConnectionFactory {
    private static SessionFactory sessionFactory;
    private static Session session;

    public static Session getSession() throws IOException {
        if(sessionFactory == null) {
            Configuration configuration = new Configuration();
            Properties properties = new Properties();

            // Searching the thread for the file specified and streaming it into the properties.load()
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            properties.load(loader.getResourceAsStream("hibernate.properties"));
//            properties.load(new FileReader("src/main/resources/hibernate.properties"));

            // ONE ADDITIONAL STEP I NEED TO INCLUDE
            configuration.addAnnotatedClass(Member.class);

            // ServiceRegistry
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();

            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        }

        if(session == null) {
            session = sessionFactory.openSession();
        }

        return session;
    }

    public static void closeSession() {
        session.close();
        session = null;

    }
}
