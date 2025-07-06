package hexagon.shop.adapter.in.rest.cart;

import hexagon.shop.model.cart.CartLineItem;
import hexagon.shop.model.money.Money;

public record CartLineItemWebModel(
    String productId, String productName, Money price, int quantity) {
  public static CartLineItemWebModel fromDomainModel(final CartLineItem lineItem) {
    var product = lineItem.product();
    return new CartLineItemWebModel(
        product.id().value(), product.name(), product.price(), lineItem.quantity());
  }
}
