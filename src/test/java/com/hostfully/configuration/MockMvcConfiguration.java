package com.hostfully.configuration;

import com.hostfully.application.property.PropertyController;
import com.hostfully.infrastructure.configuration.exceptionhandling.HostfullyControllerAdvice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@Configuration
public class MockMvcConfiguration {

  @Autowired
  private PropertyController propertyController;

  @Autowired
  private HostfullyControllerAdvice restControllerAdvice;

  @Bean("mockMvcInterceptor")
  public MockMvc mockMvcInterceptor() {
    return MockMvcBuilders.standaloneSetup(
            propertyController)
        .setControllerAdvice(restControllerAdvice)
        .build();
  }

}
