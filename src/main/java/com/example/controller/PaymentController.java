package com.example.controller;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Value("${stripe.secret.key}")
    private String stripeSecretKey;

    @PostMapping("/create-payment-intent")
    public String createPaymentIntent() {
        try {
            // Set your secret key
            Stripe.apiKey = stripeSecretKey;

            // Create PaymentIntent using Stripe API
            PaymentIntent paymentIntent = PaymentIntent.create(createPaymentIntentParams());

            return "PaymentIntent created: " + paymentIntent.getId();
        } catch (StripeException e) {
            e.printStackTrace();
            return "Error creating PaymentIntent: " + e.getMessage();
        }
    }

    private PaymentIntentCreateParams createPaymentIntentParams() {
        int amount = 1000; // Adjust the amount as needed, representing $10.00
        return PaymentIntentCreateParams.builder()
                .setAmount((long) amount * 100)  // Amount in cents, adjust as needed
                .setCurrency("usd")
                .build();
    }
}
