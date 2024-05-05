package com.mayur.OutboxEventPatternPOC.PaymentAcknowledge.repository;

import com.mayur.OutboxEventPatternPOC.PaymentAcknowledge.model.PaymentTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PaymentTransactionRepo extends JpaRepository<PaymentTransaction, UUID> {
    public PaymentTransaction findByBankTransactionId(UUID bankTransactionId);
}
