# JPA 프로그래밍 공부

자바 ORM 표준 JPA 프로그래밍 책을 보면서 내용 정리 및 코드 구성

## JPA 소개

기존의 관계형 데이터베이스를 이용한 애플리케이션 개발은 마이바티스나 SQL매퍼를 사용하면서 많은 JDBC API 사용 코드를 줄일 수 있었다.  
하지만 여전히 반복적인 SQL문(등록,수정,삭제,조회)을 작성해야 했으며, 데이터를 저장하는 관계형 데이터베이스 때문에 객체 모델링을 SQL로 풀어내는데 많은 노력을 요구하였다.  
이런 문제점을 해결해 주는 것이 ORM프레임워크 JPA이다. JPA는 반복적인 SQL해결 및 객체 모델링과 관계형 데이터베이스 사이의 차이점을 해결해준다.

### SQL을 직접 다를 때 문제점

- 진정한 의미의 계층 분할이 어렵다.
- 엔티티를 신뢰할 수 없다.
- SQL에 의존적인 개발을 피하기 어렵다.

### JPA로 문제해결

- 저장
<pre><code>jpa.persist(member); //저장</code></pre>
- 조회
<pre><code>String memberId = "helloId";
Member member = jpa.find(Member.class, memberId); //조회</code></pre>
- 수정
<pre><code>Member member = jpa.find(Member.class, memberId);
member.setName("이름변경"); //수정</code></pre>
- 연관된 객체 조회
<pre><code>Member member = jpa.find(Member.class, memberId);
Team team = member.getTeam(); //연관된 객체 조회</code></pre>

### JPA 연관관계

객체 = 참조를 사용해서 다른 객체와 연관관계를 가지고 참조에 접근해서 연관된 객체를 조회  
 **참조가 있는 방향으로만 조회가 가능하다**

  <pre><code>member.getTeam() //가능
team.getMember() //참조가 없으므로 불가능</code></pre>

테이블 = 외래키를 사용해서 다른 테이블과 연관과녜를 가지고 조인을 사용해서 연관된 테이블을 조회  
**외래키 하나로 양방향 조회가 가능하다**

### JPA와 객체 그래프 탐색

지연 로딩 = 실제 객체를 사용하는 시점까지 데이터베이스 조회를 미룬다.  
JPA는 연관된 객체를 즛기 함께 조회할지 아니면 실제 사용되는 시점에 지연해서 조회할지 설정으로 정의할 수 있다.

<pre><code>//처음 조회 시점에 SELECT MEMBER SQL
Member member = jpa.find(Member.class, memberId);

Order order = member.getOrder();
order.getOrderDate();  //Order를 사용하는 시점에 SELECT ORDER SQL</code></pre>

### JPA 비교

- 동일성 비교는 == 비교. 객체 인스턴스의 주소 값을 비교한다.
- 동등성 비교는 equalls()을 사용해서 객체 내부의 값을 비교한다.

<pre><code>String memberId = "100";
Member member1 = memberDAO.getMember(memberId);
Member member2 = memberDAO.getMember(memberId);

member1 == member2 //다르다</code></pre>

객체 측면에서 볼 떄 둘은 다른 인스턴스이기 때문에 동일성 비교는 같을 수 없다.

- JPA 비교
JPA는 같은 트랙잭션일 떄 같은 객체가 조회되는 것을 보장한다.
<pre><code>String memberId = "100";
Member member1 = jpa.find(Member.class, memberId);
Member member2 = jpa.find(Member.class, memberId);
member1 == member2 //같다</code></pre>

### JPA(java Persistence API)

자바 진영의 ORM기술 표준이다. 애플리케이션과 JDBC사이에서 동작한다.
<img src="/img/jpa.png">

### ORM(Object-Relational Mapping)

객체와 관계형 데이터베이스를 매팡하는 프레임워크이다. 객체를 데이터베이스에 저장할 때 SQL문을 작성하지 않고, 자바 컬렉션에 저장하듯이 할 수 있게 해준다.
<img src="/img/orm.png">  
하이버네이트 = 거의 대부분의 패러다임 불일치 문제를 해결해주는 성숙한 ORM프레임워크

### why JPA??

1. 생산성
   자바 컬렉션에 객체를 저장하듯이 JPA에게 저장할 객체를 전달하면 끝이다.
   <pre><code>jpa.persist(member); //저장
   Member member = jpa.find(memberId); //조회</code></pre>
   지루하고 반복적인 코드와 CRUD용 SQL을 개발자가 직접 작성하지 않아도 된다.
2. 유지보수
   SQL을 직접 다루면 엔티티에 필드를 하나만 추가해도 관려된 등록, 수정, 조회 SQL과 결과를 매핑하기 위한 JDBC API코드를 모두 변경해야 한다. 반면에 JPA는 이런 과정을 대신 처리해주므로 수정해야 할 코드가 줄어 든다. 따라서 유지보수 코드 수가 줄어든다.
3. 패러다임의 불일치 해결
   JPA는 상속, 연관관계, 객체 그래프 탐색, 비교하기와 같은 패러다임의 불일치 문제를 해결해준다.
4. 성능
   JPA는 애플리케이션과 테이터베이스 사이에서 동작한다. 이렇게 애플리케이션과 데이터베이스 사이에 계층이 하나 더 있으면 최적화 관점에서 시도해 볼 수 있는 것들이 많다.
   <pre><code>String memberId = "helloId";
   Member member1 = jpa.find(memberId);
   Member member2 = jpa.find(memberId);</code></pre>
   JDBC API로 SQL을 사용해서 직접 코드를 작성했다면 데이터 베이스와 두 번 통신했을 것이다. JPA를 사용하면 SELECT SQL을 한번만 데이터베이스에 전달하고 두 번째는 조회한 회원 객체를 재사용한다.
5. 데이터 접근 추상화와 벤더 독립성
   예를 들어 페이징 처리를 한다고 가정하면 데이터베이스마다 사용법이 달라서 각자의 문법을 배워야 한다. 반면에 JPA는 특정 데이터베이스 기술에 종속되지 않아서 데이터베이스를 변경한다면 JPA에게 알려주기만 하면 된다.  
   예를 들어 개발환경에서는 H2를 사용하고 테스트 환경이나 운영 환경에서는 오라클을 사용할 수 있다.

## JPA 시작

### 객체 매핑

- @Entity
  해당 클래스를 테이블과 매핑한다고 JPA에게 알려준다. 이런 클래스를 엔티티 클래스라고 한다.
- @Table
  엔티티 클래스에 매핑할 테이블 정보를 알려준다. 보통은 클래스 이름을 테이블 이름으로 매핑한다.  
  name속성을 이용해서 테이블 이름을 명시할 수도 있다.
- @Id
  엔티티 클래스의 필드를 테이블의 기본 키(PRIMARY KEY)에 매핑한다. 이렇게 사용된 필드를 식별자 필드라고 한다.
- @Column
  필드를 컬럼에 매핑한다.
- 매핑 정보가 없는 필드
  매핑 어노테이션을 생략하면 필드명을 사용해서 컬럼명으로 매핑한다. 대소문자를 구분하는 데이터베이스를 사용한다면 column어노테이션을 사용해서 명시적으로 매핑해 주는 것이 좋다.

### 엔티티 매니저 팩토리 생성

<img src="/img/entityManager.png">

persistence.xml의 설정 정보를 사용해서 엔티티 매니져 팩토리를 생성해야 한다. 이때 엔티티 매니저 팩토리를 생성하는 비용은 아주 크다.  
따라서 **엔티티 매니저 팩토리는 애플리케이션 전체에서 딱 한 번만 생성하고 공유해서 사용해야 한다.**

### 엔티티 매니저 생성

JPA의 기능 대부분은 이 엔티티 매니저가 제공한다.  
**엔티티 매니저를 사용해서 엔티티를 데이터베이스에 등록/수정/삭제/조회할 수 있다.**
**참고로 엔티티 매니저는 데이터베이스 커넥션과 밀접한 관계가 있으므로 스레드간에 공유하거나 재사용하면 안 된다.**

### 종료

<pre><code>em.close();  //엔티티 매니저 종료
emf.close();   //엔티티 매니저 팩토리 종료</code></pre>

마지막으로 사용이 끝난 엔티티 매니저, 엔티티 매니저 팩토리는 반드시 종료해야 한다.

### JPQL
* JPQL은 엔티티 객체를 대상으로 쿼리한다. 클래스와 필드를 대상으로 쿼리한다는 뜻.
* SQL은 데이터베이스 테이블을 대상으로 쿼리한다.
* 사용법 : em.createQuery(JPQL, 반환타입) 메소드를 실행해서 쿼리 객체를 생성한 후 쿼리 객체의 getResultList() 메소를 호출.

## 영속성 관리
### 영속성 컨텍스트?
'엔티티를 영구 저장하는 환경'이라는 뜻이다.   
em.persist(member)은 엔티티 매니저를 사용해서 회원 엔티티를 영속성 켄텍스트에 저장하는 것이다.   
영속성 컨텍스트는 엔티티 매니저를 생성할 때 하나 만들어진다. 그리고 엔티티 매니저를 통해서 영속성 컨텍스트ㅡ에 접근할 수 있고, 영속성 컨텍스트를 관리할 수 있다.   
_여러 엔티티 매니저가 같은 영속성 컨텍스트에 접근하는 방법도 있다_

### 엔티티 생명주기
* 비영속성(new/transient)   
영속성 컨텍스트와 전혀 관계가 없는 상태
* 영속(managed)   
영속성 컨텍스트에 저장된 상태
* 준영속(detached)   
영속성 컨텍스트에 저장되었다가 분리된 상태
* 삭제(removed)   
삭제된 상태

<img src="/img/persistenceLifeCycle.png">

### 영속성 컨텍스트의 특징
1. 영속성 컨텍스트와 식별자 값   
영속성 컨텍스트는 엔티티를 식별자 값(@id로 테이블의 기본 키와 매핑한 값)으로 구분한다. 따라서 **영속 상태는 식별자 값이 반드시 있어야 한다.**
2. 영속성 컨텍스트와 데이터베이스 저장   
플러쉬(flush): 트랜잭션을 커밋하는 순간 영속성 컨텍스트에 새로 저장된 엔티티를 데이터베이스에 반영하는 것
3. 영속성 컨텍스트가 엔티티를 관리하면 다음과 같은 장점이 있다.   
* 1차 캐시
* 동일성 보장
* 트랜잭션을 지원하는 쓰기 지원
* 변경 감지
* 지연 로딩

### 엔티티 조회
1차 캐시 : 영속성 컨텍스트가 내부에 가지고 있는 캐시, 영속 상태의 엔티티는 모두 이곳에 저장된다.

#### 1차 캐시에서 조회
em.find()를 호출하면 우선 1차 캐시에서 식별자 값으로 엔티티를 찾는다. 만약에 찾는 엔티티가 있으면 데이터베이스를 조회하지 않고 메모리에 있는 1차 캐시에서 엔티티를 조회한다.
<pre><code>Member member = new Member();
member.setId("member1");
member.setUsername("회원1");

//1차 캐시에 저장
em.persist(membber);

//1차 캐시에서 조회
Member findMember = em.find(Member.class, "member1");</code></pre>

#### 데이터베이스에서 조회
1. em.find(Member.class, "member2")를 실행한다
2. member2가 1차 캐시에 없으면 데이터베이스에서 조회한다.
3. 조회한 데이터로 member2 엔티티를 생성해서 1차 캐시에 저장한다.(영속 상태)
4. 조회한 엔티티를 반환한다.

#### 변경 감지
스냅샷 : JPA가 엔티티를 영속성 컨텍스트에 보관할 때, 최초 상태를 복사해서 저장해 놓은 것
1. 트랜잭션을 커밋하면 엔티티 매니저 내부에서 먼저 플러시가 호출된다.
2. 엔티티와 스냅샷을 비교해서 변경된 엔티티를 찾는다.
3. 변경된 엔티티가 있으면 수정 쿼리를 생성해서 쓰기 지연 SQL 저장소에 보낸다.
4. 쓰기 지연 저장소와 SQL을 데이터베이스에 보낸다.
5. 데이터베이스 트랜잭션을 커밋한다.

**변경 감지는 영속성 컨텍스트가 관리하는 영속 상태의 엔티티에만 적용**   
비영속, 준영속상태는 엔티티 값을 변경해도 데이터베이스에 반영되지 않는다.

**JPA의 기본 전략은 수정이 발생하면 엔티티의 모든 필드를 업데이트**   
모든 필드 업데이트시 장점   
* 수정 쿼리가 항상 같다. 애플리케이션 로딩 시점에 수정 쿼리를 미리 생성해두고 재사용 가능
* 데이터베이스에 동일한 쿼리를 보내면 데이터베이스는 이전에 한 번 파싱된 쿼리를 재사용
* org.hibernate.annotations.DynamicUpdate 어노테이션을 사용하면 수정된 데이터만 사용해서 동적으로 UPDATE SQL을 생성한다.

#### 엔티티 삭제
<pre><code>Member memberA = em.find(Member.class, "memberA"); //삭제 대상 엔티티 조회
em.remove(memberA); //엔티티 삭제</code></pre>

em.remove()에 삭제 대상 엔티티를 넘겨주면 삭제한다. 하지만 즉시 삭제하는 것이 아니라 엔티티 등록과 비슷하게 삭제 쿼리를 쓰기 지연 SQL저장소에 등록한다. 이후 트랜잭션을 커밋해서 플러시를 호출하면 실제 데이터베이스에 삭제 쿼리를 전달한다.   
이렇게 **삭제된 엔티티는 재사용하지 않고 자연스럽게 가비지 컬렉션의 대상이 되도록 두는 것이 좋다.**

### 플러시
**플러시(fluse())는 영속성 컨텍스트의 변경 내용을 데이터베이스에 반영한다.**    
영속성 컨텍스트에 보관된 엔티티를 지운다고 생각하지 말고, 영속성 컨텍스트의 변경 내용을 데이터베이스에 동기화하는 것이다.     
1. 변경 감지가 동작해서 영속성 컨텍스트에 있는 모든 엔티티를 스냅샷과 비교해서 수정된 엔티티를 찾는다.
2. 쓰기 지연 SQL저장소의 쿼리를 데이터베이스에 전송(등록,수정,삭제 쿼리)   

영속성 컨텍스트를 플러시 하는 방법   
1. em.fluse()를 직접 호출   
영속성 컨텍스트를 강제로 플러시한다. 테스트나 다른 프레임워크와 JPA를 함께 사용할 때를 제외하고 거의 사용X
2. 트랜잭션 커밋 시 플러시가 자동 호출   
트랜잭션을 커밋하기 전에 꼭 플러시를 호출해서 영속성 컨텍스트의 변경 내용을 데이터베이스에 반영해야 한다.   
JPA는 이런 문제를 예방하기 위해서 트랜재개션을 커밋할 때 플러시를 자동으로 호출한다.
3. JPQL 쿼리 실행 시 플러시가 자동 호출   
<pre><code>em.persist(memberA);
em.persist(memberB);
em.persist(memberC);

//중간에 JPQL 실행
query = em.createQuery("select m from Member m", Member.class);
List< Member> members = query.getResultList();</code></pre>
엔티티 memberA, memberB, memberC를 영속 상태로 만들었다. 이 엔티티들은 영속성 컨텍스트에는 있지만 아직 데이터베이스에는 반영되지 않았다. 이때 JPQL을 실행하면 아직 데이터베이스에 member값들이 없으므로 조회가 되지 않는다.   
따라서 쿼리를 실행하지 직전에 영속성 컨텍스트를 플러시해 변경 내용을 데이터베이스에 반영해야 한다.   
**JPA는 이런 문제를 예방하기 위해 JPQL을 실행할 때도 플러시를 자동 호출한다.**   
참고로 식별자를 기준으로 조회하는 find()메소드를 호출할 때는 플러시가 실행되지 않는다.

### 준영속
준영속 상태(detachec) : 영속성 컨텍스트가 관리하는 영속 상태의 엔티티가 영속성 컨텍스트에서 분리된 상태.
**준영속 상태의 엔티티는 영속성 컨텍스트가 제공하는 기능을 사용할 수 없다.**   

준영속 상태를 만드는 방법
1. em.detach(entity) : 특정 엔티티만 준영속 상태로 전환
2. em.clear() : 영속성 컨텍스트를 완전히 초기화
3. em.close() : 영속성 컨텍스트를 종료

**영속 상태의 엔티티는 주로 영속성 컨텍스트가 종료되면서 준영속 상태가 된다. 개발자가 직접 준영속 상태로 만드는 일은 드물다.**

준영속 상태의 특징   
1. 거의 비영속 상태에 가깝다.   
영속성 컨텍스트가 관리하지 않으므로 1차 캐시, 쓰기 지연, 변경 감지, 지연 로딩을 포함한 영속성 컨텍스트가 제공하는 모든 기능이 동작하지 않는다.
2. 식별자 값을 가지고 있다.   
이미 한 번 영속 상태였으므로 반드시 식별자 값을 가지고 있다.
3. 지연 로딩을 할 수 없다.   
실제 객체 대신 프록치 객체를 로딩해두고 해당 객체를 실제 사용할 때 영속성 컨텍스트를 통해 데이터를 불러오는 방법인데, 영속성 컨텍스트가 더 이상 관리하지 않으므로 문제가 발생 한다.

### 병합 (merge)
새로운 영속 상태릐 엔티티를 반환.
<pre><code>Member mergeMember = em.merge(member);</code></pre>

## 엔티티 매핑
JPA를 사용하는데 가장 중요한 일은 엔티티와 테이블을 정확하게 매핑하는 것이다.

* 객체와 테이블 매핑 : @Entity, @Table
* 기본 키 매핑 : @Id
* 필드와 컬럼 매핑 : @Column
* 연관관계 매핑 : @ManyToOne, @JoinColumn   

매핑 정보는 XML이나 어노테이션 중에 선택해서 기술하면 된다. 각각 장단점이 있지만 어노테이션을 사용하는 쪽이 좀 더 쉽고 직관적이다.

### @Entity
@Entity가 붙은 클래스는 JPA가 관리하는 것으로, 엔티티라고 부른다.   
적용 시 주의 사항
* 기본 생성자는 필수다 (파라미터가 없는 public or protected 생성자)
* final 클래스, enum, interface, inner 클래스에는 사용 할 수 없다.
* 저장할 필드에 final을 사용하면 안 된다.

### @Table
@Table은 엔티티와 매핑할 테이블을 지정한다.
