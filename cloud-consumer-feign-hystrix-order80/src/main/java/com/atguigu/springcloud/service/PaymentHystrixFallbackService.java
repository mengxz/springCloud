package com.atguigu.springcloud.service;

import org.springframework.stereotype.Component;

@Component
public class PaymentHystrixFallbackService implements PaymentHystrixService{
    @Override
    public String payment_ok(int id) {
        return "service 80  HystrixFallbackService--payment_ok-id:"+id;
    }

    @Override
    public String payment_timeout(int id) {
        return "service 80  HystrixFallbackService--payment_timeout-id:"+id;
    }
}
