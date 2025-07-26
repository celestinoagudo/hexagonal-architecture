package hexagon.shop.adapter.out.persistence.inmemory;

import hexagon.shop.application.port.out.persistence.CartRepository;
import hexagon.shop.model.cart.Cart;
import hexagon.shop.model.customer.CustomerId;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

@Repository
@ConditionalOnProperty(name = "persistence", havingValue = "inmemory", matchIfMissing = true)
public class InMemoryCartRepository implements CartRepository {

  private final Map<CustomerId, Cart> carts = new ConcurrentHashMap<>();

  @Override
  public void save(final Cart cart) {
    carts.put(cart.customerId(), cart);
  }

  @Override
  public Optional<Cart> findByCustomerId(final CustomerId customerId) {
    return Optional.ofNullable(carts.get(customerId));
  }

  @Override
  public void deleteById(final CustomerId customerId) {
    carts.remove(customerId);
  }
}
