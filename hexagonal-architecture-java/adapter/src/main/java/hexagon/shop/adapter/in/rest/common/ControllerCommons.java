package hexagon.shop.adapter.in.rest.common;

import jakarta.ws.rs.ClientErrorException;
import jakarta.ws.rs.core.Response;

public final class ControllerCommons {
  private ControllerCommons() {}

  public static ClientErrorException clientErrorException(
      final Response.Status status, final String message) {
    return new ClientErrorException(errorResponse(status, message));
  }

  public static Response errorResponse(final Response.Status status, final String message) {
    var errorEntity = new ErrorEntity(status.getStatusCode(), message);
    return Response.status(status).entity(errorEntity).build();
  }
}
