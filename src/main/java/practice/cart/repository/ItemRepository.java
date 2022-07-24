package practice.cart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import practice.cart.domain.Cart;
import practice.cart.domain.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
