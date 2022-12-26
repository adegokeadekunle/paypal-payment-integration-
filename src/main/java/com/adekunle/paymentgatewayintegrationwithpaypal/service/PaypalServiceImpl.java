package com.adekunle.paymentgatewayintegrationwithpaypal.service;

import com.adekunle.paymentgatewayintegrationwithpaypal.dto.PaypalPaymentDto;
import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service

public class PaypalServiceImpl implements PaypalService {

    @Autowired
    private APIContext apiContext;

    @Override
    public Payment createPayment(PaypalPaymentDto paypalPaymentDto) throws PayPalRESTException {

        Amount amount = new Amount();
        amount.setCurrency(paypalPaymentDto.getCurrency());
        // paypalPaymentDto.setTotal(new BigDecimal(paypalPaymentDto.getTotal()).setScale(2, RoundingMode.HALF_DOWN).doubleValue());
        Double total = paypalPaymentDto.setTotal(BigDecimal.valueOf(paypalPaymentDto.getTotal()).setScale(2, RoundingMode.HALF_DOWN).doubleValue());
        amount.setTotal(String.format("%.2f", total));

        Transaction transaction = new Transaction();
        transaction.setDescription(paypalPaymentDto.getDescription());
        transaction.setAmount(amount);

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        Payer payer = new Payer();
        payer.setPaymentMethod(paypalPaymentDto.getMethod());

        Payment payment = new Payment();
        payment.setIntent(paypalPaymentDto.getIntent());
        payment.setPayer(payer);
        payment.setTransactions(transactions);
        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl(paypalPaymentDto.getCancelUrl());
        redirectUrls.setReturnUrl(paypalPaymentDto.getSuccessUrl());
        payment.setRedirectUrls(redirectUrls);

        return payment.create(apiContext);
    }
    @Override
    public Payment executePayment(String paymentId, String payerId) throws PayPalRESTException {

        Payment payment = new Payment();
        payment.setId(paymentId);
        PaymentExecution paymentExecution = new PaymentExecution();
        paymentExecution.setPayerId(payerId);

        return payment.execute(apiContext,paymentExecution);

    }
}
