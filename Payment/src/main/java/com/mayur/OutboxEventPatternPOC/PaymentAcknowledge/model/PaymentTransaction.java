package com.mayur.OutboxEventPatternPOC.PaymentAcknowledge.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
public class PaymentTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(nullable = false)
    private UUID bankTransactionId;
    @Column(nullable = false)
    private String fromAccount;
    @Column(nullable = false)
    private String toAccount;
    @Column(nullable = false)
    private double transferredAmount;
    @Column(nullable = false)
    private Boolean isCompleted;
    @Column(nullable = false)
    private Boolean isRecorded;
    @Column(nullable = false)
    private LocalDateTime createdAt;
    private LocalDateTime completedOn;
}
