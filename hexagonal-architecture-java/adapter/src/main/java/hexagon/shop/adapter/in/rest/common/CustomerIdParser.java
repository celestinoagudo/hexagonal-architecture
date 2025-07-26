package hexagon.shop.adapter.in.rest.common;

import static hexagon.shop.adapter.in.rest.common.ControllerCommons.clientErrorException;

import hexagon.shop.model.customer.CustomerId;
import org.springframework.http.HttpStatus;

public final class CustomerIdParser {
  private CustomerIdParser() {}

  public static CustomerId parseCustomerId(String string) {
    try {
      return new CustomerId(Integer.parseInt(string));
    } catch (IllegalArgumentException e) {
      throw clientErrorException(HttpStatus.BAD_REQUEST, "Invalid 'customerId'");
    }
  }
}
