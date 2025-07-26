package hexagon.shop.adapter.out.persistence.jpa;

import hexagon.shop.application.port.out.persistence.CartRepository;
import hexagon.shop.model.cart.Cart;
import hexagon.shop.model.customer.CustomerId;
import jakarta.transaction.Transactional;
import java.util.Optional;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

@Repository
@ConditionalOnProperty(name = "persistence", havingValue = "mysql")
public class JpaCartRepository implements CartRepository {

  private final JpaCartSpringDataRepository cartSpringDataRepository;

  public JpaCartRepository(final JpaCartSpringDataRepository cartSpringDataRepository) {
    this.cartSpringDataRepository = cartSpringDataRepository;
  }

  @Transactional
  @Override
  public void save(final Cart cart) {
    cartSpringDataRepository.save(CartMapper.toJpaEntity(cart));
  }

  @Override
  @Transactional
  public Optional<Cart> findByCustomerId(final CustomerId customerId) {
    var cartJpaEntity = cartSpringDataRepository.findById(customerId.value());
    return cartJpaEntity.flatMap(CartMapper::toModelEntityOptional);
  }

  @Override
  @Transactional
  public void deleteById(final CustomerId customerId) {
    cartSpringDataRepository.deleteById(customerId.value());
  }
}
