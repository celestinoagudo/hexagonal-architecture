package hexagon.shop;

import hexagon.shop.application.port.in.cart.AddToCartUseCase;
import hexagon.shop.application.port.in.cart.EmptyCartUseCase;
import hexagon.shop.application.port.in.cart.GetCartUseCase;
import hexagon.shop.application.port.in.product.FindProductsUseCase;
import hexagon.shop.application.port.out.persistence.CartRepository;
import hexagon.shop.application.port.out.persistence.ProductRepository;
import hexagon.shop.application.service.cart.AddToCartService;
import hexagon.shop.application.service.cart.EmptyCartService;
import hexagon.shop.application.service.cart.GetCartService;
import hexagon.shop.application.service.product.FindProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringAppConfiguration {
  private final CartRepository cartRepository;
  private final ProductRepository productRepository;

  public SpringAppConfiguration(
      @Autowired final CartRepository cartRepository,
      @Autowired final ProductRepository productRepository) {
    this.cartRepository = cartRepository;
    this.productRepository = productRepository;
  }

  @Bean
  GetCartUseCase getCartUseCase() {
    return new GetCartService(cartRepository);
  }

  @Bean
  EmptyCartUseCase emptyCartUseCase() {
    return new EmptyCartService(cartRepository);
  }

  @Bean
  FindProductsUseCase findProductsUseCase() {
    return new FindProductsService(productRepository);
  }

  @Bean
  AddToCartUseCase addToCartUseCase() {
    return new AddToCartService(cartRepository, productRepository);
  }
}
