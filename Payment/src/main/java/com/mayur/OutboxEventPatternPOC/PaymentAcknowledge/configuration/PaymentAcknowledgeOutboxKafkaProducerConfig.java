package com.mayur.OutboxEventPatternPOC.PaymentAcknowledge.configuration;

import com.mayur.OutboxEventPatternPOC.PaymentAcknowledge.request.LedgerRequest;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class PaymentAcknowledgeOutboxKafkaProducerConfig {
    @Value("${com.mayur.OutboxEventPatternPOC.PaymentAcknowledge.LedgerRequest.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Bean
    public ProducerFactory<String, LedgerRequest> ledgerRequestProducerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        //This ensures that the Kafka producer will send messages idempotently, preventing duplicate messages from being produced.
        configProps.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, LedgerRequest> videoAnalysisRequestKafkaTemplate() {
        return new KafkaTemplate<>(ledgerRequestProducerFactory());
    }
}
