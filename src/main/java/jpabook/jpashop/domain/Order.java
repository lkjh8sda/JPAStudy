package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
//DB에 들어가는 테이블명
@Table(name = "orders")
@Getter@Setter
public class Order {

    @Id@GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne
    //order랑 member는 다 대 원 관계
    //매핑을 뭘로할꺼냐    이게 FK됨
    @JoinColumn(name = "member_id")
    private Member member;

    //  order를 영속화 할때 딜리버리도 한꺼번에 영속화함
    @OneToOne(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;
//
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

//    persist(orderItemA)
//    persist(orderItemB)
//    persist(orderItemC)
//    persist(order)
//    보통 엔티티 하나씩 영속화하고 마지막에 오더를 영속화한다.
// CascadeType all을 하면 엔티티를 한꺼번에 영속화 하고 지울 때도 한꺼번에 지워준다
//    persist(order)


    private LocalDateTime orderDate; //주문시간

    @Enumerated(EnumType.STRING)
    private OrderStatus status; //주문 상태 [order, cancel]

    //연관 관계 메서드: 양방향 연관관계 세팅할 때 양쪽에다 값을 세팅해줄 필요가 있음
    public void setMember(Member member){
        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem){
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery){
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    //생성 메서드
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems){
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);

        for(OrderItem orderItem : orderItems){
            order.addOrderItem(orderItem);
        }
        order.setStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());
        return order;
    }


    //비즈니스 로직
    //    주문 취소
    public void cancel(){
        if(delivery.getStatus() == DeliveryStatus.comp){
            throw new IllegalStateException("이미 배송완료된 상품은 취소가 불가능합니다.");
        }
        this.setStatus(OrderStatus.CANCEL);

        //오더 하나에 상품 두개 주문하면 오더 아이탬 두 개 생김 각각에 캔슬 날려줘야됨
        for(OrderItem orderItem : orderItems){
            orderItem.cancel();
        }
    }

    //조회 로직
    //전체 주문 가격
    public int getTotalPrice(){
        int totalPrice = 0;
        for(OrderItem orderItem : orderItems){
            totalPrice += orderItem.getTotalPrice();
        }
        return totalPrice;
    }
}
