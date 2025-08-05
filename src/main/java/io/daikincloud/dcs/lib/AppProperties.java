package io.daikincloud.dcs.lib;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class AppProperties {
    private Properties props;

    public AppProperties() {
        String env = System.getenv("ENV");

        this.props = new Properties();

        if (env != null && env == "PRODUCTION") {
            try (InputStream input = getClass().getClassLoader().getResourceAsStream("production.properties")) {
                props.load(input);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            props.setProperty("dcs.env", env);
        } else {
            try (InputStream input = getClass().getClassLoader().getResourceAsStream("stage.properties")) {
                props.load(input);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            props.setProperty("dcs.env", "STAGE");
        }

        props.setProperty("currentVersion", "5.0.1");
    }

    public String getProperty(String key) {
        return props.getProperty(key);
    }

    public String getProperty(String key, String defaultValue) {
        return props.getProperty(key, defaultValue);
    }
}