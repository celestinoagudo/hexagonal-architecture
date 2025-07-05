package hexagon.shop.application.service.cart;

import hexagon.shop.application.port.in.cart.AddToCartUseCase;
import hexagon.shop.application.port.in.cart.ProductNotFoundException;
import hexagon.shop.application.port.out.persistence.CartRepository;
import hexagon.shop.application.port.out.persistence.ProductRepository;
import hexagon.shop.model.cart.Cart;
import hexagon.shop.model.cart.NotEnoughItemsInStockException;
import hexagon.shop.model.customer.CustomerId;
import hexagon.shop.model.product.ProductId;
import java.util.Objects;

public class AddToCartService implements AddToCartUseCase {

  private final CartRepository cartRepository;
  private final ProductRepository productRepository;

  public AddToCartService(
      final CartRepository cartRepository, final ProductRepository productRepository) {
    this.cartRepository = cartRepository;
    this.productRepository = productRepository;
  }

  @Override
  public Cart addToCart(final CustomerId customerId, final ProductId productId, final int quantity)
      throws ProductNotFoundException, NotEnoughItemsInStockException {
    Objects.requireNonNull(customerId, "'customerId' must not be null");
    Objects.requireNonNull(productId, "'customerId' must not be null");
    if (quantity < 1) {
      throw new IllegalArgumentException("'quantity' must be greater than 0");
    }
    var product = productRepository.findById(productId).orElseThrow(ProductNotFoundException::new);
    var cart = cartRepository.findByCustomerId(customerId).orElseGet(() -> new Cart(customerId));
    cart.addProduct(product, quantity);
    cartRepository.save(cart);

    return cart;
  }
}
