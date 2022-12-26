package com.adekunle.paymentgatewayintegrationwithpaypal.config;

import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.OAuthTokenCredential;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class PaypalConfig {

    @Value("$paypal.client.id")
    private String clientId;
    @Value("$paypal.client.secret")
    private String clientSecret;
    @Value("$paypal.client.mode")
    private String clientMode;

    @Bean
    public Map<String, String> payPalSDKConfig() {
        Map<String,String> configMap = new HashMap<String,String>();
        configMap.put("mode",clientMode);
        return configMap;
    }

    @Bean
    public OAuthTokenCredential oauthTokenCredential(){
        return new OAuthTokenCredential(clientId,clientSecret,payPalSDKConfig());
    }
    @Bean
    public APIContext apiContext() throws PayPalRESTException {
        APIContext apiContext = new APIContext(oauthTokenCredential().getAccessToken());
        apiContext.setConfigurationMap(payPalSDKConfig());
        return apiContext;
    }
}
