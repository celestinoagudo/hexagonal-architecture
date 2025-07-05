package hexagon.shop.application.service.product;

import hexagon.shop.application.port.in.product.FindProductsUseCase;
import hexagon.shop.application.port.out.persistence.ProductRepository;
import hexagon.shop.model.product.Product;
import java.util.List;
import java.util.Objects;

public class FindProductsService implements FindProductsUseCase {

  private final ProductRepository productRepository;

  public FindProductsService(final ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  @Override
  public List<Product> findByNameOrDescription(final String query) {
    Objects.requireNonNull(query, "'query' must not be null");
    if (query.length() < 2) {
      throw new IllegalArgumentException("'query' must be at least two characters long");
    }
    return productRepository.findByNameOrDescription(query);
  }
}
