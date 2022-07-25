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
import static javax.persistence.FetchType.*;

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

    //TODO : cascade = ALL 덕분에 멤버를 지우면 모든게 지워지나, 이것 때문에 장바구니에서의 아이템 삭제가 되지 않는다..
    // 이는 CartService에 @Transactional을 붙여주니까 해결됐는데, 솔직히 왜 해결됐는지 모르겠다.
    @OneToMany(mappedBy = "cart", cascade = ALL, fetch = LAZY) // Cart가 삭제될 때, cartItem도 삭제된다.
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
