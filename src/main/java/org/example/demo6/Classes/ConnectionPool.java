package org.example.demo6.Classes;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Database connection pool using HikariCP for better performance.
 * This class provides a centralized way to manage database connections,
 * reducing the overhead of creating new connections for each database operation.
 */
public class ConnectionPool {
    private static HikariDataSource dataSource;

    static {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:sqlite:database/LibraryMain");
        config.setMaximumPoolSize(10);
        config.setMinimumIdle(2);
        config.setConnectionTimeout(30000);
        config.setIdleTimeout(600000);
        config.setMaxLifetime(1800000);

        dataSource = new HikariDataSource(config);
    }

    /**
     * Get a connection from the pool
     * @return Connection object
     * @throws SQLException if unable to get connection
     */
    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    /**
     * Close the connection pool when application shuts down
     */
    public static void close() {
        if (dataSource != null) {
            dataSource.close();
        }
    }
}
