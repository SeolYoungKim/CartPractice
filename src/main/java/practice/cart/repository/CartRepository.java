package practice.cart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import practice.cart.domain.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
