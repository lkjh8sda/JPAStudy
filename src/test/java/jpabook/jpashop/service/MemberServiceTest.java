package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
//JAP는 persist를 한다고 DB에 insert문이 안나간다. DB transaction이 커밋 될 때 플러쉬가 되면서 영속성 컨테스트에 있는 맴버 객체가 insert문이 만들어지면서 DB에 나간다.
//그러나 스프링 @트랜잭셔널은 기본적으로 커밋이 아니라 롤백을 해버림... 그래서 select는 나오는데 insert문이 안나옴 만약 커밋할라면 @Rollback(false)를 넣어줘야함, 혹은 em.flush()사용해라
@Transactional
public class MemberServiceTest {
    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    @Test
    public void 회원가입() throws Exception{
        //given
        Member member = new Member();
        member.setName("lee");
        //when
        Long savedId = memberService.join(member);

        //then
        Assert.assertEquals(member, memberRepository.findOne(savedId));
    }

    @Test
    public void 중복_회원_예외() throws Exception{
        //given
        Member member1 = new Member();
        member1.setName("kim");

        Member member2 = new Member();
        member2.setName("kim");
        //when
        memberService.join(member1);
        try{
            memberService.join(member2); //IllegalStateException 예외가 발생해야 한다. 따라서 캐치해주면 테스트 성공!
        }catch (IllegalStateException e){
            return;
        }
        //then
        fail("예외가 발생해야 한다.");
    }

}