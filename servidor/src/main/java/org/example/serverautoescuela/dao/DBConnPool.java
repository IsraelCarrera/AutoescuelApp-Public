package org.example.serverautoescuela.dao;


import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.annotation.PreDestroy;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.extern.log4j.Log4j2;
import org.example.serverautoescuela.config.Configuration;
import org.example.serverautoescuela.dao.utils.Constantes;

import javax.sql.DataSource;

@Log4j2
@Singleton
public class DBConnPool {

    private final Configuration config;
    private DataSource hirakiDatasource = null;

    @Inject
    public DBConnPool(Configuration config) {
        this.config = config;
        hirakiDatasource = getDataSourceHikari();
    }


    private DataSource getDataSourceHikari() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(this.config.getRuta());
        config.setUsername(this.config.getUser_bd());
        config.setPassword(this.config.getPassword_bd());
        config.setDriverClassName(this.config.getDriver());
        config.setMaximumPoolSize(1);
        config.addDataSourceProperty(Constantes.DBPOOL_CACHE_PREP_STMTS, Constantes.DBPOOL_VALUE_CACHE_PREP_STMTS);
        config.addDataSourceProperty(Constantes.DBPOOL_PREP_STMT_CACHE_SIZE, Constantes.DBPOOL_VALUE_CACHE_SIZE);
        config.addDataSourceProperty(Constantes.DBPOOL_PREP_STMT_CACHE_SQL_LIMIT, Constantes.DBPOOL_VALUE_PREP_STMT_CACHE_SQL_LIMIT);
        HikariDataSource datasource = new HikariDataSource(config);

        return datasource;
    }

    public DataSource getDataSource() {
        return hirakiDatasource;
    }


    @PreDestroy
    public void closePool() {
        ((HikariDataSource) hirakiDatasource).close();
    }
}
