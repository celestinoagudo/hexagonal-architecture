package hexagon.shop.application.port.out.persistence;

import hexagon.shop.model.product.Product;
import hexagon.shop.model.product.ProductId;
import java.util.List;
import java.util.Optional;

public interface ProductRepository {
  List<Product> findByNameOrDescription(String query);

  Optional<Product> findById(ProductId productId);

  void save(Product product);
}
