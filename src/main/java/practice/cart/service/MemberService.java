package practice.cart.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import practice.cart.domain.Cart;
import practice.cart.domain.Member;
import practice.cart.repository.CartRepository;
import practice.cart.repository.MemberRepository;
import practice.cart.request.MemberRequest;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final CartRepository cartRepository;

    public void addMember(MemberRequest memberRequest) {
        Member member = Member.builder()
                .loginId(memberRequest.getLoginId())
                .password(memberRequest.getPassWord())
                .build();

        memberRepository.save(member);
    }

    public void deleteMember(Long id) {

        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("없는 회원입니다."));

        memberRepository.delete(member);

    }
}
