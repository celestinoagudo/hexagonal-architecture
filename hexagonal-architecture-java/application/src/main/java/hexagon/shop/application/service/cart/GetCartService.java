package hexagon.shop.application.service.cart;

import hexagon.shop.application.port.in.cart.GetCartUseCase;
import hexagon.shop.application.port.out.persistence.CartRepository;
import hexagon.shop.model.cart.Cart;
import hexagon.shop.model.customer.CustomerId;
import java.util.Objects;

public class GetCartService implements GetCartUseCase {

  private final CartRepository cartRepository;

  public GetCartService(CartRepository cartRepository) {
    this.cartRepository = cartRepository;
  }

  @Override
  public Cart getCart(final CustomerId customerId) {
    Objects.requireNonNull(customerId, "'customerId' must not be null");
    return cartRepository.findByCustomerId(customerId).orElseGet(() -> new Cart(customerId));
  }
}
