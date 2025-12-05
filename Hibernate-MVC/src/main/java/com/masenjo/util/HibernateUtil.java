package com.masenjo.util;
import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import com.masenjo.model.Jugador;
import com.masenjo.model.Videojuego;
public class HibernateUtil {
    private static SessionFactory sessionFactory;
    public HibernateUtil(String configurationFile, Class[] clases) throws Exception {
        try {
            // Construye la SessionFactory usando la configuración de hibernate.cfg.xml
            sessionFactory = new Configuration().configure(configurationFile) // Carga la configuración de Hibernate
                    .addAnnotatedClass(clases[0]) // Indica las clases de entidad
                    .addAnnotatedClass(clases[1])
                    .buildSessionFactory(); // Construye la SessionFactory
        } catch (Exception e) {
            throw new Exception("No se ha podido configurar el entorno Hibernate.");
        }
    }
    // Obtiene la SessionFactory para poder abrir sesiones de Hibernate
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    // Cierra la SessionFactory
    public static void shutdown() {
        getSessionFactory().close();
    }
}