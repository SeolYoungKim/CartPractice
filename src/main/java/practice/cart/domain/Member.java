package practice.cart.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "login_id")
    private String loginId;

    @Column(name = "password")
    private String password;

    // member에서 cart를 조회?
    @OneToOne(mappedBy = "member", cascade = CascadeType.PERSIST)
    private final Cart cart = new Cart(this);  // TODO: 해당 코드가 이쁜 코드가 맞는가?는 고민해봅시다.

    @Builder
    public Member(String loginId, String password) {
        this.loginId = loginId;
        this.password = password;
    }
}
