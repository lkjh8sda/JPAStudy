package jpabook.jpashop;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;



@ExtendWith(SpringExtension.class)
@SpringBootTest
public class MemberRepositoryTest {
//    @Autowired
//    MemberRepository memberRepository;
//
//
//    //엔티티 매니져를 통한 모든 데이터 변경은 트랜잭션 안에서 이루어져야 한다.
//    //트랜잭셔널 어노테이션이 테스트 케이스에 있으면 테스트가 끝나면 데이터를 롤백해버림
//    @Test
//    @Transactional
//    @Rollback(value = false)
//    public void testMember() throws Exception{
//        //given
//        Member member = new Member();
//        member.setUsername("memberA");
//
//        //when
//        Long saveId = memberRepository.save(member);
//        Member findMember = memberRepository.find(saveId);
//
//        //then
//        Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
//        Assertions.assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
//        Assertions.assertThat(findMember).isEqualTo(member);
//        System.out.println("findMember == member: "+(findMember == member));
//    }
}