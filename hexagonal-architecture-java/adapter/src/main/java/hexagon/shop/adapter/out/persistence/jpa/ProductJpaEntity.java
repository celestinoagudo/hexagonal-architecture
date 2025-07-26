package hexagon.shop.adapter.out.persistence.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.*;

@Entity
@Table(name = "Product")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductJpaEntity {
  @Id private String id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String description;

  @Column(nullable = false)
  private String priceCurrency;

  @Column(nullable = false)
  private BigDecimal priceAmount;

  private int itemsInStock;
}
