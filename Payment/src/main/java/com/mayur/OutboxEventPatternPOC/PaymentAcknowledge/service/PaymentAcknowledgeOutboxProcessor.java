package com.mayur.OutboxEventPatternPOC.PaymentAcknowledge.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mayur.OutboxEventPatternPOC.PaymentAcknowledge.model.PaymentAcknowledgeOutboxEvent;
import com.mayur.OutboxEventPatternPOC.PaymentAcknowledge.repository.PaymentAcknowledgeOutboxEventRepo;
import com.mayur.OutboxEventPatternPOC.PaymentAcknowledge.request.LedgerRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentAcknowledgeOutboxProcessor {

    @Autowired
    private PaymentAcknowledgeOutboxEventRepo paymentAcknowledgeOutboxEventRepo;

    @Autowired
    private KafkaTemplate<String, LedgerRequest> kafkaTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Scheduled(fixedRate = 5000) // Run every 5 seconds
    public void processOutboxEvents() {
        List<PaymentAcknowledgeOutboxEvent> outboxEvents = paymentAcknowledgeOutboxEventRepo.findAllBySent(false);

        for (PaymentAcknowledgeOutboxEvent outboxEvent : outboxEvents) {
            // Publish events to Kafka
            try {
                kafkaTemplate.send("update-ledger", objectMapper.readValue(outboxEvent.getPayload(), LedgerRequest.class));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }

            // Remove the event from the outbox
            outboxEvent.setSent(true);
            paymentAcknowledgeOutboxEventRepo.save(outboxEvent);
        }
    }
}
