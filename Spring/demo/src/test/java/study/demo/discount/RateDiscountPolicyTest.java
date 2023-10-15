package study.demo.discount;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import study.demo.member.Grade;
import study.demo.member.Member;

import static org.junit.jupiter.api.Assertions.*;

class RateDiscountPolicyTest {
    RateDiscountPolicy discountPolicy = new RateDiscountPolicy();

    @Test
    @DisplayName("VIP는 10% 할인")
    void vip_o(){
        Member member = new Member(1L, "memberViP", Grade.VIP);
        int discount = discountPolicy.discount(member, 10000);
        Assertions.assertThat(discount).isEqualTo(1000);
    }

//    @Test
//    @DisplayName("VIP 노할인")
//    void vip_x(){
//        Member member = new Member(1L, "memberBASIC", Grade.BASIC);
//        int discount = discountPolicy.discount(member, 10000);
//        Assertions.assertThat(discount).isEqualTo(1000);
//    }
}