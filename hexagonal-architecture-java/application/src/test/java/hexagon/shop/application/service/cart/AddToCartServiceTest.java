package hexagon.shop.application.service.cart;

import static hexagon.shop.model.money.TestMoneyFactory.euros;
import static hexagon.shop.model.product.TestProductFactory.createTestProduct;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import hexagon.shop.application.port.in.cart.ProductNotFoundException;
import hexagon.shop.application.port.out.persistence.CartRepository;
import hexagon.shop.application.port.out.persistence.ProductRepository;
import hexagon.shop.model.cart.Cart;
import hexagon.shop.model.cart.NotEnoughItemsInStockException;
import hexagon.shop.model.customer.CustomerId;
import hexagon.shop.model.product.Product;
import hexagon.shop.model.product.ProductId;
import java.util.Optional;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AddToCartServiceTest {

  private static final CustomerId TEST_CUSTOMER_ID = new CustomerId(12345);
  private static final Product TEST_PRODUCT_1 = createTestProduct(euros(19, 99));
  private static final Product TEST_PRODUCT_2 = createTestProduct(euros(19, 99));

  private final CartRepository cartRepository = mock(CartRepository.class);
  private final ProductRepository productRepository = mock(ProductRepository.class);
  private final AddToCartService addToCartService =
      new AddToCartService(cartRepository, productRepository);

  @BeforeEach
  void initializeTestDoubles() {
    when(productRepository.findById(TEST_PRODUCT_1.id())).thenReturn(Optional.of(TEST_PRODUCT_1));
    when(productRepository.findById(TEST_PRODUCT_2.id())).thenReturn(Optional.of(TEST_PRODUCT_2));
  }

  @Test
  void shouldSaveCartWhenTheresExistingCart()
      throws NotEnoughItemsInStockException, ProductNotFoundException {
    // GIVEN: Existing Cart
    // WHEN: Adding to Cart
    // THEN: Expect cart with added product is saved and returned.
    var existingCart = new Cart(TEST_CUSTOMER_ID);
    existingCart.addProduct(TEST_PRODUCT_1, 1);
    when(cartRepository.findByCustomerId(TEST_CUSTOMER_ID)).thenReturn(Optional.of(existingCart));
    var savedCart = addToCartService.addToCart(TEST_CUSTOMER_ID, TEST_PRODUCT_2.id(), 3);
    verify(cartRepository).save(savedCart);
    assertThat(savedCart.lineItems()).hasSize(2);
    assertThat(savedCart.lineItems().getFirst().product()).isEqualTo(TEST_PRODUCT_1);
    assertThat(savedCart.lineItems().getFirst().quantity()).isEqualTo(1);
    assertThat(savedCart.lineItems().get(1).product()).isEqualTo(TEST_PRODUCT_2);
    assertThat(savedCart.lineItems().get(1).quantity()).isEqualTo(3);
  }

  @Test
  void shouldSaveCartWhenNoExistingCart()
      throws NotEnoughItemsInStockException, ProductNotFoundException {
    // GIVEN: No Existing Cart
    // WHEN: Adding to Cart
    // THEN: Expect cart with added product is saved and returned.
    var cart = addToCartService.addToCart(TEST_CUSTOMER_ID, TEST_PRODUCT_1.id(), 2);
    verify(cartRepository).save(cart);
    assertThat(cart.lineItems()).hasSize(1);
    assertThat(cart.lineItems().getFirst().product()).isEqualTo(TEST_PRODUCT_1);
    assertThat(cart.lineItems().getFirst().quantity()).isEqualTo(2);
  }

  @Test
  void shouldThrowExceptionWhenUnknownProductId() {
    // GIVEN: Unknown Product.
    // WHEN: Adding to Cart.
    // THEN: Expect an Exception is thrown.
    var productId = ProductId.generateRandomProductId();
    ThrowingCallable addToCartInvocation =
        () -> addToCartService.addToCart(TEST_CUSTOMER_ID, productId, 1);
    assertThatExceptionOfType(ProductNotFoundException.class).isThrownBy(addToCartInvocation);
    verify(cartRepository, never()).save(any());
  }

  @Test
  void shouldThrowExceptionWhenQuantityIsLessThan1() {
    // GIVEN: A Quantity of > 1
    // WHEN: Adding to Cart.
    // THEN: Expect an Exception is thrown.
    ThrowingCallable addToCartInvocation =
        () -> addToCartService.addToCart(TEST_CUSTOMER_ID, TEST_PRODUCT_1.id(), -1);
    assertThatIllegalArgumentException().isThrownBy(addToCartInvocation);
    verify(cartRepository, never()).save(any());
  }
}
