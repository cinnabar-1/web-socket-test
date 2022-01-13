package com.cinnabar.client;

import com.cinnabar.client.config.filtertTest.FilterRequest;
import com.cinnabar.client.config.filtertTest.FilterTest;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.servlet.Filter;

@SpringBootApplication
//@EnableEurekaClient
@ComponentScan(basePackages = "com.cinnabar")
//@EnableFeignClients
//@EnableScheduling
@ServletComponentScan
public class ClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClientApplication.class, args);
    }

    @Bean
    public FilterRegistrationBean<Filter> timeFilter() {
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        FilterTest filter = new FilterTest();
        FilterRequest filterRequest = new FilterRequest();
        filterRegistrationBean.setFilter(filter);
        filterRegistrationBean.setFilter(filterRequest);
        filterRegistrationBean.addUrlPatterns("/*", "/*");
        return filterRegistrationBean;
    }
}
