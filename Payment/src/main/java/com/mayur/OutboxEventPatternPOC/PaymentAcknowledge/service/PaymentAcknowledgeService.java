package com.mayur.OutboxEventPatternPOC.PaymentAcknowledge.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mayur.OutboxEventPatternPOC.PaymentAcknowledge.model.PaymentAcknowledgeOutboxEvent;
import com.mayur.OutboxEventPatternPOC.PaymentAcknowledge.model.PaymentTransaction;
import com.mayur.OutboxEventPatternPOC.PaymentAcknowledge.repository.PaymentAcknowledgeOutboxEventRepo;
import com.mayur.OutboxEventPatternPOC.PaymentAcknowledge.repository.PaymentTransactionRepo;
import com.mayur.OutboxEventPatternPOC.PaymentAcknowledge.request.LedgerRequest;
import com.mayur.OutboxEventPatternPOC.PaymentAcknowledge.request.PaymentAcknowledgeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class PaymentAcknowledgeService {

    @Autowired
    PaymentTransactionRepo paymentTransactionRepo;

    @Autowired
    PaymentAcknowledgeOutboxEventRepo paymentAcknowledgeOutboxEventRepo;

    @Autowired
    ObjectMapper objectMapper;

    @Transactional
    public void acknowledgePayment(final PaymentAcknowledgeRequest paymentAcknowledgeRequest){
        PaymentTransaction paymentTransaction = paymentTransactionRepo.findByBankTransactionId(paymentAcknowledgeRequest.getTransactionId());
        paymentTransaction.setIsCompleted(paymentAcknowledgeRequest.getIsCompleted());
        paymentTransaction.setCompletedOn(LocalDateTime.now());
        paymentTransactionRepo.save(paymentTransaction);
        if(paymentTransaction.getIsCompleted()) {
            PaymentAcknowledgeOutboxEvent paymentAcknowledgeOutboxEvent = new PaymentAcknowledgeOutboxEvent();
            paymentAcknowledgeOutboxEvent.setCreatedAt(LocalDateTime.now());
            paymentAcknowledgeOutboxEvent.setSent(false);
            try {
                paymentAcknowledgeOutboxEvent.setPayload(objectMapper.writeValueAsString(new LedgerRequest(paymentTransaction.getId(), paymentTransaction.getFromAccount(), paymentTransaction.getToAccount(),paymentTransaction.getTransferredAmount())));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            paymentAcknowledgeOutboxEventRepo.save(paymentAcknowledgeOutboxEvent);
        }
    }
}
