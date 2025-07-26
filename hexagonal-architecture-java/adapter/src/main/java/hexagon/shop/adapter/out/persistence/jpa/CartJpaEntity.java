package hexagon.shop.adapter.out.persistence.jpa;

import jakarta.persistence.*;
import java.util.List;
import lombok.*;

@Entity
@Table(name = "Cart")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartJpaEntity {

  @Id private int customerId;

  @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<CartLineItemJpaEntity> lineItems;
}
