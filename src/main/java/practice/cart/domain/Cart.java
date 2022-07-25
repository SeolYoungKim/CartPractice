package practice.cart.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static javax.persistence.CascadeType.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = ALL)
    @JoinColumn(name = "member_id", unique = true)
    private Member member;

    @OneToMany(mappedBy = "cart", cascade = ALL) // Cart가 삭제될 때, cartItem도 삭제된다.
    private final List<CartItem> cartItems = new ArrayList<>();

    public Cart(Member member) {
        this.member = member;
    }

    // cart_item에 담긴 item을 카트에서 조회할 수 있도록 해보자.
    public void addCartItems(CartItem cartItem) {
        this.cartItems.add(cartItem);
    }

    public List<Item> getItemList() {
        return cartItems.stream()
                .map(CartItem::getItem)
                .collect(Collectors.toList());
    }
}
