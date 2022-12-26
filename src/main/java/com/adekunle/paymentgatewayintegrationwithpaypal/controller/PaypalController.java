package com.adekunle.paymentgatewayintegrationwithpaypal.controller;

import com.adekunle.paymentgatewayintegrationwithpaypal.dto.OrderDto;
import com.adekunle.paymentgatewayintegrationwithpaypal.service.PaypalService;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static com.adekunle.paymentgatewayintegrationwithpaypal.utils.AppConstants.CANCEL_URL;
import static com.adekunle.paymentgatewayintegrationwithpaypal.utils.AppConstants.SUCCESS_URL;


@Controller
@Slf4j
public class PaypalController {

    @Autowired
    PaypalService paypalService;

    @GetMapping("/")
    public String home(){
        return "home";
    }

    @PostMapping("/pay")
    public String payment(@ModelAttribute("orderDto") OrderDto order) {

        String serverPort = "localhost://9090/";

        try {
            Payment payment = paypalService.createPayment(order.getPrice(),
                    order.getCurrency(),
                    order.getMethod(),
                    order.getIntent(),
                    order.getDescription(),
                    serverPort + CANCEL_URL,
                    serverPort + SUCCESS_URL);

            //Links linke = payment.getLinks().forEach(lk->{if(lk.getRel().equals("approval_url")) return "redirect:"+lk.getHref();});

            for (Links link : payment.getLinks()) {
                if (link.getRel().equals("approval_url")) {
                    return "redirect:" + link.getHref();
                }
            }
        } catch (PayPalRESTException e) {
            e.printStackTrace();
        }

        return "redirect:/";
    }
    @GetMapping(value = CANCEL_URL)
    public String cancel(){
return "cancel";
    }
    @GetMapping(value = SUCCESS_URL)
    public String paySuccess(@RequestParam("paymentId") String paymentId,@RequestParam("payerId") String payerId){
        Payment payment = null;
        try {
            payment = paypalService.executePayment(paymentId,payerId);
        } catch (PayPalRESTException e) {
            log.error(e.getMessage(), e);
        }
        System.out.println(payment.toJSON());
        if(payment.getState().equals("approved")){
            return "success";
        }
        return "redirect:/";
    }
}
