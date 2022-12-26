package com.adekunle.paymentgatewayintegrationwithpaypal.service;

import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;

public interface PaypalService {

    Payment executePayment(String paymentId, String payerId) throws PayPalRESTException;// the Payment is a class provided by paypal which is imported

    Payment createPayment(Double price, String currency, String method, String intent, String description, String cancel_url, String success_url) throws PayPalRESTException;
}
