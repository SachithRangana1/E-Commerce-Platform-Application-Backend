package lk.ecommerce.app.repository;

import lk.ecommerce.app.entity.Cart;
import lk.ecommerce.app.entity.Product;
import lk.ecommerce.app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    @Query("SELECT c FROM Cart c JOIN c.user u JOIN c.productList p WHERE u.id = :userId AND p.id = :productId")
    Optional<Cart> findCartById(@Param("userId") Long userId, @Param("productId") Long productId);

    void delete(Cart cart);

    @Modifying
    @Transactional
    @Query("DELETE FROM Cart c WHERE c.user.id = :userId AND c.id IN (SELECT c.id FROM c.productList p WHERE p.id = :productId)")
    void deleteCartById(@Param("userId") Long userId, @Param("productId") Long productId);

}
