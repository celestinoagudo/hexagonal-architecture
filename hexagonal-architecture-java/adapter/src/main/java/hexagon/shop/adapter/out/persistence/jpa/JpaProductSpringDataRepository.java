package hexagon.shop.adapter.out.persistence.jpa;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaProductSpringDataRepository extends JpaRepository<ProductJpaEntity, String> {
  @Query("SELECT p FROM ProductJpaEntity p " + "WHERE p.name like ?1 or p.description like ?1")
  List<ProductJpaEntity> findByNameOrDescriptionLike(String pattern);
}
