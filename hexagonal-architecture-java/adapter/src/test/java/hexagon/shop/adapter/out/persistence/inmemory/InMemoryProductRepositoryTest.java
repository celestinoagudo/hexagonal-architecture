package hexagon.shop.adapter.out.persistence.inmemory;

import static org.junit.jupiter.api.Assertions.*;

import hexagon.shop.adapter.out.persistence.AbstractProductRepositoryTest;

class InMemoryProductRepositoryTest
    extends AbstractProductRepositoryTest<InMemoryProductRepository> {
  @Override
  protected InMemoryProductRepository createProductRepository() {
    return new InMemoryProductRepository();
  }
}
