package com.adekunle.paymentgatewayintegrationwithpaypal.service;

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
    public Payment createPayment(Double total,
                                 String currency,
                                 String method,
                                 String intent,
                                 String description,
                                 String cancel_url,
                                 String success_url) throws PayPalRESTException {

        Amount amount = new Amount();
        amount.setCurrency(currency);
        // paypalPaymentDto.setTotal(new BigDecimal(paypalPaymentDto.getTotal()).setScale(2, RoundingMode.HALF_DOWN).doubleValue());
        total = BigDecimal.valueOf(total).setScale(2, RoundingMode.HALF_DOWN).doubleValue();
        amount.setTotal(String.format("%.2f", total));

        Transaction transaction = new Transaction();
        transaction.setDescription(description);
        transaction.setAmount(amount);

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        Payer payer = new Payer();
        payer.setPaymentMethod(method);

        Payment payment = new Payment();
        payment.setIntent(intent);
        payment.setPayer(payer);
        payment.setTransactions(transactions);
        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl(cancel_url);
        redirectUrls.setReturnUrl(success_url);
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
