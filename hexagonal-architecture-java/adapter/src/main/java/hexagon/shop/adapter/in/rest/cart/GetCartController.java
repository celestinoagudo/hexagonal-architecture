package hexagon.shop.adapter.in.rest.cart;

import static hexagon.shop.adapter.in.rest.common.CustomerIdParser.parseCustomerId;

import hexagon.shop.application.port.in.cart.GetCartUseCase;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/carts")
@Produces(MediaType.APPLICATION_JSON)
public class GetCartController {

  private final GetCartUseCase getCartUseCase;

  public GetCartController(final GetCartUseCase getCartUseCase) {
    this.getCartUseCase = getCartUseCase;
  }

  @GET
  @Path("/{customerId}")
  public CartWebModel getCart(@PathParam("customerId") final String customerIdAsString) {
    var customerId = parseCustomerId(customerIdAsString);
    var cart = getCartUseCase.getCart(customerId);
    return CartWebModel.fromDomainModel(cart);
  }
}
