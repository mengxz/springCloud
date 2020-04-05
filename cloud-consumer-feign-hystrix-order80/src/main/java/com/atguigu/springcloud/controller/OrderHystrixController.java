package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.service.PaymentHystrixService;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
@DefaultProperties(defaultFallback = "golbalFallBack")
public class OrderHystrixController {
    @Resource
    private PaymentHystrixService paymentHystrixService;


    @GetMapping(value = "/consumer/payment/hystrix/ok/{id}")
    public String payment_ok(@PathVariable("id") int id){
        return paymentHystrixService.payment_ok(id);
    }

    @GetMapping(value = "/consumer/payment/hystrix/timeout/{id}")
    @HystrixCommand(fallbackMethod = "paymentFallBack",commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "2000")
    })
    public String payment_timeout(@PathVariable("id") int id){
        return paymentHystrixService.payment_timeout(id);
    }


    @GetMapping(value = "/consumer/payment/hystrix/excepte/{id}")
    @HystrixCommand
    public String payment_exception(@PathVariable("id") int id){
        int div = 10/0;
        return paymentHystrixService.payment_timeout(id);
    }

    //定制熔断处理方法
    public String paymentFallBack(int id){
        return "service 80 Fallback... thread name:"+Thread.currentThread().getName()
                +"  paymentFallBack id:"+id;
    }

    //通用熔断处理方法
    public String golbalFallBack(){
        return "service 80 Fallback... thread name:"+Thread.currentThread().getName()
                +"  golbalFallBack...";
    }
}
