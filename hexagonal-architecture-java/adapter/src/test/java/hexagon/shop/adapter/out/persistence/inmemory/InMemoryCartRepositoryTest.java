package hexagon.shop.adapter.out.persistence.inmemory;

import static org.junit.jupiter.api.Assertions.*;

import hexagon.shop.adapter.out.persistence.AbstractCartRepositoryTest;

class InMemoryCartRepositoryTest
    extends AbstractCartRepositoryTest<InMemoryCartRepository, InMemoryProductRepository> {
  @Override
  protected InMemoryCartRepository createCartRepository() {
    return new InMemoryCartRepository();
  }

  @Override
  protected InMemoryProductRepository createProductRepository() {
    return new InMemoryProductRepository();
  }
}
