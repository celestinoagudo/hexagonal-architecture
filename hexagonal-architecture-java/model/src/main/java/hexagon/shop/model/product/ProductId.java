package hexagon.shop.model.product;

import static java.util.Objects.requireNonNull;

import java.util.concurrent.ThreadLocalRandom;

public record ProductId(String value) {

  private static final String ALPHABET = "23456789ABCDEFGHJKLMNPQRSTUVWXYZ";
  private static final int PRODUCT_ID_LENGTH = 8;

  public ProductId {
    requireNonNull(value, "'value' must not be null");
    if (value.isEmpty()) {
      throw new IllegalArgumentException("'value' must not be empty");
    }
  }

  public static ProductId generateRandomProductId() {
    var threadLocalRandom = ThreadLocalRandom.current();
    var productIdAsArray = new char[PRODUCT_ID_LENGTH];
    for (int index = 0; index < PRODUCT_ID_LENGTH; ++index) {
      productIdAsArray[index] = ALPHABET.charAt(threadLocalRandom.nextInt(ALPHABET.length()));
    }
    return new ProductId(new String(productIdAsArray));
  }
}
