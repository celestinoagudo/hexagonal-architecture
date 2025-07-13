package hexagon.shop.adapter.out.persistence.jpa;

import hexagon.shop.application.port.out.persistence.CartRepository;
import hexagon.shop.model.cart.Cart;
import hexagon.shop.model.customer.CustomerId;
import jakarta.persistence.EntityManagerFactory;
import java.util.Objects;
import java.util.Optional;

public class JpaCartRepository implements CartRepository {

  private final EntityManagerFactory entityManagerFactory;

  public JpaCartRepository(final EntityManagerFactory entityManagerFactory) {
    this.entityManagerFactory = entityManagerFactory;
  }

  @Override
  public void save(final Cart cart) {
    try (var entityManager = entityManagerFactory.createEntityManager()) {
      entityManager.getTransaction().begin();
      entityManager.merge(CartMapper.toJpaEntity(cart));
      entityManager.getTransaction().commit();
    }
  }

  @Override
  public Optional<Cart> findByCustomerId(final CustomerId customerId) {
    try (var entityManager = entityManagerFactory.createEntityManager()) {
      var cartJpaEntity = entityManager.find(CartJpaEntity.class, customerId.value());
      return CartMapper.toModelEntityOptional(cartJpaEntity);
    }
  }

  @Override
  public void deleteById(final CustomerId customerId) {
    try (var entityManager = entityManagerFactory.createEntityManager()) {
      entityManager.getTransaction().begin();
      var cartJpaEntity = entityManager.find(CartJpaEntity.class, customerId.value());
      if (Objects.nonNull(cartJpaEntity)) entityManager.remove(cartJpaEntity);
      entityManager.getTransaction().commit();
    }
  }
}
