package practice.cart.service;

import org.assertj.core.api.Assertions;
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

        List<Member> memberList = IntStream.range(1, 6)
                .mapToObj(i -> Member.builder()
                        .loginId("id" + i)
                        .password("pw" + i)
                        .build())
                .collect(Collectors.toList());

        memberRepository.saveAll(memberList);

        //when
        Member member = memberList.get(0);

        cartService.addItemInCart(member, itemList.get(0));
        cartService.addItemInCart(member, itemList.get(1));
        cartService.addItemInCart(member, itemList.get(2));
        cartService.addItemInCart(member, itemList.get(3));

        Cart memberCart = member.getMemberCart();
        List<Item> items = memberCart.getItemList();

        //then
        for (Item item : items) {
            System.out.println("item.getItemName() = " + item.getItemName());
        }
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

        itemRepository.saveAll(itemList);

        List<Member> memberList = IntStream.range(1, 6)
                .mapToObj(i -> Member.builder()
                        .loginId("id" + i)
                        .password("pw" + i)
                        .build())
                .collect(Collectors.toList());

        memberRepository.saveAll(memberList);

        //when
        Member member = memberList.get(0);

        cartService.addItemInCart(member, itemList.get(0));
        cartService.addItemInCart(member, itemList.get(1));
        cartService.addItemInCart(member, itemList.get(2));
        cartService.addItemInCart(member, itemList.get(3));

        Cart memberCart = member.getMemberCart();
        List<Item> items = memberCart.getItemList();

        cartService.deleteItemInCart(member, 2L);

        //then
        assertThat(cartItemRepository.findAll().size()).isEqualTo(3);
    }


}