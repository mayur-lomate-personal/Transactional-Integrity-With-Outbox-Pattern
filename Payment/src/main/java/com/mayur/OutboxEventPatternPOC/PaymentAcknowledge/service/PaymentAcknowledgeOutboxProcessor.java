package com.mayur.OutboxEventPatternPOC.PaymentAcknowledge.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mayur.OutboxEventPatternPOC.PaymentAcknowledge.model.PaymentAcknowledgeOutboxEvent;
import com.mayur.OutboxEventPatternPOC.PaymentAcknowledge.repository.PaymentAcknowledgeOutboxEventRepo;
import com.mayur.OutboxEventPatternPOC.PaymentAcknowledge.util.AvroUtil;
import com.mayur.OutboxEventPatternPOC.schema.LedgerRequest;
import io.micrometer.core.annotation.Timed;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
public class PaymentAcknowledgeOutboxProcessor {

    @Autowired
    private PaymentAcknowledgeOutboxEventRepo paymentAcknowledgeOutboxEventRepo;

    @Autowired
    private KafkaTemplate<String, LedgerRequest> kafkaTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Timed(value = "PaymentAcknowledgeService.acknowledgePayment", description = "Time taken to execute acknowledgePayment method")
    @Scheduled(fixedRate = 5000) // Run every 5 seconds
    public void processOutboxEvents() {
        List<PaymentAcknowledgeOutboxEvent> outboxEvents = paymentAcknowledgeOutboxEventRepo.findAllBySent(false);

        for (PaymentAcknowledgeOutboxEvent outboxEvent : outboxEvents) {
            // Publish events to Kafka
            try {
                log.info("ledgerRequest in form of Object" + AvroUtil.deserializeFromString(outboxEvent.getPayload(), LedgerRequest.class).toString());
                kafkaTemplate.send("update-ledger", AvroUtil.deserializeFromString(outboxEvent.getPayload(), LedgerRequest.class));
            } catch (RuntimeException e) {
                throw new RuntimeException(e);
            }

            // Remove the event from the outbox
            outboxEvent.setSent(true);
            paymentAcknowledgeOutboxEventRepo.save(outboxEvent);
        }
    }
}
