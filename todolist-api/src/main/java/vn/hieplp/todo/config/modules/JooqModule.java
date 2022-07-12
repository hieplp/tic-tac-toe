package vn.hieplp.todo.config.modules;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import vn.hieplp.todo.common.config.DatabaseConfig;
import vn.hieplp.todo.common.utils.JsonConverter;
import vn.hieplp.todo.common.utils.States;

/**
 * Copyright by HiepLP.
 * Creator: Ly Phuoc Hiep
 * Date: 29/04/2022
 * Time: 14:41
 */
public class JooqModule extends AbstractModule {
    private static final Logger LOGGER = LogManager.getLogger(JooqModule.class);

    private final DatabaseConfig dbConfig;
    private final HikariDataSource dataSource;

    public JooqModule(DatabaseConfig dbConfig) {
        this.dbConfig = dbConfig;
        this.dataSource = createConnection(this.dbConfig);
    }

    public static HikariDataSource createConnection(DatabaseConfig dbConfig) {
        LOGGER.info("Start create database connection with config {}", JsonConverter.toJson(dbConfig));

        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName(dbConfig.getDriverClassName());
        hikariConfig.setJdbcUrl(dbConfig.getJdbcUrl());
        hikariConfig.setUsername(dbConfig.getUsername());
        hikariConfig.setPassword(dbConfig.getPassword());
        hikariConfig.setConnectionTimeout(dbConfig.getConnectionTimeout());
        hikariConfig.addDataSourceProperty("serverTimezone", dbConfig.getServerTimezone());
        hikariConfig.addDataSourceProperty("useLegacyDatetimeCode", dbConfig.getUseLegacyDatetimeCode());
        hikariConfig.setAutoCommit(dbConfig.isAutoCommit());
        hikariConfig.setMinimumIdle(4);

        if (States.isNotNull(dbConfig.getMaxPoolSize())) {
            hikariConfig.setMaximumPoolSize(dbConfig.getMaxPoolSize());
        } else {
            hikariConfig.setMaximumPoolSize(8);
        }

        return new HikariDataSource(hikariConfig);
    }

    @Provides
    @Singleton
    public HikariDataSource getDataSource() {
        return dataSource;
    }

    @Override
    protected void configure() {
        LOGGER.info("Start loading jooq module");
    }
}