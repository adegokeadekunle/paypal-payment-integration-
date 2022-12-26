package com.adekunle.paymentgatewayintegrationwithpaypal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderDto {

    private Double price;
    private String currency;
    private String method;
    private String intent;
    private String description;
}
