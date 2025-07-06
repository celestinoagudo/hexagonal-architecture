package hexagon.shop.adapter.in.rest.cart;

import static hexagon.shop.adapter.in.rest.HttpTestCommons.TEST_PORT;
import static hexagon.shop.adapter.in.rest.cart.CartsControllerAssertions.assertThatResponseIsCart;
import static io.restassured.RestAssured.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import hexagon.shop.application.port.in.cart.AddToCartUseCase;
import hexagon.shop.application.port.in.cart.EmptyCartUseCase;
import hexagon.shop.application.port.in.cart.GetCartUseCase;
import hexagon.shop.application.port.in.cart.ProductNotFoundException;
import hexagon.shop.model.cart.Cart;
import hexagon.shop.model.cart.NotEnoughItemsInStockException;
import hexagon.shop.model.customer.CustomerId;
import hexagon.shop.model.money.TestMoneyFactory;
import hexagon.shop.model.product.Product;
import hexagon.shop.model.product.TestProductFactory;
import jakarta.ws.rs.core.Application;
import java.util.Set;
import org.jboss.resteasy.plugins.server.undertow.UndertowJaxrsServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class CartsControllerTest {

  private static final CustomerId TEST_CUSTOMER_ID = new CustomerId(12345);
  private static final Product TEST_PRODUCT_1 =
      TestProductFactory.createTestProduct(TestMoneyFactory.euros(12, 22));
  private static final AddToCartUseCase addToCartUseCase = mock(AddToCartUseCase.class);
  private static final GetCartUseCase getCartUseCase = mock(GetCartUseCase.class);
  private static final EmptyCartUseCase emptyCartUseCase = mock(EmptyCartUseCase.class);
  private static UndertowJaxrsServer server;

  @BeforeAll
  static void init() {
    server =
        new UndertowJaxrsServer()
            .setPort(TEST_PORT)
            .start()
            .deploy(
                new Application() {
                  @Override
                  public Set<Object> getSingletons() {
                    return Set.of(
                        new AddToCartController(addToCartUseCase),
                        new GetCartController(getCartUseCase),
                        new EmptyCartController(emptyCartUseCase));
                  }
                });
  }

  @AfterAll
  static void stop() {
    server.stop();
  }

  @Test
  void shouldAddToCart() throws NotEnoughItemsInStockException, ProductNotFoundException {
    // GIVEN: valid customer, product and quantity
    // WHEN: adding to Cart
    // THEN: expect items are added to cart.
    var customerId = TEST_CUSTOMER_ID;
    var productId = TEST_PRODUCT_1.id();
    var quantity = 5;
    var cart = new Cart(customerId);
    cart.addProduct(TEST_PRODUCT_1, quantity);
    when(addToCartUseCase.addToCart(customerId, productId, quantity)).thenReturn(cart);
    var response =
        given()
            .port(TEST_PORT)
            .queryParam("productId", productId.value())
            .queryParam("quantity", quantity)
            .post("/carts/" + customerId.value() + "/line-items")
            .then()
            .extract()
            .response();

    assertThatResponseIsCart(response, cart);
  }
}
