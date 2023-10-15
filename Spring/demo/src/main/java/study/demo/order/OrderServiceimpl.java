package study.demo.order;

import study.demo.discount.DiscountPolicy;
import study.demo.discount.FiixDiscountPolicy;
import study.demo.discount.RateDiscountPolicy;
import study.demo.member.Member;
import study.demo.member.MemberRepository;
import study.demo.member.MemoryMemberRepository;

public class OrderServiceimpl implements OrderService{

    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;

    public OrderServiceimpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }


//    private DiscountPolicy discountPolicy;

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findByid(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }
}
