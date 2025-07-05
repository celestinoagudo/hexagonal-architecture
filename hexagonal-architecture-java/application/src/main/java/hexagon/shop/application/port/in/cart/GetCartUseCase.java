package hexagon.shop.application.port.in.cart;

import hexagon.shop.model.cart.Cart;
import hexagon.shop.model.customer.CustomerId;

public interface GetCartUseCase {

  Cart getCart(CustomerId customerId);
}
