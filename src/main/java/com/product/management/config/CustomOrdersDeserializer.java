package com.product.management.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.product.management.model.Orders;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Deserializer;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class CustomOrdersDeserializer implements Deserializer<Orders> {

    private final ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT)
            .registerModule(new JavaTimeModule());

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        Deserializer.super.configure(configs, isKey);
    }

    @Override
    public Orders deserialize(String topic, byte[] data) {
        log.debug("Entered custom serializer. Topic {}, data {}", topic, data);
        if (data == null) {
            return null;
        }
        Orders order = null;
        try {
            order = objectMapper.readValue(new String(data), Orders.class);
        } catch (Exception e) {
            log.error("Failed to deserialize JSON to Order: {}", e.getMessage());
        }
        return order;
    }

    @Override
    public void close() {
        log.debug("CustomOrdersDeserializer closed");
    }
}
