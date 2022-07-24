package practice.cart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import practice.cart.domain.Cart;
import practice.cart.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
