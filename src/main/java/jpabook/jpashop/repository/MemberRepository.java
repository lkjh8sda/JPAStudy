package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import org.springframework.stereotype.Repository;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;


@Repository
public class MemberRepository {

    @PersistenceContext
    private EntityManager em;


    //jpa에서 퍼시스트 하면 영속성 컨텍스트에 이 맴버 객체를 올림 이때 영속성 컨텍스트의 키는  Member 도메인에 id값 value는 generatedValue는 자동으로 넣어줌, DB에 들어가기 전에 값 들어간다
    public void save(Member member){
        em.persist(member);
    }

    public Member findOne(Long id){
        return em.find(Member.class, id);
    }

    public List<Member> findAll(){
        //                                      from의 대상이 테이블이 아니라 엔티티임
        return em.createQuery("select m from Member m",Member.class).getResultList();
    }

    public List<Member> findByName(String name){
        //                                                                 파라미터 바인딩, 조회 타입
        return em.createQuery("select m from Member m where m.name = :name",Member.class)
                .setParameter("name", name)
                .getResultList();
    }
}
