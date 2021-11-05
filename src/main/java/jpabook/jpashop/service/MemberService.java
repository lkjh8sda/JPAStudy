package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
//데이터 변경은 기본적으로 트랜잭션 안에서 처리,
//readOnly true는 읽기만 됨, 데이터변경 안됨
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    //1,필드 주입: 테스트를 하거나 할 때 리포지토리를 엑세스 해서 바꿀 방법이 없다.
    //2,세터 주입: 스프링이 바로주입하지 않고 세터로 들어와 주입, 테스트 코드 작성시 가짜 리포지토리 주입가능
    //            누군가 set 메서드를 불러와서 수정할 수 있다.
    //3,생성자 주입: 가장 권장됨됨


    //회원가입
    // 디폴트가 false임,따로 설정한거는 우선권을 가져서 변경가능
    @Transactional
    public Long join(Member member){
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }
    //맴버A가 동시에 가입하면 즉 멀티쓰레드 상황에서 둘 다 가입될 수 있음, 최후의 방어라인으로 DB에 맴버 이름을 유니크 제약조건을 걸어주자
    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());

        if(!findMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }

    }
    //회원 전체 조회
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    //한건 조회
    public Member findOne(Long memberId){
        return memberRepository.findOne(memberId);
    }

}
