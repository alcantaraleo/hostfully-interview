package com.hostfully;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.AbstractTestExecutionListener;

public class CleanupDatabaseTestExecutionListener extends AbstractTestExecutionListener {

  @Override
  public void beforeTestExecution(final TestContext testContext) {
    cleanupDatabaseUsingJDBCTemplate(testContext);
  }

  private void cleanupDatabaseUsingJDBCTemplate(TestContext testContext) {
    final JdbcTemplate jdbcTemplate =
        testContext.getApplicationContext().getBean(JdbcTemplate.class);
    jdbcTemplate
        .queryForList("SELECT RELNAME FROM PG_STAT_USER_TABLES")
        .forEach(
            table -> {
              final var tableName = (String) table.get("RELNAME");
              if (!tableName.contains("database")) {
                jdbcTemplate.execute("TRUNCATE " + tableName + " CASCADE");
              }
            });
  }
}
