package hexagon.shop.model.cart;

import hexagon.shop.model.customer.CustomerId;
import hexagon.shop.model.money.Money;
import hexagon.shop.model.product.Product;
import hexagon.shop.model.product.ProductId;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

@Accessors(fluent = true)
@RequiredArgsConstructor
public class Cart {
  @Getter private final CustomerId customerId;
  private final Map<ProductId, CartLineItem> lineItems = new LinkedHashMap<>();

  public void addProduct(final Product product, final int quantity)
      throws NotEnoughItemsInStockException {
    lineItems
        .computeIfAbsent(product.id(), ignored -> new CartLineItem(product))
        .increaseQuantityBy(quantity, product.itemsInStock());
  }

  public List<CartLineItem> lineItems() {
    return List.copyOf(lineItems.values());
  }

  public int numberOfItems() {
    return lineItems.values().stream().mapToInt(CartLineItem::quantity).sum();
  }

  public Money subTotal() {
    return lineItems.values().stream().map(CartLineItem::subTotal).reduce(Money::add).orElse(null);
  }

  public void putProductIgnoringNotEnoughItemsInStock(final Product product, final int quantity) {
    lineItems.put(product.id(), new CartLineItem(product, quantity));
  }
}
