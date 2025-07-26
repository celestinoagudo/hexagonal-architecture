package hexagon.shop.adapter.in.rest.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public final class ControllerCommons {
  private ControllerCommons() {}

  public static ClientErrorException clientErrorException(
      final HttpStatus httpStatus, final String message) {
    return new ClientErrorException(errorResponse(httpStatus, message));
  }

  public static ResponseEntity<ErrorEntity> errorResponse(
      final HttpStatus httpStatus, final String message) {
    var errorEntity = new ErrorEntity(httpStatus.value(), message);
    return ResponseEntity.status(httpStatus.value()).body(errorEntity);
  }
}
