package org.example.serverautoescuela.config;

import jakarta.inject.Inject;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener()
public class ListenerConfig implements ServletContextListener {

    Configuration conf;
    //DBConnPool dbConnection;
    //Tema DBPool. Lo intenté poner, pero me daba un nullpointer y no sabía como arreglarlo. Por eso lo tengo comentado, para

    @Inject
    public ListenerConfig(Configuration conf) {
        this.conf = conf;
        //this.dbConnection = dbConnection;
    }

    public ListenerConfig() {
    }

    public void contextInitialized(ServletContextEvent sce) {
        conf.cargar();
    }


    public void contextDestroyed(ServletContextEvent sce) {
        //Cerrar el DBPool
        //dbConnection.closePool();
    }

}
