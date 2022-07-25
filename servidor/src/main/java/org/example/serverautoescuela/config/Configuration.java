package org.example.serverautoescuela.config;


import jakarta.inject.Singleton;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.example.serverautoescuela.config.utils.Constantes;
import org.yaml.snakeyaml.Yaml;

import java.util.Map;

@Getter
@Log4j2
@Singleton
public class Configuration {
    private String ruta;
    private String user_bd;
    private String password_bd;
    private String driver;
    private String host;
    private String password_email;
    private String user_email;
    private String ruta_mongodb;
    private String mongo_database;
    private String mongo_collection;

    void cargar() {
        try {
            Yaml yaml = new Yaml();
            Iterable<Object> it = yaml.loadAll(this.getClass().getClassLoader().getResourceAsStream(Constantes.SETTINGS_YAML));
            Map<String, String> m = (Map) it.iterator().next();
            this.ruta = m.get(Constantes.RUTA);
            this.password_bd = m.get(Constantes.PASSWORD_BD);
            this.user_bd = m.get(Constantes.USER_BD);
            this.driver = m.get(Constantes.DRIVER);
            this.host = m.get(Constantes.HOST);
            this.password_email = m.get(Constantes.PASSWORD_MAIL);
            this.user_email = m.get(Constantes.USER_MAIL);
            this.ruta_mongodb = m.get(Constantes.RUTA_MONGODB);
            this.mongo_database = m.get(Constantes.MONGO_DATABASE);
            this.mongo_collection = m.get(Constantes.MONGO_COLLECTION);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
    }
}
