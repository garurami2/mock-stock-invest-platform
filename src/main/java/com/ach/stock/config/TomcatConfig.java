package com.ach.stock.config;

import org.apache.catalina.Context;
import org.apache.catalina.core.StandardContext;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TomcatConfig {

    @Bean
    public WebServerFactoryCustomizer<TomcatServletWebServerFactory> containerCustomizer() {
        return factory -> factory.addContextCustomizers((Context context) ->  {
            if (context instanceof StandardContext) {
                ((StandardContext) context).setClearReferencesStopThreads(true);
                ((StandardContext) context).setClearReferencesStopTimerThreads(true);
            }
        });
    }

}
