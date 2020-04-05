package com.atguigu.springcloud.service;

import cn.hutool.core.util.IdUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.concurrent.TimeUnit;

@Service
public class PaymentService {

    public String payment_ok(int id){
        return "thread name:"+Thread.currentThread().getName()+"  payment_ok,id:"+id;
    }

    @HystrixCommand(fallbackMethod = "paymentFallBack",commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "2000")
    })
    public String payment_TimeOut(int id){
        //异常情况熔断
        //int div = 10/0;
        int second = 3;
        //线程睡眠指定时间
        try {
            TimeUnit.SECONDS.sleep(second);
        }catch (InterruptedException ex){
            ex.printStackTrace();
        }
        return "thread name:"+Thread.currentThread().getName()
                +"  payment_TimeOut,id:"+id
                +"  time seconds:"+second;
    }

    public String paymentFallBack(int id){
        return "service 8001 Fallback... thread name:"+Thread.currentThread().getName()
                +"  payment_TimeOut,id:"+id;
    }


    //=====服务熔断
    @HystrixCommand(fallbackMethod = "paymentCircuitBreaker_fallback",commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled",value = "true"),// 是否开启断路器
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold",value = "10"),// 请求次数
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds",value = "10000"), // 时间窗口期
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage",value = "60"),// 失败率达到多少后跳闸
    })
    public String paymentCircuitBreaker(@PathVariable("id") Integer id)
    {
        if(id < 0)
        {
            throw new RuntimeException("******id 不能负数");
        }
        String serialNumber = IdUtil.simpleUUID();

        return Thread.currentThread().getName()+"\t"+"调用成功，流水号: " + serialNumber;
    }
    public String paymentCircuitBreaker_fallback(@PathVariable("id") Integer id)
    {
        return "id 不能负数，请稍后再试，/(ㄒoㄒ)/~~   id: " +id;
    }
}
