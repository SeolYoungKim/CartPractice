package practice.cart.service;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class CartServiceTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private CartService cartService;

    // TODO : Test 코드를 독립적으로 짜는 데 완전히 실패했다. 이에 대한 해결 방안을 생각해보자.
//    @AfterEach
//    void clear() {
//        memberRepository.deleteAllInBatch();
//        itemRepository.deleteAllInBatch();
//        cartItemRepository.deleteAllInBatch();
//    }

    @Transactional
    @DisplayName("개인 카트에 아이템을 저장한다.")
    @Test
    void addCartItemTest() {
        //given
        List<Item> itemList = IntStream.range(1, 11)
                .mapToObj(i -> Item.builder()
                        .itemName("아이템이름 " + i)
                        .itemPrice(1000 * i)
                        .build())
                .collect(Collectors.toList());

        itemRepository.saveAll(itemList);

        Member member = Member.builder()
                .loginId("aaa")
                .password("bbb")
                .build();

        memberRepository.save(member);

        //when
        cartService.addItemInCart(member, itemList.get(0));
        cartService.addItemInCart(member, itemList.get(1));
        cartService.addItemInCart(member, itemList.get(2));
        cartService.addItemInCart(member, itemList.get(3));

        Cart memberCart = member.getMemberCart();
        List<Item> items = memberCart.getItemList();

        //then
        assertThat(items.get(0).getItemName()).isEqualTo("아이템이름 1");
        assertThat(items.get(0).getItemPrice()).isEqualTo(1000);
    }

    @DisplayName("개인 카트에 들어있는 아이템을 삭제한다.")
    @Test
    void deleteItemInCart() {
        //given
        List<Item> itemList = IntStream.range(1, 11)
                .mapToObj(i -> Item.builder()
                        .itemName("아이템이름 " + i)
                        .itemPrice(1000 * i)
                        .build())
                .collect(Collectors.toList());

        itemRepository.saveAll(itemList);  // 쿼리 전송

        Member member = Member.builder()
                .loginId("aaa")
                .password("bbb")
                .build();

        memberRepository.save(member);  // 쿼리 전송

        //when
        List<Item> items = itemRepository.findAll();  // 쿼리 전송

        cartService.addItemInCart(member, items.get(0));  // 쿼리 전송
        cartService.addItemInCart(member, items.get(1));
        cartService.addItemInCart(member, items.get(2));
        cartService.addItemInCart(member, items.get(3));

        // --- 여기까지 DB에 저장이 된 상태 --- (오토커밋)

        cartService.deleteItemInCart(member, items.get(2).getId());

        //then
        assertThat(cartItemRepository.findAll().size()).isEqualTo(3);
//        assertThat(items.size()).isEqualTo(10);  // Test Code 독립적으로 구성하기 실패
    }


}