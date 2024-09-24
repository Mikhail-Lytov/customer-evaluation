package com.example.mihail.lytov.configuration;

import org.camunda.bpm.dmn.engine.DmnEngine;
import org.camunda.bpm.dmn.engine.DmnEngineConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfiguration {


    @Bean
    public DmnEngineConfiguration configurationDmn() {
        return DmnEngineConfiguration.createDefaultDmnEngineConfiguration();
    }

    @Bean
    public DmnEngine getDmnEngine(DmnEngineConfiguration configurationDmn) {
        return configurationDmn.buildEngine();
    }

}
