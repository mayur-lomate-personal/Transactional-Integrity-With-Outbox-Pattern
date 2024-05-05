package com.mayur.OutboxEventPatternPOC.PaymentAcknowledge.response;

import com.mayur.OutboxEventPatternPOC.PaymentAcknowledge.model.PaymentTransaction;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
@Getter
public class PaymentResponse {
    private final UUID transactionId;
    private final String fromAccount;
    private final String toAccount;
    private final double amountToTransfer;

    public PaymentResponse(PaymentTransaction paymentTransaction) {
        this.transactionId = paymentTransaction.getId();
        this.fromAccount = paymentTransaction.getFromAccount();
        this.toAccount = paymentTransaction.getToAccount();
        this.amountToTransfer = paymentTransaction.getTransferredAmount();
    }
}
