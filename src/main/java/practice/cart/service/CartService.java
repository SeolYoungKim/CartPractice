package practice.cart.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import practice.cart.domain.Cart;
import practice.cart.domain.CartItem;
import practice.cart.domain.Item;
import practice.cart.domain.Member;
import practice.cart.repository.CartItemRepository;
import practice.cart.repository.CartRepository;
import practice.cart.repository.ItemRepository;
import practice.cart.repository.MemberRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {

    // 1. Cart_Item에 Cart를 지정한다
    // 2. Cart_Item에 Item을 지정하는데, 특정 Cart가 지정된 곳에 아이템이 담길 수 있도록 해야한다.
    // 3. Member가 Item을 장바구니에 담으면, Member가 가진 장바구니의 id를 조회하고, 해당 장바구니에 아이템이 담길 수 있도록 한다.

    private final MemberRepository memberRepository;
    private final CartRepository cartRepository;
    private final ItemRepository itemRepository;
    private final CartItemRepository cartItemRepository;

    //TODO : 체크한 아이템들을 리스트화 해서 리스트로 한번에 받아서 처리하는 로직을 구성하는 게 좋을 것 같다.
    public void addItemInCart(Member member, Item item) {
        Cart cart = member.getMemberCart();  // member의 cart를 조회한다.

        CartItem cartItem = CartItem.builder()
                .cart(cart)
                .item(item)
                .build();

        cartItemRepository.save(cartItem);
        cart.addCartItems(cartItem);
    }

//    @Transactional  // TODO: 왜 오늘(7/26)은 갑자기 트랜잭셔널이 없어도 잘될까...........알다가도모르겠네......
    public void deleteItemInCart(Member member, Long itemId) {
        Cart memberCart = member.getMemberCart();

        // TODO: CartItemRepositoryCustom과 +Impl을 구현해서 ItemId로 CartItem을 찾는 로직을 짜도 된다. (QueryDSL)
        CartItem findCartItem = memberCart.getCartItems().stream()
                .filter(cartItem -> cartItem.getItem().getId().equals(itemId))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("아이템이 없습니다."));

        cartItemRepository.delete(findCartItem);  // 왜 삭제가 안되지 -> @Transactional 추가하니까 삭제가 됨.. 이유는?
    }
}
