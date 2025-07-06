package hexagon.shop.adapter.in.rest.cart;

import hexagon.shop.adapter.in.rest.common.CustomerIdParser;
import hexagon.shop.application.port.in.cart.EmptyCartUseCase;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/carts")
@Produces(MediaType.APPLICATION_JSON)
public class EmptyCartController {

  private final EmptyCartUseCase emptyCartUseCase;

  public EmptyCartController(final EmptyCartUseCase emptyCartUseCase) {
    this.emptyCartUseCase = emptyCartUseCase;
  }

  @DELETE
  @Path("/{customerId}")
  public void deleteCart(@PathParam("customerId") final String customerIdAsString) {
    emptyCartUseCase.emptyCart(CustomerIdParser.parseCustomerId(customerIdAsString));
  }
}
