package study.demo.discount;

import study.demo.member.Grade;
import study.demo.member.Member;

public class FiixDiscountPolicy implements DiscountPolicy{

    private int discountFixAmount = 1000; // 1000원 할인
    @Override
    public int discount(Member member, int price) {
        if (member.getGrade() == Grade.VIP){
            return discountFixAmount;
        }
        else{
            return 0;
        }

    }
}
