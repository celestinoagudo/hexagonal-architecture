package hexagon.shop.adapter.out.persistence.inmemory;

import static java.util.Locale.ROOT;

import hexagon.shop.adapter.out.persistence.DemoProducts;
import hexagon.shop.application.port.out.persistence.ProductRepository;
import hexagon.shop.model.product.Product;
import hexagon.shop.model.product.ProductId;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryProductRepository implements ProductRepository {

  private final Map<ProductId, Product> products = new ConcurrentHashMap<>();

  public InMemoryProductRepository() {
    createDemoProducts();
  }

  private void createDemoProducts() {
    DemoProducts.DEMO_PRODUCTS.forEach(this::save);
  }

  @Override
  public List<Product> findByNameOrDescription(final String query) {
    var asLowerCase = query.toLowerCase(ROOT);
    return products.values().stream()
        .filter(product -> matchesQuery(product, asLowerCase))
        .toList();
  }

  @Override
  public Optional<Product> findById(final ProductId productId) {
    return Optional.ofNullable(products.get(productId));
  }

  @Override
  public void save(final Product product) {
    products.put(product.id(), product);
  }

  private boolean matchesQuery(final Product product, final String query) {
    return product.name().toLowerCase(ROOT).contains(query)
        || product.description().toLowerCase(ROOT).contains(query);
  }
}
