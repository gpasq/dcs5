package io.daikincloud.dcs.services;

import java.time.Instant;
import org.bson.Document;
import io.daikincloud.dcs.lib.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

// Define POJOs

class ServiceInfo {
    @JsonProperty("currentVersion")
    public String currentVersion;

    @JsonProperty("environment")
    public String environment;

    @JsonProperty("updatedAt")
    public String updatedAt;

    public ServiceInfo() {
    }
}

public class Hello {

    public static String get() {
        AppProperties props = new AppProperties();

        Document doc = Mongo.getCollection("ApiKeys").find().first();

        ServiceInfo info = new ServiceInfo();
        info.currentVersion = props.getProperty("dcs.currentVersion");
        info.environment = props.getProperty("dcs.env");
        info.updatedAt = Instant.now().toString();

        // Convert to JSON
        ObjectMapper mapper = new ObjectMapper();
        String json;
        try {
            json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(doc);
            return json;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "{}";
        }
    }
}
