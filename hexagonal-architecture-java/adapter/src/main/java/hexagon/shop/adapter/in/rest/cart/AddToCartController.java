package hexagon.shop.adapter.in.rest.cart;

import static hexagon.shop.adapter.in.rest.common.ControllerCommons.clientErrorException;
import static hexagon.shop.adapter.in.rest.common.CustomerIdParser.parseCustomerId;
import static hexagon.shop.adapter.in.rest.common.ProductIdParser.parseProductId;

import hexagon.shop.application.port.in.cart.AddToCartUseCase;
import hexagon.shop.application.port.in.cart.ProductNotFoundException;
import hexagon.shop.model.cart.NotEnoughItemsInStockException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/carts")
@Produces(MediaType.APPLICATION_JSON)
public class AddToCartController {
  private final AddToCartUseCase addToCartUseCase;

  public AddToCartController(final AddToCartUseCase addToCartUseCase) {
    this.addToCartUseCase = addToCartUseCase;
  }

  @POST
  @Path("/{customerId}/line-items")
  public CartWebModel addLineItem(
      @PathParam("customerId") final String customerIdAsString,
      @QueryParam("productId") final String productIdAsString,
      @QueryParam("quantity") final int quantity) {
    var customerId = parseCustomerId(customerIdAsString);
    var productId = parseProductId(productIdAsString);
    try {
      var cart = addToCartUseCase.addToCart(customerId, productId, quantity);
      return CartWebModel.fromDomainModel(cart);
    } catch (final ProductNotFoundException productNotFoundException) {
      throw clientErrorException(
          Response.Status.BAD_REQUEST, "The requested product does not exist");
    } catch (final NotEnoughItemsInStockException notEnoughItemsInStockException) {
      throw clientErrorException(
          Response.Status.BAD_REQUEST,
          "Only %d items in stock".formatted(notEnoughItemsInStockException.itemsInStock()));
    }
  }
}
