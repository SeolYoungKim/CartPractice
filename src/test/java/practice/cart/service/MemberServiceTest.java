package practice.cart.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import practice.cart.domain.Member;
import practice.cart.repository.CartRepository;
import practice.cart.repository.MemberRepository;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberServiceTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private MemberService memberService;


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

}