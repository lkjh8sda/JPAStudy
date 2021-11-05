package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import org.springframework.stereotype.Service;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter@Setter
public class Delivery {

    @Id
    @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    //1:1관계에서 생기는 딜레마, 1:x는 x에 fk를 두는데, 1:1은 어디에 두어야 하는가
    @OneToOne(mappedBy = "delivery", fetch = LAZY)
    private Order order;

    @Embedded
    private Address address;

    // enum 쓸 때 주의할점, 디폴트가 ordinal임 1,2,3,4 숫자로 컬럼이 들어감 가령 ready면 1 comp면 2, 이 때 문제가 중간에 다른 상태가 생기면(ready,xxx ,comp) 디비로 조회할 때 xxx로 나옴
    //따라서 꼭 String으로 쓸것
    @Enumerated(EnumType.STRING)
    private DeliveryStatus status; //ready, comp
}
