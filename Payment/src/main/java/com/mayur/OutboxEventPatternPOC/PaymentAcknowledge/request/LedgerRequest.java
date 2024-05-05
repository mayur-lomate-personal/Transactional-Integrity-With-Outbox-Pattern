package com.mayur.OutboxEventPatternPOC.PaymentAcknowledge.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@AllArgsConstructor
@Getter
@NoArgsConstructor
@Setter
public class LedgerRequest implements Serializable {
    private UUID id;
    private String fromAccount;
    private String toAccount;
    private double transferredAmount;
}
