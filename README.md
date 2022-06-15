# JPA 프로그래밍 공부

자바 ORM 표준 JPA 프로그래밍 책을 보면서 내용 정리 및 코드 구성

### JPA 소개

기존의 관계형 데이터베이스를 이용한 애플리케이션 개발은 마이바티스나 SQL매퍼를 사용하면서 많은 JDBC API 사용 코드를 줄일 수 있었다.  
하지만 여전히 반복적인 SQL문(등록,수정,삭제,조회)을 작성해야 했으며, 데이터를 저장하는 관계형 데이터베이스 때문에 객체 모델링을 SQL로 풀어내는데 많은 노력을 요구하였다.  
이런 문제점을 해결해 주는 것이 ORM프레임워크 JPA이다. JPA는 반복적인 SQL해결 및 객체 모델링과 관계형 데이터베이스 사이의 차이점을 해결해준다.

##### SQL을 직접 다를 때 문제점

- 진정한 의미의 계층 분할이 어렵다.
- 엔티티를 신뢰할 수 없다.
- SQL에 의존적인 개발을 피하기 어렵다.

##### JPA로 문제해결

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

##### JPA 연관관계

객체 = 참조를 사용해서 다른 객체와 연관관계를 가지고 참조에 접근해서 연관된 객체를 조회  
 **참조가 있는 방향으로만 조회가 가능하다**

  <pre><code>member.getTeam() //가능
team.getMember() //참조가 없으므로 불가능</code></pre>

테이블 = 외래키를 사용해서 다른 테이블과 연관과녜를 가지고 조인을 사용해서 연관된 테이블을 조회  
**외래키 하나로 양방향 조회가 가능하다**

##### JPA와 객체 그래프 탐색

지연 로딩 = 실제 객체를 사용하는 시점까지 데이터베이스 조회를 미룬다.  
JPA는 연관된 객체를 즛기 함께 조회할지 아니면 실제 사용되는 시점에 지연해서 조회할지 설정으로 정의할 수 있다.

<pre><code>//처음 조회 시점에 SELECT MEMBER SQL
Member member = jpa.find(Member.class, memberId);

Order order = member.getOrder();
order.getOrderDate();  //Order를 사용하는 시점에 SELECT ORDER SQL</code></pre>

##### JPA 비교

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

##### JPA(java Persistence API)

자바 진영의 ORM기술 표준이다. 애플리케이션과 JDBC사이에서 동작한다.
<img src="/img/jpa.png">

##### ORM(Object-Relational Mapping)

객체와 관계형 데이터베이스를 매팡하는 프레임워크이다. 객체를 데이터베이스에 저장할 때 SQL문을 작성하지 않고, 자바 컬렉션에 저장하듯이 할 수 있게 해준다.
<img src="/img/orm.png">  
하이버네이트 = 거의 대부분의 패러다임 불일치 문제를 해결해주는 성숙한 ORM프레임워크

##### why JPA??

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

### JPA 시작

##### 객체 매핑

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

##### 엔티티 매니저 팩토리 생성

<img src="/img/entityManager.png">   
persistence.xml의 설정 정보를 사용해서 엔티티 매니져 팩토리를 생성해야 한다. 이때 엔티티 매니저 팩토리를 생성하는 비용은 아주 크다.   
따라서 **엔티티 매니저 팩토리는 애플리케이션 전체에서 딱 한 번만 생성하고 공유해서 사용해야 한다.**
