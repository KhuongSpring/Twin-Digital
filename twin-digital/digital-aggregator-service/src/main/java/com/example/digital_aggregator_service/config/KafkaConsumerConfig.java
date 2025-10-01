package com.example.digital_aggregator_service.config;

import com.example.digital_aggregator_service.domain.dto.response.DynamicSpecProducerResponseDto;
import com.example.digital_aggregator_service.domain.dto.response.StaticSpecProducerResponseDto;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.std.StringDeserializer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConsumerConfig {

    @Bean
    public ConsumerFactory<String, DynamicSpecProducerResponseDto> dynamicSpecConsumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "aggregator-group");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);

        props.put("spring.json.trusted.packages", "com.example.digital_aggregator_service.domain.dto.response");
        props.put("spring.json.value.default.type",
                "com.example.digital_aggregator_service.domain.dto.response.DynamicSpecProducerResponseDto");

        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, DynamicSpecProducerResponseDto> dynamicSpecKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, DynamicSpecProducerResponseDto> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(dynamicSpecConsumerFactory());
        return factory;
    }


    @Bean
    public ConsumerFactory<String, StaticSpecProducerResponseDto> staticSpecConsumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "aggregator-group");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);

        props.put("spring.json.trusted.packages", "com.example.digital_aggregator_service.domain.dto.response");
        props.put("spring.json.value.default.type",
                "com.example.digital_aggregator_service.domain.dto.response.StaticSpecProducerResponseDto");

        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, StaticSpecProducerResponseDto> staticSpecKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, StaticSpecProducerResponseDto> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(staticSpecConsumerFactory());
        return factory;
    }
}

