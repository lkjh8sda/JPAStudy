package jpabook.jpashop.domain;

import jpabook.jpashop.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter@Setter
public class Category {

    @Id@GeneratedValue
    @Column(name = "category_id")
    private Long id;

    private String name;

    //왜 실무에서 manytomany못쓰게하는가? 더 필드를 추가하거나 하는 것이 불가능 하다.
    //1:x x:1 관계로 풀어내는 중간 테이블을 매핑해줘야함
    @ManyToMany(fetch = LAZY)
    @JoinTable(name = "category_item",
            joinColumns = @JoinColumn(name="category_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id")
    )
    private List<Item> items = new ArrayList<>();


    //카테고리 계층구조 매핑
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name="parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private List<Category> child= new ArrayList<>();

    //양방향 메서드
    public void addChildCategory(Category child){
        this.child.add(child);
        child.setParent(this);
    }
}
