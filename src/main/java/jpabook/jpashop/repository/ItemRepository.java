package jpabook.jpashop.repository;

import jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    //item은 jpa에 저장하기 전까지 id값이 없다 -> 완전히 새로 생성하는 객체다.
    //아이템 값이 있다면 이미 DB에 등록되 있다는 뜻임.
    public void save(Item item){
        if(item.getId()== null){
            em.persist(item);
        }else {
            em.merge(item);
        }
    }

    public Item findOne(Long id){
        return em.find(Item.class, id);
    }

    public List<Item> findAll(){
        return em.createQuery("select i from Item i", Item.class)
                .getResultList();
    }
}
