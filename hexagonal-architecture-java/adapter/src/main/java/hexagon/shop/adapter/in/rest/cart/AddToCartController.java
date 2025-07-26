package hexagon.shop.adapter.in.rest.cart;

import static hexagon.shop.adapter.in.rest.common.ControllerCommons.clientErrorException;
import static hexagon.shop.adapter.in.rest.common.CustomerIdParser.parseCustomerId;
import static hexagon.shop.adapter.in.rest.common.ProductIdParser.parseProductId;

import hexagon.shop.application.port.in.cart.AddToCartUseCase;
import hexagon.shop.application.port.in.cart.ProductNotFoundException;
import hexagon.shop.model.cart.NotEnoughItemsInStockException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carts")
public class AddToCartController {
  private final AddToCartUseCase addToCartUseCase;

  public AddToCartController(final AddToCartUseCase addToCartUseCase) {
    this.addToCartUseCase = addToCartUseCase;
  }

  @PostMapping("/{customerId}/line-items")
  public CartWebModel addLineItem(
      @PathVariable("customerId") final String customerIdAsString,
      @RequestParam("productId") final String productIdAsString,
      @RequestParam("quantity") final int quantity) {
    var customerId = parseCustomerId(customerIdAsString);
    var productId = parseProductId(productIdAsString);
    try {
      var cart = addToCartUseCase.addToCart(customerId, productId, quantity);
      return CartWebModel.fromDomainModel(cart);
    } catch (final ProductNotFoundException productNotFoundException) {
      throw clientErrorException(HttpStatus.BAD_REQUEST, "The requested product does not exist");
    } catch (final NotEnoughItemsInStockException notEnoughItemsInStockException) {
      throw clientErrorException(
          HttpStatus.BAD_REQUEST,
          "Only %d items in stock".formatted(notEnoughItemsInStockException.itemsInStock()));
    }
  }
}
