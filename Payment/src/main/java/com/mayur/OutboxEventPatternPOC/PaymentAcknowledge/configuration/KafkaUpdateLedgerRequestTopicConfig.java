package com.mayur.OutboxEventPatternPOC.PaymentAcknowledge.configuration;

import com.mayur.OutboxEventPatternPOC.PaymentAcknowledge.util.Constants;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

import java.util.Collections;

@Configuration
public class KafkaUpdateLedgerRequestTopicConfig {
    @Bean
    NewTopic videoAnalysisTopicCreation() {
        //return TopicBuilder.name(Constants.UPDATE_LEDGER_REQUEST_TOPIC_NAME).configs(Collections.singletonMap("retention.ms", "604800000")).partitions(3).replicas(3).build();
        return TopicBuilder.name(Constants.UPDATE_LEDGER_REQUEST_TOPIC_NAME).configs(Collections.singletonMap("retention.ms", "604800000")).build();
    }
}
