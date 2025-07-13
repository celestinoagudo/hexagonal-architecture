package hexagon.shop.adapter.out.persistence.jpa;

import hexagon.shop.model.money.Money;
import hexagon.shop.model.product.Product;
import hexagon.shop.model.product.ProductId;
import java.util.Currency;
import java.util.List;
import java.util.Optional;

final class ProductMapper {
  private ProductMapper() {}

  static ProductJpaEntity toJpaEntity(final Product product) {
    return ProductJpaEntity.builder()
        .id(product.id().value())
        .name(product.name())
        .description(product.description())
        .priceCurrency(product.price().currency().getCurrencyCode())
        .priceAmount(product.price().amount())
        .itemsInStock(product.itemsInStock())
        .build();
  }

  static Optional<Product> toModelEntityOptional(final ProductJpaEntity jpaEntity) {
    return Optional.ofNullable(jpaEntity).map(ProductMapper::toModelEntity);
  }

  static Product toModelEntity(final ProductJpaEntity jpaEntity) {
    return new Product(
        new ProductId(jpaEntity.getId()),
        jpaEntity.getName(),
        jpaEntity.getDescription(),
        new Money(Currency.getInstance(jpaEntity.getPriceCurrency()), jpaEntity.getPriceAmount()),
        jpaEntity.getItemsInStock());
  }

  static List<Product> toModelEntities(final List<ProductJpaEntity> jpaEntities) {
    return jpaEntities.stream().map(ProductMapper::toModelEntity).toList();
  }
}
