package hexagon.shop.application.port.out.persistence;

import hexagon.shop.model.cart.Cart;
import hexagon.shop.model.customer.CustomerId;
import java.util.Optional;

public interface CartRepository {

  void save(Cart cart);

  Optional<Cart> findByCustomerId(CustomerId customerId);

  void deleteById(CustomerId customerId);
}
