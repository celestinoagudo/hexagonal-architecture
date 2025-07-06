package hexagon.shop.adapter.in.rest.cart;

import hexagon.shop.model.cart.Cart;
import hexagon.shop.model.money.Money;
import java.util.List;

public record CartWebModel(
    List<CartLineItemWebModel> lineItems, int numberOfItems, Money subTotal) {

  static CartWebModel fromDomainModel(final Cart cart) {
    return new CartWebModel(
        cart.lineItems().stream().map(CartLineItemWebModel::fromDomainModel).toList(),
        cart.numberOfItems(),
        cart.subTotal());
  }
}
