package hexagon.shop.adapter.in.rest.cart;

import static jakarta.ws.rs.core.Response.Status.OK;
import static org.assertj.core.api.Assertions.assertThat;

import hexagon.shop.model.cart.Cart;
import hexagon.shop.model.cart.CartLineItem;
import io.restassured.response.Response;
import java.util.Objects;

public final class CartsControllerAssertions {
  private CartsControllerAssertions() {}

  public static void assertThatResponseIsCart(final Response response, final Cart cart) {
    assertThat(response.statusCode()).isEqualTo(OK.getStatusCode());
    var json = response.jsonPath();
    for (int index = 0; index < cart.lineItems().size(); index++) {
      CartLineItem lineItem = cart.lineItems().get(index);
      String lineItemPrefix = "lineItems[%d].".formatted(index);
      assertThat(json.getString(lineItemPrefix + "productId"))
          .isEqualTo(lineItem.product().id().value());
      assertThat(json.getString(lineItemPrefix + "productName"))
          .isEqualTo(lineItem.product().name());
      assertThat(json.getString(lineItemPrefix + "price.currency"))
          .isEqualTo(lineItem.product().price().currency().getCurrencyCode());
      assertThat(json.getDouble(lineItemPrefix + "price.amount"))
          .isEqualTo(lineItem.product().price().amount().doubleValue());
      assertThat(json.getInt(lineItemPrefix + "quantity")).isEqualTo(lineItem.quantity());
    }
    assertThat(json.getInt("numberOfItems")).isEqualTo(cart.numberOfItems());
    if (Objects.nonNull(cart.subTotal())) {
      assertThat(json.getString("subTotal.currency"))
          .isEqualTo(cart.subTotal().currency().getCurrencyCode());
      assertThat(json.getDouble("subTotal.amount"))
          .isEqualTo(cart.subTotal().amount().doubleValue());
    } else {
      assertThat(json.getString("subTotal")).isNull();
    }
  }
}
