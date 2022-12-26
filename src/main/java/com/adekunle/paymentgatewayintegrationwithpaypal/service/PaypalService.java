package com.adekunle.paymentgatewayintegrationwithpaypal.service;

import com.adekunle.paymentgatewayintegrationwithpaypal.dto.PaypalPaymentDto;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;

public interface PaypalService {
    Payment createPayment(PaypalPaymentDto paypalPaymentDto) throws PayPalRESTException;  // the Payment is a class provided by paypal which is imported
    Payment executePayment(String paymentId, String payerId)throws PayPalRESTException;
}
