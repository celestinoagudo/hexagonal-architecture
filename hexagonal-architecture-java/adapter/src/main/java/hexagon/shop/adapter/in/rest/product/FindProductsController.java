package hexagon.shop.adapter.in.rest.product;

import static hexagon.shop.adapter.in.rest.common.ControllerCommons.clientErrorException;

import hexagon.shop.application.port.in.product.FindProductsUseCase;
import hexagon.shop.model.product.Product;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.Objects;

@Path("/products")
@Produces(MediaType.APPLICATION_JSON)
public class FindProductsController {

  private final FindProductsUseCase findProductsUseCase;

  public FindProductsController(final FindProductsUseCase findProductsUseCase) {
    this.findProductsUseCase = findProductsUseCase;
  }

  public List<ProductInListWebModel> findProducts(@QueryParam("query") final String query) {
    if (Objects.isNull(query))
      throw clientErrorException(Response.Status.BAD_REQUEST, "Missing 'query'");
    List<Product> products;
    try {
      products = findProductsUseCase.findByNameOrDescription(query);
    } catch (final IllegalArgumentException exception) {
      throw clientErrorException(Response.Status.BAD_REQUEST, "Invalid 'query'");
    }
    return products.stream().map(ProductInListWebModel::fromDomainModel).toList();
  }
}
