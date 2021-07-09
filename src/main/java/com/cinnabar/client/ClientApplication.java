package com.cinnabar.client;

import com.cinnabar.client.config.filtertTest.FilterTest;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//@EnableEurekaClient
@ComponentScan(basePackages = "com.cinnabar")
//@EnableFeignClients
public class ClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClientApplication.class, args);
    }

    @Bean
    public FilterRegistrationBean<FilterTest> timeFilter() {
        FilterRegistrationBean<FilterTest> filterRegistrationBean = new FilterRegistrationBean<>();
        FilterTest filter = new FilterTest();
        filterRegistrationBean.setFilter(filter);
        filterRegistrationBean.addUrlPatterns("/*","/*");
        return filterRegistrationBean;
    }
}
