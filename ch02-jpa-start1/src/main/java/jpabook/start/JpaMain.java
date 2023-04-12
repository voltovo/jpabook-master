package jpabook.start;

import javax.persistence.*;
import java.util.List;

/**
 * @author suman
 */
public class JpaMain {

    public static void main(String[] args) {

        //엔티티 매니저 팩토리 생성
        //persistence.xml에서 persistence-unit을 찾으므로 jpabook
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");
        EntityManager em = emf.createEntityManager(); //엔티티 매니저 생성

        EntityTransaction tx = em.getTransaction(); //트랜잭션 기능 획득

        try {


            tx.begin(); //트랜잭션 시작
            logic(em);  //비즈니스 로직
            tx.commit();//트랜잭션 커밋
            logic2(em);  //비즈니스 로직

        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback(); //트랜잭션 롤백
        } finally {
            em.close(); //엔티티 매니저 종료
        }

        emf.close(); //엔티티 매니저 팩토리 종료
    }

    public static void logic(EntityManager em) {

        //회원 엔티티 생성, 비영속 상태
        Member member = new Member();
        member.setId("memberA");
        member.setUsername("회원A");

        // 영속 상태
        em.persist(member);

    }

    public static void logic2(EntityManager em){
        // 조회
        Member findMember = em.find(Member.class, "memberA");
        System.out.println("findMember.name = " + findMember.getUsername());

        // 영속성 컨텍스트 초기화
        em.clear();

        findMember.setUsername("changeName");
    }
}
