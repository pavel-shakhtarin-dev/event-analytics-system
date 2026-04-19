package ru.shpg.eventreceiver.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

@Component
public class JsonUtils {

    private final ObjectMapper objectMapper;

    public JsonUtils(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Named("toJson")
    public String toJson(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to serialize object to JSON", e);
        }
    }

}
