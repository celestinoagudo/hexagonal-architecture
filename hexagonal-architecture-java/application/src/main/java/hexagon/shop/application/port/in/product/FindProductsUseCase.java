package hexagon.shop.application.port.in.product;

import hexagon.shop.model.product.Product;
import java.util.List;

public interface FindProductsUseCase {
  List<Product> findByNameOrDescription(String query);
}
