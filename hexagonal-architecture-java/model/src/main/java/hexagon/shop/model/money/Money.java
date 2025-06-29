package hexagon.shop.model.money;

import static java.util.Objects.requireNonNull;

import java.math.BigDecimal;
import java.util.Currency;

public record Money(Currency currency, BigDecimal amount) {

  public Money {
    requireNonNull(currency, "'currency' must not be null");
    requireNonNull(amount, "'amount' must not be null");
    if (amount.scale() > currency.getDefaultFractionDigits()) {
      throw new IllegalArgumentException(
          ("Scale of amount %s is greater "
                  + "than the number of fraction digits used with currency %s")
              .formatted(amount, currency));
    }
  }

  public static Money of(final Currency currency, final int mayor, final int minor) {
    var scale = currency.getDefaultFractionDigits();
    return new Money(currency, BigDecimal.valueOf(mayor).add(BigDecimal.valueOf(minor, scale)));
  }

  public Money add(final Money augend) {
    if (!this.currency.equals(augend.currency())) {
      throw new IllegalArgumentException(
          "Currency %s of augend does not match this money's currency %s"
              .formatted(augend.currency(), this.currency));
    }
    return new Money(currency, amount.add(augend.amount()));
  }

  public Money multiply(int multiplicand) {
    return new Money(currency, amount.multiply(BigDecimal.valueOf(multiplicand)));
  }
}
