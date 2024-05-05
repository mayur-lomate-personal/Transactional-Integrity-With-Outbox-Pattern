package com.mayur.OutboxEventPatternPOC.PaymentAcknowledge.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@NoArgsConstructor
@Setter
public class PaymentRequest {
    private String fromAccount;
    private String toAccount;
    private double amountToTransfer;
}
