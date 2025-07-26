package hexagon.shop.adapter.in.rest.cart;

import hexagon.shop.adapter.in.rest.common.CustomerIdParser;
import hexagon.shop.application.port.in.cart.EmptyCartUseCase;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/carts")
public class EmptyCartController {

  private final EmptyCartUseCase emptyCartUseCase;

  public EmptyCartController(final EmptyCartUseCase emptyCartUseCase) {
    this.emptyCartUseCase = emptyCartUseCase;
  }

  @DeleteMapping("/{customerId}")
  public void deleteCart(@PathVariable("customerId") final String customerIdAsString) {
    emptyCartUseCase.emptyCart(CustomerIdParser.parseCustomerId(customerIdAsString));
  }
}
