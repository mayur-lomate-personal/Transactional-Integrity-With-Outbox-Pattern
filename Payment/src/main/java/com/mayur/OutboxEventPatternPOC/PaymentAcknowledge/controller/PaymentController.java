package com.mayur.OutboxEventPatternPOC.PaymentAcknowledge.controller;

import com.mayur.OutboxEventPatternPOC.PaymentAcknowledge.model.PaymentTransaction;
import com.mayur.OutboxEventPatternPOC.PaymentAcknowledge.request.PaymentAcknowledgeRequest;
import com.mayur.OutboxEventPatternPOC.PaymentAcknowledge.request.PaymentRequest;
import com.mayur.OutboxEventPatternPOC.PaymentAcknowledge.response.PaymentResponse;
import com.mayur.OutboxEventPatternPOC.PaymentAcknowledge.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController(value = "/payment")
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    PaymentService paymentService;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<PaymentResponse> createPayment(@RequestBody final PaymentRequest paymentRequest) {
        PaymentTransaction paymentTransaction = paymentService.createPayment(paymentRequest);
        return new ResponseEntity<>(new PaymentResponse(paymentTransaction), HttpStatus.OK);
    }
}
