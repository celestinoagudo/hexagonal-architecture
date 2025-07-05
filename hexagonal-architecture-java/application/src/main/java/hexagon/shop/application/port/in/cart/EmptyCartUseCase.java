package hexagon.shop.application.port.in.cart;

import hexagon.shop.model.customer.CustomerId;

public interface EmptyCartUseCase {

  void emptyCart(CustomerId customerId);
}
