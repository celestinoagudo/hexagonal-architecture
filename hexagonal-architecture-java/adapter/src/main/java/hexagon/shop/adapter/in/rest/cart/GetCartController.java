package hexagon.shop.adapter.in.rest.cart;

import static hexagon.shop.adapter.in.rest.common.CustomerIdParser.parseCustomerId;

import hexagon.shop.application.port.in.cart.GetCartUseCase;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/carts")
public class GetCartController {

  private final GetCartUseCase getCartUseCase;

  public GetCartController(final GetCartUseCase getCartUseCase) {
    this.getCartUseCase = getCartUseCase;
  }

  @GetMapping("/{customerId}")
  public CartWebModel getCart(@PathVariable("customerId") final String customerIdAsString) {
    var customerId = parseCustomerId(customerIdAsString);
    var cart = getCartUseCase.getCart(customerId);
    return CartWebModel.fromDomainModel(cart);
  }
}
