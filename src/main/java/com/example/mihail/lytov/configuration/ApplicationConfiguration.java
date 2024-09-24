package com.example.mihail.lytov.configuration;

import org.camunda.bpm.dmn.engine.DmnEngine;
import org.camunda.bpm.dmn.engine.DmnEngineConfiguration;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.ProcessEngineConfiguration;
import org.camunda.bpm.engine.impl.ProcessEngineImpl;
import org.camunda.bpm.engine.spring.ProcessEngineFactoryBean;
import org.camunda.bpm.model.dmn.impl.instance.DecisionServiceImpl;
import org.camunda.bpm.model.dmn.instance.DecisionService;
import org.camunda.bpm.model.xml.ModelBuilder;
import org.camunda.bpm.model.xml.impl.instance.ModelTypeInstanceContext;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ApplicationConfiguration {

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    public DmnEngineConfiguration configurationDmn() {
        return DmnEngineConfiguration.createDefaultDmnEngineConfiguration();
    }



    @Bean
    public DmnEngine getDmnEngine(DmnEngineConfiguration configurationDmn) {
        return configurationDmn.buildEngine();
    }

    /*@Bean
    public ProcessEngineFactoryBean processEngine() {
        return new ProcessEngineFactoryBean();
    }*/

    /*@Bean
    public ProcessEngine processEngine(ProcessEngineFactoryBean factoryBean) throws Exception {
        return factoryBean.getObject();
    }*/


    public static void getDecisionService(ModelTypeInstanceContext modelTypeInstanceContext) {
        ModelBuilder modelBuilder = ModelBuilder.createInstance("");
        DecisionServiceImpl.registerType(modelBuilder);
        //return new DecisionServiceImpl(modelTypeInstanceContext);
    }

    /*@Bean
    public DecisionService getDecisionService(DmnEngineConfiguration configurationDmn) {
        return c
    }*/
}
