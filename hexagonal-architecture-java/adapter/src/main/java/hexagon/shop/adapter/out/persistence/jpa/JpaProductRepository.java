package hexagon.shop.adapter.out.persistence.jpa;

import hexagon.shop.adapter.out.persistence.DemoProducts;
import hexagon.shop.application.port.out.persistence.ProductRepository;
import hexagon.shop.model.product.Product;
import hexagon.shop.model.product.ProductId;
import jakarta.persistence.EntityManagerFactory;
import java.util.List;
import java.util.Optional;

public class JpaProductRepository implements ProductRepository {

  private final EntityManagerFactory entityManagerFactory;

  public JpaProductRepository(final EntityManagerFactory entityManagerFactory) {
    this.entityManagerFactory = entityManagerFactory;
    createDemoProducts();
  }

  private void createDemoProducts() {
    DemoProducts.DEMO_PRODUCTS.forEach(this::save);
  }

  @Override
  public List<Product> findByNameOrDescription(final String queryString) {
    try (var entityManager = entityManagerFactory.createEntityManager()) {
      var query =
          entityManager
              .createQuery(
                  "from ProductJpaEntity where name like :query or description like :query",
                  ProductJpaEntity.class)
              .setParameter("query", "%".concat(queryString).concat("%"));
      return ProductMapper.toModelEntities(query.getResultList());
    }
  }

  @Override
  public Optional<Product> findById(final ProductId productId) {
    try (var entityManager = entityManagerFactory.createEntityManager()) {
      var jpaEntity = entityManager.find(ProductJpaEntity.class, productId.value());
      return ProductMapper.toModelEntityOptional(jpaEntity);
    }
  }

  @Override
  public void save(final Product product) {
    try (var entityManager = entityManagerFactory.createEntityManager()) {
      entityManager.getTransaction().begin();
      entityManager.merge(ProductMapper.toJpaEntity(product));
      entityManager.getTransaction().commit();
    }
  }
}
