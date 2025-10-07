package com.example.continuous_data_service.config;

import com.example.continuous_data_service.domain.dto.response.DynamicSpecProducerResponseDto;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConfig {

    @Bean
    public ConsumerFactory<String, DynamicSpecProducerResponseDto> dynamicSpecConsumerFactory() {
        JsonDeserializer<DynamicSpecProducerResponseDto> deserializer =
                new JsonDeserializer<>(DynamicSpecProducerResponseDto.class);
        deserializer.addTrustedPackages("com.example.continuous_data_service.domain.dto.response");

        return new DefaultKafkaConsumerFactory<>(
                Map.of(
                        ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092",
                        ConsumerConfig.GROUP_ID_CONFIG, "aggregator-group",
                        ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class,
                        ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class
                ),
                new StringDeserializer(),
                deserializer
        );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, DynamicSpecProducerResponseDto> dynamicSpecKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, DynamicSpecProducerResponseDto> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(dynamicSpecConsumerFactory());
        return factory;
    }
}


