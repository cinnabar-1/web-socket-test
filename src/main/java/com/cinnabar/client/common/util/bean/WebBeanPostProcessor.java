package com.cinnabar.client.common.util.bean;

import com.cinnabar.client.mapper.MessageMapper;
import lombok.SneakyThrows;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author cinnabar-1
 * @version 1.0.0
 * @ClassName WebBeanPostProcessor.java
 * @Description
 * @createTime 2022-01-12  09:50:00
 */
@Component
public class WebBeanPostProcessor implements BeanPostProcessor {
    @SneakyThrows
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (beanName.equals("UserMapper")) {
            System.out.println("this is UserMapper after initialization");
            MapperFactoryBean userMapperMapperFactoryBean;
            Object o = null;
            if (bean instanceof MapperFactoryBean) {
                userMapperMapperFactoryBean = (MapperFactoryBean) bean;
                o = userMapperMapperFactoryBean.getObject();
            } else {
                o = bean;
                return Proxy.newProxyInstance(bean.getClass().getClassLoader(), bean.getClass().getInterfaces(),
                        (Object proxy, Method method, Object[] args) -> {
                            System.out.println("invocation getByUserAccount");
                            System.out.println(SpringBeans.getBean(MessageMapper.class).selectUser());
                            return method.invoke(bean, args);
                        });
            }
        }
        return bean;
    }
}
