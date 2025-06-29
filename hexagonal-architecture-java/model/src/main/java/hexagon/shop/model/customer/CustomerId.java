package hexagon.shop.model.customer;

public record CustomerId(int value) {
  /*
   * Why do we need such a wrapper? Can’t we store the customer number directly as an int value in Cart?
   * We could – but that would be a code smell called “primitive obsession.” The wrapper has two advantages:
   * - We can make sure that the value is valid. In the example, the customer number must be a positive number.
   * - We can pass the customer number to methods in a type-safe way. If the customer number were an int primitive,
   * we could accidentally swap parameters in a method with multiple int parameters.
   */
  public CustomerId {
    if (value < 1) {
      throw new IllegalArgumentException("'value' must be a positive Integer!");
    }
  }
}
