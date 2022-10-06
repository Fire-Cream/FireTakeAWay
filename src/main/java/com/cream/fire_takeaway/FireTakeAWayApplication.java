package com.cream.fire_takeaway;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

@Slf4j
@SpringBootApplication
@ServletComponentScan
@EnableTransactionManagement
public class FireTakeAWayApplication {

    public static void main(String[] args) throws MessagingException, UnsupportedEncodingException {
        SpringApplication.run(FireTakeAWayApplication.class, args);

        log.info("项目启动成功！");
    }

}
