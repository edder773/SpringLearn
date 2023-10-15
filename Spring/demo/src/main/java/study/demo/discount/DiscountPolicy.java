package study.demo.discount;

import study.demo.member.Member;

public interface DiscountPolicy {
    int discount(Member member, int price);
}
