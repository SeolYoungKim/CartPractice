package practice.cart.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import practice.cart.domain.Cart;
import practice.cart.domain.Item;
import practice.cart.domain.Member;
import practice.cart.repository.CartRepository;
import practice.cart.repository.ItemRepository;
import practice.cart.repository.MemberRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberServiceTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private MemberService memberService;

    @Autowired
    private CartService cartService;

    @BeforeEach
    void clear() {
        memberRepository.deleteAllInBatch();
        itemRepository.deleteAllInBatch();
    }

    @Transactional // 아래의 테스트와 트랜잭션 환경을 분리해준다.
    @DisplayName("멤버가 저장되고, 카트가 자동추가 된다. 멤버에서 카트를 조회할 수 있다.")
    @Test
    void saveTest() {
        //given
        Member member = Member.builder()
                .loginId("id123")
                .password("pw123")
                .build();

        //when
        memberRepository.save(member);

        //then
        assertThat(cartRepository.findAll().size()).isEqualTo(1);
    }

    @DisplayName("멤버를 삭제하여도 Item은 삭제되지 않는다.")
    @Test
    void deleteMemberTest() {
        //given
        List<Item> itemList = IntStream.range(1, 11)
                .mapToObj(i -> Item.builder()
                        .itemName("아이템이름 " + i)
                        .itemPrice(1000 * i)
                        .build())
                .collect(Collectors.toList());

        itemRepository.saveAll(itemList);

        Member member = Member.builder()
                .loginId("id123")
                .password("pw123")
                .build();

        memberRepository.save(member);

//        when
        List<Item> items = itemRepository.findAll();

        cartService.addItemInCart(member, items.get(0));
        cartService.addItemInCart(member, items.get(1));
        cartService.addItemInCart(member, items.get(2));
        cartService.addItemInCart(member, items.get(3));

        Cart memberCart = member.getMemberCart();
        List<Item> itemLists = memberCart.getItemList();

        memberService.deleteMember(member.getId());

        //then
        assertThat(itemLists.get(0)).isNotNull();
        assertThat(itemLists.get(0).getItemName()).isEqualTo("아이템이름 1");
        assertThat(itemLists.get(0).getItemPrice()).isEqualTo(1000);
    }
}