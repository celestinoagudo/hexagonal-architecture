package hexagon.shop.application.port.in.cart;

import hexagon.shop.model.cart.Cart;
import hexagon.shop.model.cart.NotEnoughItemsInStockException;
import hexagon.shop.model.customer.CustomerId;
import hexagon.shop.model.product.ProductId;

public interface AddToCartUseCase {
  Cart addToCart(CustomerId customerId, ProductId productId, int quantity)
      throws ProductNotFoundException, NotEnoughItemsInStockException;
}
