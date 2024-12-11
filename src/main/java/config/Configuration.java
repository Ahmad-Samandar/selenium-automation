package config;

import enums.Environment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Configuration {
    private static final Logger logger = LogManager.getLogger(Configuration.class);

    private Environment environment;
    private final String browser;
    private final boolean headless;
    private final boolean enableWait;
    private final int waitInSeconds;
    private final String baseUrl;
    private final int timeout;

    /**
     * Creates a typed configuration object from loaded properties.
     *
     * @param configManager The ConfigManager instance with loaded properties.
     */
    public Configuration(ConfigManager configManager) {
        // Handle Environment with fallback
        String envValue = configManager.getProperty("env", "DEV");
        try {
            this.environment = Environment.valueOf(envValue.toUpperCase());
        } catch (IllegalArgumentException e) {
            logger.warn("Invalid environment specified: {}. Falling back to DEV.", envValue);
            this.environment = Environment.DEV;
        }

        this.browser = configManager.getProperty("browser", "CHROME");
        this.headless = Boolean.parseBoolean(configManager.getProperty("headless", "false"));
        this.enableWait = Boolean.parseBoolean(configManager.getProperty("enableWait", "false"));

        String waitSecondsValue = configManager.getProperty("waitInSeconds", "0");
        this.waitInSeconds = parseIntegerOrDefault(waitSecondsValue, 0, "waitInSeconds");

        this.baseUrl = configManager.getProperty("baseUrl", "http://example.com");

        String timeoutValue = configManager.getProperty("timeout", "10");
        this.timeout = parseIntegerOrDefault(timeoutValue, 10, "timeout");
    }

    private int parseIntegerOrDefault(String value, int defaultValue, String propertyName) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            logger.warn("Invalid integer for {}: '{}'. Falling back to {}.", propertyName, value, defaultValue);
            return defaultValue;
        }
    }

    public Environment getEnvironment() {
        return environment;
    }

    public String getBrowser() {
        return browser;
    }

    public boolean isHeadless() {
        return headless;
    }

    public boolean isEnableWait() {
        return enableWait;
    }

    public int getWaitInSeconds() {
        return waitInSeconds;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public int getTimeout() {
        return timeout;
    }
}
