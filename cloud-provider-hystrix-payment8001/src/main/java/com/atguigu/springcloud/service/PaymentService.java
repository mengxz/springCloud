package com.atguigu.springcloud.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;

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
}
