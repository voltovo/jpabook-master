package jpabook.model.entity;

import javax.persistence.Entity;

@Entity
public class MemberEx extends BaseEntityEx {
    
    private String email;


    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
