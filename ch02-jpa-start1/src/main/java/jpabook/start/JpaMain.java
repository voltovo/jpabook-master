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

        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback(); //트랜잭션 롤백
        } finally {
            em.close(); //엔티티 매니저 종료
        }

        emf.close(); //엔티티 매니저 팩토리 종료
    }

    public static void logic(EntityManager em) {

        Member member = new Member();
        member.setId("member1");
        member.setUsername("suman");

        //등록
        em.persist(member);

        //조회
        Member findMember = em.find(Member.class, "member1");

        // 출력
        System.out.println("findMember.id = " + findMember.getId());

        Member member2 = new Member();
        member2.setId("member2");
        member2.setUsername("suman2");

        //등록
        em.persist(member2);

        //조회
        Member findMember2 = em.find(Member.class, "member2");

        // 출력
        System.out.println("findMember2.id = " + findMember2.getId());

        Member member3 = new Member();
        member3.setId("member3");
        member3.setUsername("suman3");

        //등록
        em.persist(member3);

        //조회
        Member findMember3 = em.find(Member.class, "member3");

        // 출력
        System.out.println("findMember3.id = " + findMember3.getId());
    }
}
