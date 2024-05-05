package com.mayur.OutboxEventPatternPOC.PaymentAcknowledge.controller;

import com.mayur.OutboxEventPatternPOC.PaymentAcknowledge.request.PaymentAcknowledgeRequest;
import com.mayur.OutboxEventPatternPOC.PaymentAcknowledge.service.PaymentAcknowledgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController(value = "/payment-acknowledge")
@RequestMapping("/payment-acknowledge")
public class PaymentAcknowledgeController {

    @Autowired
    PaymentAcknowledgeService paymentAcknowledgeService;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void paymentAcknowledged(@RequestBody final PaymentAcknowledgeRequest paymentAcknowledgeRequest) {
        paymentAcknowledgeService.acknowledgePayment(paymentAcknowledgeRequest);
    }
}
