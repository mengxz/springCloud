package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.service.PaymentFeignService;
import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDate;

@RestController
@Slf4j
public class OrderFeignController {
    @Resource
    private PaymentFeignService paymentFeignService;

    @GetMapping(value = "/consumer/payment/get/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id") Long id){
//        log.info("getPaymentById feign===result:{}",id);
//        CommonResult result = paymentFeignService.getPaymentById(id);
//        return  result;
        CommonResult<Payment> result =  paymentFeignService.getPaymentById(id);
        Payment payment = result.getData();
        log.info("payment:"+payment.getId()+","+payment.getSerial());
        return result;
    }

    @GetMapping(value = "/consumer/payment/feign/timeout")
    public CommonResult<String> paymentFeignTimeout(){
        String result = paymentFeignService.paymentFeignTimeout();
        return new CommonResult<String>(200,"suc",result);
    }

    @GetMapping(value = "/consumer/payment/get/time")
    public CommonResult<String> getTime(){
        LocalDate now = LocalDate.now();
        log.info("now:"+now.toString());
        CommonResult<String> result = new CommonResult(200,"suc",now.toString());
        return result;
    }
}
