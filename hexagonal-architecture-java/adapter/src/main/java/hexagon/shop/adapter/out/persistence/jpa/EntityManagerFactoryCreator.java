package hexagon.shop.adapter.out.persistence.jpa;

import static java.util.Map.entry;
import static java.util.Map.ofEntries;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public final class EntityManagerFactoryCreator {
  private EntityManagerFactoryCreator() {}

  public static EntityManagerFactory createMySqlEntityManagerFactory(
      final String jdbcUrl, final String user, final String password) {

    return Persistence.createEntityManagerFactory(
        "hexagon.shop.adapter.out.persistence.jpa",
        ofEntries(
            entry("hibernate.dialect", "org.hibernate.dialect.MySQLDialect"),
            entry("hibernate.hbm2ddl.auto", "update"),
            entry("jakarta.persistence.jdbc.driver", "com.mysql.jdbc.Driver"),
            entry("jakarta.persistence.jdbc.url", jdbcUrl),
            entry("jakarta.persistence.jdbc.user", user),
            entry("jakarta.persistence.jdbc.password", password)));
  }
}
