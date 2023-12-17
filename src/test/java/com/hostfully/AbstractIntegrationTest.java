package com.hostfully;

import com.hostfully.configuration.MockMvcConfiguration;
import com.hostfully.domain.property.usecase.CreateProperty;
import com.hostfully.domain.property.usecase.FindProperty;
import com.hostfully.domain.property.usecase.ListProperties;
import com.hostfully.infrastructure.converters.property.PropertyModelDTOMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:/clean-db.sql")
public abstract class AbstractIntegrationTest {

  @LocalServerPort
  protected int port;

  @Autowired
  protected CreateProperty createProperty;

  @Autowired
  protected ListProperties listProperties;

  @Autowired
  protected FindProperty findProperty;

  @Autowired
  protected PropertyModelDTOMapper propertyModelDTOMapper;

  @Autowired
  protected MockMvc mockMvc;

}
