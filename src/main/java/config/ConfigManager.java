package config;

import enums.Environment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * ConfigManager class loads properties files and provides access to configuration values.
 */
public class ConfigManager {
    private static final Logger logger = LogManager.getLogger(ConfigManager.class);
    private static final Properties properties = new Properties();
    private static final Configuration configuration;

    static {
        loadProperties();
        configuration = new Configuration(new ConfigManager());
    }

    /**
     * Loads the base configuration and environment-specific properties using classpath resources.
     */
    private static void loadProperties() {
        // Load base config
        try (InputStream baseConfigStream = getResourceAsStream("config/config.properties")) {
            if (baseConfigStream == null) {
                logger.error("Base configuration file not found in classpath.");
                throw new RuntimeException("Base configuration file not found.");
            }
            properties.load(baseConfigStream);
        } catch (IOException e) {
            logger.error("Failed to load base configuration file.", e);
            throw new RuntimeException("Failed to load base configuration file.", e);
        }

        // Determine environment and load environment-specific config
        String envName = properties.getProperty("env", "DEV").toUpperCase();
        Environment env;
        try {
            env = Environment.valueOf(envName);
        } catch (IllegalArgumentException e) {
            logger.warn("Invalid environment specified: {}. Falling back to DEV.", envName);
            env = Environment.DEV;
        }

        String envConfigFile = String.format("config/%s-config.properties", env.name().toLowerCase());
        try (InputStream envConfigStream = getResourceAsStream(envConfigFile)) {
            if (envConfigStream == null) {
                logger.warn("Environment config file '{}' not found. Using base configuration only.", envConfigFile);
            } else {
                properties.load(envConfigStream);
            }
            logger.info("Configuration loaded for environment: {}", env);
        } catch (IOException e) {
            logger.error("Failed to load environment configuration file.", e);
            throw new RuntimeException("Failed to load environment configuration file.", e);
        }
    }

    /**
     * Returns the configuration value for the given key.
     * @param key the property key
     * @return the property value or null if not found
     */
    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    /**
     * Returns the configuration value for the given key, or a default value if the key is not found.
     * @param key the property key
     * @param defaultValue the default value if the key is not found
     * @return the property value or defaultValue if the key is not found
     */
    public String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    /**
     * Returns the Configuration POJO loaded from the properties.
     * @return The Configuration instance.
     */
    public static Configuration getConfiguration() {
        return configuration;
    }

    /**
     * Convenience method to get the current environment from the typed configuration.
     * @return The Environment enum value.
     */
    public static Environment getEnvironment() {
        return configuration.getEnvironment();
    }

    /**
     * Helper method to load resources from the classpath.
     * @param resourcePath The path to the resource file.
     * @return InputStream or null if not found.
     */
    private static InputStream getResourceAsStream(String resourcePath) {
        return ConfigManager.class.getClassLoader().getResourceAsStream(resourcePath);
    }
}
