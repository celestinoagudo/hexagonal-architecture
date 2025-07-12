package hexagon.shop.bootstrap;

import hexagon.shop.adapter.in.rest.cart.AddToCartController;
import hexagon.shop.adapter.in.rest.cart.EmptyCartController;
import hexagon.shop.adapter.in.rest.cart.GetCartController;
import hexagon.shop.adapter.in.rest.product.FindProductsController;
import hexagon.shop.adapter.out.persistence.inmemory.InMemoryCartRepository;
import hexagon.shop.adapter.out.persistence.inmemory.InMemoryProductRepository;
import hexagon.shop.application.port.out.persistence.CartRepository;
import hexagon.shop.application.port.out.persistence.ProductRepository;
import hexagon.shop.application.service.cart.AddToCartService;
import hexagon.shop.application.service.cart.EmptyCartService;
import hexagon.shop.application.service.cart.GetCartService;
import hexagon.shop.application.service.product.FindProductsService;
import jakarta.ws.rs.core.Application;
import java.util.Set;

public class RestEasyUndertowShopApplication extends Application {

  private CartRepository cartRepository;
  private ProductRepository productRepository;

  @Override
  public Set<Object> getSingletons() {
    initializePersistenceAdapters();
    return Set.of(
        addToCartController(),
        getCartController(),
        emptyCartController(),
        findProductsController());
  }

  private void initializePersistenceAdapters() {
    cartRepository = new InMemoryCartRepository();
    productRepository = new InMemoryProductRepository();
  }

  private AddToCartController addToCartController() {
    var addToCartUseCase = new AddToCartService(cartRepository, productRepository);
    return new AddToCartController(addToCartUseCase);
  }

  private GetCartController getCartController() {
    var getCartUseCase = new GetCartService(cartRepository);
    return new GetCartController(getCartUseCase);
  }

  private EmptyCartController emptyCartController() {
    var emptyCartUseCase = new EmptyCartService(cartRepository);
    return new EmptyCartController(emptyCartUseCase);
  }

  private FindProductsController findProductsController() {
    var findProductUseCase = new FindProductsService(productRepository);
    return new FindProductsController(findProductUseCase);
  }
}
