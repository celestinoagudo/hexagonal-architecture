package hexagon.shop.adapter.out.persistence.jpa;

import jakarta.persistence.*;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Cart")
@Getter
@Setter
@Builder
public class CartJpaEntity {

  @Id private int customerId;

  @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<CartLineItemJpaEntity> lineItems;
}
