package hexagon.shop.adapter.out.persistence.jpa;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "CartLineItem")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartLineItemJpaEntity {

  @Id @GeneratedValue private Integer id;

  @ManyToOne private CartJpaEntity cart;

  @ManyToOne private ProductJpaEntity product;

  private int quantity;
}
