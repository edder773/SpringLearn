package study.demo.beanfind;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import study.demo.AppConfig;
import study.demo.member.MemberRepository;
import study.demo.member.MemoryMemberRepository;

import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ApplicationContextSameBeanfindTest {
    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(SameBeanConfig.class);

    @Test
    @DisplayName("타입 조회시 같은 타입 2개면 중복오류")
    void findBeanByTypeDuplicate(){
//       MemberRepository bean = ac.getBean(MemberRepository.class);
       assertThrows(NoUniqueBeanDefinitionException.class, () -> ac.getBean(MemberRepository.class));
    }

    @Test
    @DisplayName("타입으로 조회소 같은 타입 둘이면 빈 이름으로 지정")
    void findBeanByName(){
        MemberRepository memberRepository = ac.getBean("memberRepository1", MemberRepository.class);
        assertThat(memberRepository).isInstanceOf(MemberRepository.class);
    }

    @Test
    @DisplayName("특정 타입 모두 조회")
    void findAllBeanByType(){
        Map<String, MemberRepository> beansOFType = ac.getBeansOfType(MemberRepository.class);
        for (String key : beansOFType.keySet()){
            System.out.println("key= " + key + " value = " + beansOFType.get(key));
        }
        System.out.println("beansOfType = " + beansOFType);
        assertThat(beansOFType.size()).isEqualTo(2);
    }


    @Configuration
    static class SameBeanConfig{
        @Bean
        public MemberRepository memberRepository1() {
            return new MemoryMemberRepository();
        }
        @Bean
        public MemberRepository memberRepository2() {
            return new MemoryMemberRepository();
        }
    }
}
