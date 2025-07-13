package hexagon.shop.adapter.out.persistence.jpa;

import hexagon.shop.model.cart.Cart;
import hexagon.shop.model.cart.CartLineItem;
import hexagon.shop.model.customer.CustomerId;
import java.util.Objects;
import java.util.Optional;

final class CartMapper {

  private CartMapper() {}

  static CartJpaEntity toJpaEntity(final Cart cart) {
    var cartJpaEntity = CartJpaEntity.builder().customerId(cart.customerId().value()).build();
    cartJpaEntity.setLineItems(
        cart.lineItems().stream().map(lineItem -> toJpaEntity(cartJpaEntity, lineItem)).toList());
    return cartJpaEntity;
  }

  static CartLineItemJpaEntity toJpaEntity(
      final CartJpaEntity cartJpaEntity, final CartLineItem lineItem) {
    final var productJpaEntity =
        ProductJpaEntity.builder().id(lineItem.product().id().value()).build();
    return CartLineItemJpaEntity.builder()
        .cart(cartJpaEntity)
        .product(productJpaEntity)
        .quantity(lineItem.quantity())
        .build();
  }

  static Optional<Cart> toModelEntityOptional(final CartJpaEntity cartJpaEntity) {
    if (Objects.isNull(cartJpaEntity)) return Optional.empty();
    var cart = new Cart(new CustomerId(cartJpaEntity.getCustomerId()));
    cartJpaEntity
        .getLineItems()
        .forEach(
            cartLineItemJpaEntity -> {
              cart.putProductIgnoringNotEnoughItemsInStock(
                  ProductMapper.toModelEntity(cartLineItemJpaEntity.getProduct()),
                  cartLineItemJpaEntity.getQuantity());
            });
    return Optional.of(cart);
  }
}
