package hexagon.shop.adapter.out.persistence;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import hexagon.shop.application.port.out.persistence.ProductRepository;
import hexagon.shop.model.product.Product;
import hexagon.shop.model.product.ProductId;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public abstract class AbstractProductRepositoryTest<T extends ProductRepository> {

  private T productRepository;

  @BeforeEach
  void initRepository() {
    productRepository = createProductRepository();
  }

  protected abstract T createProductRepository();

  @Test
  void givenTestProductsAndATestProductIdFindByIdReturnsATestProduct() {
    ProductId productId = DemoProducts.COMPUTER_MONITOR.id();

    Optional<Product> product = productRepository.findById(productId);

    assertThat(product).contains(DemoProducts.COMPUTER_MONITOR);
  }

  @Test
  void givenTheIdOfAProductNotPersistedFindByIdReturnsAnEmptyOptional() {
    ProductId productId = new ProductId("00000");

    Optional<Product> product = productRepository.findById(productId);

    assertThat(product).isEmpty();
  }

  @Test
  void
      givenTestProductsAndASearchQueryNotMatchingAndProductFindByNameOrDescriptionReturnsAnEmptyList() {
    String query = "not matching any product";

    List<Product> products = productRepository.findByNameOrDescription(query);

    assertThat(products).isEmpty();
  }

  @Test
  void
      givenTestProductsAndASearchQueryMatchingOneProductFindByNameOrDescriptionReturnsThatProduct() {
    String query = "lights";

    List<Product> products = productRepository.findByNameOrDescription(query);

    assertThat(products).containsExactlyInAnyOrder(DemoProducts.LED_LIGHTS);
  }

  @Test
  void
      givenTestProductsAndASearchQueryMatchingTwoProductsFindByNameOrDescriptionReturnsThoseProducts() {
    String query = "monitor";

    List<Product> products = productRepository.findByNameOrDescription(query);

    assertThat(products)
        .containsExactlyInAnyOrder(DemoProducts.COMPUTER_MONITOR, DemoProducts.MONITOR_DESK_MOUNT);
  }
}
