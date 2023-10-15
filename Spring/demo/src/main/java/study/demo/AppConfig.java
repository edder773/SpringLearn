package study.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import study.demo.discount.DiscountPolicy;
import study.demo.discount.FiixDiscountPolicy;
import study.demo.discount.RateDiscountPolicy;
import study.demo.member.MemberService;
import study.demo.member.MemberServiceImpl;
import study.demo.member.MemoryMemberRepository;
import study.demo.order.OrderService;
import study.demo.order.OrderServiceimpl;

@Configuration
public class AppConfig {
    @Bean
    public MemberService memberService(){
        return new MemberServiceImpl(memberRepository());
    }
    @Bean
    public MemoryMemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }
    @Bean
    public OrderService orderService(){
        return new OrderServiceimpl(memberRepository(), new FiixDiscountPolicy());
    }
    @Bean
    public DiscountPolicy discountPolicy() {
//        return new FiixDiscountPolicy();
        return new RateDiscountPolicy();
    }
}
