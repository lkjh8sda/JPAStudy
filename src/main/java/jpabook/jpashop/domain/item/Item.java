package jpabook.jpashop.domain.item;

import jpabook.jpashop.domain.Category;
import jpabook.jpashop.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
//setter 빼고 stockquantity를 변경시킬 일이 있으면 내부에 있는 비즈니스 로직 매서드를 통해서 변경시키면 됨
@Getter@Setter
//구현체 따로 만들어 줄거야
//상속관계 매핑할 때 부모클래스에서 상속관계 전략을 지정해주어야함
//SINGLE_TABLE: 한 테이블에 다 때려박는거
//Table_per_class: 테이블 마다 나옴
//joined: 가장 정교화된 스타일
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
//구분 될 때 book이면 어떻게 할건가?
@DiscriminatorColumn(name = "dtype")
public abstract class Item {

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categoryList = new ArrayList<Category>();

    //보통 개발할때 아이템 서비스에서 스톡퀀티티가져와서 값을 더해서 넣고 값을 만들어 마지막으로 setStockQuantity를 만들어 넣었을 것임,
    // 그런데 객체지향 관점에서 데이터를 가지고 있는 곳에 비즈니스 로직이 있는게 좋다.

    //비즈니스 로직
        //재고 증가
    public void addStock(int quantity){
        this.stockQuantity += quantity;
    }
        //재고 감소
    public void removeStock(int quantity){
        int restStock = this.stockQuantity-quantity;
        if(restStock < 0){
            throw new NotEnoughStockException("need more stock");
        }
        this.stockQuantity = restStock;
    }
}
