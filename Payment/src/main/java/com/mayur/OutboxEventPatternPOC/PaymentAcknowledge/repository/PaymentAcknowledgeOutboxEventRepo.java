package com.mayur.OutboxEventPatternPOC.PaymentAcknowledge.repository;

import com.mayur.OutboxEventPatternPOC.PaymentAcknowledge.model.PaymentAcknowledgeOutboxEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentAcknowledgeOutboxEventRepo  extends JpaRepository<PaymentAcknowledgeOutboxEvent, Long> {
    public List<PaymentAcknowledgeOutboxEvent> findAllBySent(Boolean sent);
}
