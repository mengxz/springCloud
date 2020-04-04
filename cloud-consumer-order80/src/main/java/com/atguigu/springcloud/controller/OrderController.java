package com.atguigu.springcloud.controller;

import com.atguigu.myrule.MySelfRule;
import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
@Slf4j
//切换负载均衡算法
//@RibbonClient(name = "CLOUD-PAYMENT-SERVICE",configuration = MySelfRule.class)
public class OrderController {
    //public static final String PAYMENT_URL = "http://localhost:8001";
    public static final String PAYMENT_URL = "http://CLOUD-PAYMENT-SERVICE";
    @Resource
    private RestTemplate restTemplate;

    @PostMapping(value = "/consumer/payment/create")
    public CommonResult<Payment> create(@RequestBody Payment payment){
        String url = PAYMENT_URL +"/payment/create";
        log.info("create url:"+url);
        return restTemplate.postForObject(url,payment,CommonResult.class);
    }

    @PostMapping(value = "/consumer/payment/create2")
    public CommonResult<String> create2(@RequestBody Payment payment){
        String url = PAYMENT_URL +"/payment/create";
        log.info("create2 url:"+url);
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Payment> httpEntity = new HttpEntity<>(payment,headers);
        ResponseEntity<String> response2 = restTemplate.postForEntity(url, httpEntity, String.class);
        log.info("result2====================" + response2.getBody());
        return  new CommonResult(200,"suc",payment.getSerial());
    }

    @GetMapping(value = "/consumer/payment/get/{id}")
    public CommonResult getPaymentById(@PathVariable("id") Long id){
        String url = PAYMENT_URL +"/payment/get/"+id;
        log.info("getPaymentById url:"+url);
        return restTemplate.getForObject(url,CommonResult.class);
    }

    @GetMapping(value = "/consumer/payment/getForEntity/{id}")
    public CommonResult getPaymentById2(@PathVariable("id") Long id){
        String url = PAYMENT_URL +"/payment/get/"+id;
        log.info("getPaymentById2 url:"+url);
        ResponseEntity<CommonResult> entity = restTemplate.getForEntity(url, CommonResult.class);
        if(entity.getStatusCode().is2xxSuccessful()){
            return entity.getBody();
        }else {
            return  new CommonResult(444,"fail");
        }
    }
}
