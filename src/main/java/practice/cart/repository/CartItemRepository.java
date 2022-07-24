package practice.cart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import practice.cart.domain.Cart;
import practice.cart.domain.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
