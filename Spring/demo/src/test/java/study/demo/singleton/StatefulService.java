package study.demo.singleton;

public class StatefulService {

    private int price;

    public int order(String name, int price){
        System.out.println("name = " + name + " Price = " + price);
//        this.price = price; // 문제포인트
        return price;
    }

    public int getPrice(){
        return price;
    }
}
