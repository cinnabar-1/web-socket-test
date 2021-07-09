package com.cinnabar.client.config.authToken;

import org.springframework.stereotype.Component;

@Component
public interface TokenGenerator {

    String generate(String[] strings);

}