package com.zk.coupon_platform.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Binds {@code app.mongodb.*} entries from application.properties.
 * Each nested {@link Connection} represents a single MongoDB instance/database.
 */
@ConfigurationProperties(prefix = "app.mongodb")
public class MongoSettings {

    private Connection primary = new Connection();
    private Connection secondary = new Connection();

    public Connection getPrimary() {
        return primary;
    }

    public void setPrimary(Connection primary) {
        this.primary = primary;
    }

    public Connection getSecondary() {
        return secondary;
    }

    public void setSecondary(Connection secondary) {
        this.secondary = secondary;
    }

    public static class Connection {
        /** Standard MongoDB connection string, e.g. mongodb://user:pass@host:27017/?authSource=admin */
        private String uri;
        /** Logical database name to use on this connection. */
        private String database;

        public String getUri() {
            return uri;
        }

        public void setUri(String uri) {
            this.uri = uri;
        }

        public String getDatabase() {
            return database;
        }

        public void setDatabase(String database) {
            this.database = database;
        }
    }
}

