package hexagon.shop.adapter.out.persistence.jpa;

import hexagon.shop.adapter.out.persistence.DemoProducts;
import hexagon.shop.application.port.out.persistence.ProductRepository;
import hexagon.shop.model.product.Product;
import hexagon.shop.model.product.ProductId;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

@Repository
@ConditionalOnProperty(name = "persistence", havingValue = "mysql")
public class JpaProductRepository implements ProductRepository {

  private final JpaProductSpringDataRepository productSpringDataRepository;

  public JpaProductRepository(final JpaProductSpringDataRepository productSpringDataRepository) {
    this.productSpringDataRepository = productSpringDataRepository;
    createDemoProducts();
  }

  private void createDemoProducts() {
    DemoProducts.DEMO_PRODUCTS.forEach(this::save);
  }

  @Override
  @Transactional
  public List<Product> findByNameOrDescription(final String queryString) {
    var products =
        productSpringDataRepository.findByNameOrDescriptionLike(
            "%".concat(queryString).concat("%"));
    return ProductMapper.toModelEntities(products);
  }

  @Override
  @Transactional
  public Optional<Product> findById(final ProductId productId) {
    var productJpaEntity = productSpringDataRepository.findById(productId.value());
    return productJpaEntity.map(ProductMapper::toModelEntity);
  }

  @Override
  @Transactional
  public void save(final Product product) {
    productSpringDataRepository.save(ProductMapper.toJpaEntity(product));
  }
}
