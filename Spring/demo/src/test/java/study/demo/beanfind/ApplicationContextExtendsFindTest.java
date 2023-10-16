package study.demo.beanfind;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import study.demo.discount.DiscountPolicy;
import study.demo.discount.FiixDiscountPolicy;
import study.demo.discount.RateDiscountPolicy;

import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ApplicationContextExtendsFindTest {

    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);

    @Test
    @DisplayName("부모 타입 중복 오류")
    void findBeanByParentTypeDuplicate() {
        assertThrows(NoUniqueBeanDefinitionException.class, () -> ac.getBean(DiscountPolicy.class));
    }

    @Test
    @DisplayName("부모 타입 빈 지정")
    void findBeanByParentTypeBeanName() {
        DiscountPolicy rateDiscountPolicy = ac.getBean("rateDiscountPolicy", DiscountPolicy.class);
        assertThat(rateDiscountPolicy).isInstanceOf(RateDiscountPolicy.class);
    }

    @Test
    @DisplayName("부모 타입으로 모두 조회")
    void findAllbeanByParrentType(){
        Map<String, DiscountPolicy> beansOftype = ac.getBeansOfType(DiscountPolicy.class);
        assertThat(beansOftype.size()).isEqualTo(2);
        for(String key : beansOftype.keySet()){
            System.out.println("key = " + key + " value = " + beansOftype.get(key));
        }
    }

    @Test
    @DisplayName("부모 타입으로 모두꺼내")
    void findAllbeanByObjectType(){
        Map<String, Object> beansOftype = ac.getBeansOfType(Object.class);
        for(String key : beansOftype.keySet()){
            System.out.println("key = " + key + " value = " + beansOftype.get(key));
        }
    }

    @Configuration
    static class TestConfig {
        @Bean
        public DiscountPolicy rateDiscountPolicy() {
            return new RateDiscountPolicy();
        }

        @Bean
        public DiscountPolicy fixDiscountPolicy() {
            return new FiixDiscountPolicy();
        }
    }
}
