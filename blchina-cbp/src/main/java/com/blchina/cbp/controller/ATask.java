package com.blchina.cbp.controller;
import java.text.DateFormat;  
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;  
@Component
public class ATask {
       @Scheduled(cron="0 2 * * * ? ")   //每2分钟执行一次
       public void aTask(){
           DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
           System.out.print("\n"+"12121");
       }
}  