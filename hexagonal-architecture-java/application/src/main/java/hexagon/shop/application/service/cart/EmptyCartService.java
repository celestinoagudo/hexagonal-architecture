package hexagon.shop.application.service.cart;

import hexagon.shop.application.port.in.cart.EmptyCartUseCase;
import hexagon.shop.application.port.out.persistence.CartRepository;
import hexagon.shop.model.customer.CustomerId;
import java.util.Objects;

public class EmptyCartService implements EmptyCartUseCase {

  private final CartRepository cartRepository;

  public EmptyCartService(final CartRepository cartRepository) {
    this.cartRepository = cartRepository;
  }

  @Override
  public void emptyCart(final CustomerId customerId) {
    Objects.requireNonNull(customerId, "'customerId' must not be null");
    cartRepository.deleteById(customerId);
  }
}
