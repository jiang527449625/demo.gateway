package com.demo.gateway.config;

import com.marcosbarbero.cloud.autoconfigure.zuul.ratelimit.config.repository.DefaultRateLimiterErrorHandler;
import com.marcosbarbero.cloud.autoconfigure.zuul.ratelimit.config.repository.RateLimiterErrorHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author jky
 **/
@Configuration
public class ZuulRatelimitConfiguration {

    @Bean
    public RateLimiterErrorHandler rateLimitErrorHandler() {
        return new DefaultRateLimiterErrorHandler() {
            @Override
            public void handleSaveError(String key, Exception e) {
                // custom code
                System.out.println("save error-------------------------------------");
            }

            @Override
            public void handleFetchError(String key, Exception e) {
                // custom code
                System.out.println("handleFetchError error-------------------------------------");
            }

            @Override
            public void handleError(String msg, Exception e) {
                // custom code
                System.out.println(" error-------------------------------------");
            }
        };
    }
}
