package com.neoway;

import java.io.IOException;
import java.util.Properties;

public class Configuration {

    private static Configuration configuration;

    private Properties properties;

    public Configuration() throws IOException {
        properties = new Properties();
        properties.load(Configuration.class.getClassLoader().getResourceAsStream("config.properties"));
    }

    public String getProperty(final String key) {
        return properties.getProperty(key);
    }

    public static synchronized Configuration getInstance() {
        if (configuration == null) {
            try {
                configuration = new Configuration();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return configuration;
    }
}
