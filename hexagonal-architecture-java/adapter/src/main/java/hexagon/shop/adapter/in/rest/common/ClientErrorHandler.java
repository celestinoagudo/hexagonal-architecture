package hexagon.shop.adapter.in.rest.common;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ClientErrorHandler {
  @ExceptionHandler(ClientErrorException.class)
  public ResponseEntity<ErrorEntity> handleProductNotFoundException(
      final ClientErrorException clientErrorException) {
    return clientErrorException.getResponse();
  }
}
