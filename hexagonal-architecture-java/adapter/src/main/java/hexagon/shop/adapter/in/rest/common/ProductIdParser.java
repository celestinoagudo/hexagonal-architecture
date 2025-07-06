package hexagon.shop.adapter.in.rest.common;

import static hexagon.shop.adapter.in.rest.common.ControllerCommons.clientErrorException;

import hexagon.shop.model.product.ProductId;
import jakarta.ws.rs.core.Response;

public final class ProductIdParser {
  private ProductIdParser() {}

  public static ProductId parseProductId(String string) {
    if (string == null) {
      throw clientErrorException(Response.Status.BAD_REQUEST, "Missing 'productId'");
    }

    try {
      return new ProductId(string);
    } catch (IllegalArgumentException e) {
      throw clientErrorException(Response.Status.BAD_REQUEST, "Invalid 'productId'");
    }
  }
}
