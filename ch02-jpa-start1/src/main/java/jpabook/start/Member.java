package jpabook.start;

import java.time.LocalDate;

import javax.persistence.*;  //**

/**
 * User: suman
 * Date: 22. 6. 13. Time: 오후 9:42
 */
@Entity
@Table(name="MEMBER", uniqueConstraints = {@UniqueConstraint(
    name = "NAME_AGE_UNIQUE",
    columnNames = {"NAME", "AGE"}
)})
public class Member {

    @Id
    @Column(name = "ID")
    private String id;

    //회원 이름은 필수, 10자 초과 금지 조건 추가
    @Column(name = "NAME", nullable = false, length = 10)
    private String username;

    @Column(name = "AGE")
    private Integer age;

    //enum을 사용할 수 있는 어노테이션
    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    private LocalDate createdDate;

    private LocalDate lastModifiedate;
    
    //회원을 설명하는 필드의 길이 제한이 없다.
    //데이터 베이스의 VARCHAR or CLOB 타입으로 저장해야한다.
    //@Lob을 사용하면 CLOB, BLOB 타입을 매핑 할 수 있다.
    @Lob
    private String description;
    
    public RoleType getRoleType() {
        return this.roleType;
    }
    
    public void setRoleType(RoleType roleType) {
        this.roleType = roleType;
    }
    
    public LocalDate getCreatedDate() {
        return this.createdDate;
    }
    
    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }
    
    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public Integer getAge() {
        return age;
    }
    
    public void setAge(Integer age) {
        this.age = age;
    }
    public LocalDate getLastModifiedate() {
        return lastModifiedate;
    }
    
    public void setLastModifiedate(LocalDate lastModifiedate) {
        this.lastModifiedate = lastModifiedate;
    }
}
