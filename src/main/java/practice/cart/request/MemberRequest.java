package practice.cart.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class MemberRequest {

    @NotBlank
    private String loginId;

    @NotBlank
    private String passWord;

    @Builder
    public MemberRequest(String loginId, String passWord) {
        this.loginId = loginId;
        this.passWord = passWord;
    }
}
