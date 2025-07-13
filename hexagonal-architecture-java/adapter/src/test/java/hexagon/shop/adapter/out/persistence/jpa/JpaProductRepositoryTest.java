package hexagon.shop.adapter.out.persistence.jpa;

import static org.junit.jupiter.api.Assertions.*;

import hexagon.shop.adapter.out.persistence.AbstractProductRepositoryTest;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.utility.DockerImageName;

@Disabled
class JpaProductRepositoryTest extends AbstractProductRepositoryTest<JpaProductRepository> {

  private static MySQLContainer<?> mySQLContainer;
  private static EntityManagerFactory entityManagerFactory;

  @BeforeAll
  static void startDatabase() {
    mySQLContainer = new MySQLContainer<>(DockerImageName.parse("mysql:latest"));
    mySQLContainer.start();
    entityManagerFactory =
        EntityManagerFactoryCreator.createMySqlEntityManagerFactory(
            mySQLContainer.getJdbcUrl(),
            mySQLContainer.getUsername(),
            mySQLContainer.getPassword());
  }

  @AfterAll
  static void stopDatabase() {
    entityManagerFactory.close();
    mySQLContainer.stop();
  }

  @Override
  protected JpaProductRepository createProductRepository() {
    return new JpaProductRepository(entityManagerFactory);
  }
}
