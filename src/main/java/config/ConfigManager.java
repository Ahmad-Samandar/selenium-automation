package config;

import enums.Environment;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * ConfigManager class loads properties files and provides access to configuration values.
 */
public class ConfigManager {
    private static final Logger logger = LogManager.getLogger(ConfigManager.class);
    private static final Properties properties = new Properties();

    static {
        loadProperties();
    }

    /**
     * Loads the base configuration and environment-specific properties.
     */
    private static void loadProperties() {
        try {
            // Load base config into the main properties object
            FileInputStream baseConfig = new FileInputStream("src/main/resources/config/config.properties");
            properties.load(baseConfig);
            baseConfig.close();

            // Get environment from base config and load environment-specific config into the same properties object
            Environment env = Environment.valueOf(properties.getProperty("env").toUpperCase());
            String envConfigPath = "src/main/resources/config/" + env.name().toLowerCase() + "-config.properties";

            FileInputStream envConfig = new FileInputStream(envConfigPath);
            properties.load(envConfig);
            envConfig.close();

            logger.info("Configuration loaded for environment: " + env);
        } catch (IOException e) {
            logger.error("Failed to load configuration file.", e);
            throw new RuntimeException("Configuration loading failed.");
        }
    }

    /**
     * Returns the configuration value for the given key.
     * @param key the property key
     * @return the property value
     */
    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    /**
     * Returns the configuration value for the given key, or a default value if the key is not found.
     * @param key the property key
     * @param defaultValue the default value if the key is not found
     * @return the property value or defaultValue if the key is not found
     */
    public static String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    /**
     * Returns the current environment as an Environment enum.
     * @return the Environment enum value
     */
    public static Environment getEnvironment() {
        return Environment.valueOf(properties.getProperty("env").toUpperCase());
    }
}
