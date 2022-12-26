package com.adekunle.paymentgatewayintegrationwithpaypal.controller;

import com.adekunle.paymentgatewayintegrationwithpaypal.service.PaypalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class PaypalController {

    @Autowired
    private PaypalService paypalService;
}
