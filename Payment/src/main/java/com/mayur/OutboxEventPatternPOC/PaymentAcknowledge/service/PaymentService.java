package com.mayur.OutboxEventPatternPOC.PaymentAcknowledge.service;

import com.mayur.OutboxEventPatternPOC.PaymentAcknowledge.model.PaymentTransaction;
import com.mayur.OutboxEventPatternPOC.PaymentAcknowledge.repository.PaymentTransactionRepo;
import com.mayur.OutboxEventPatternPOC.PaymentAcknowledge.request.PaymentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PaymentService {

    @Autowired
    PaymentTransactionRepo paymentTransactionRepo;

    public PaymentTransaction createPayment(PaymentRequest paymentRequest) {
        PaymentTransaction paymentTransaction = new PaymentTransaction();
        paymentTransaction.setCreatedAt(LocalDateTime.now());
        paymentTransaction.setFromAccount(paymentRequest.getFromAccount());
        paymentTransaction.setToAccount(paymentRequest.getToAccount());
        paymentTransaction.setTransferredAmount(paymentRequest.getAmountToTransfer());
        paymentTransaction.setIsCompleted(false);
        paymentTransaction.setIsRecorded(false);
        //For this actually we should have hit bank api, currently for POC directly populating a random id
        paymentTransaction.setBankTransactionId(UUID.randomUUID());
        paymentTransactionRepo.saveAndFlush(paymentTransaction);
        return paymentTransaction;
    }
}
