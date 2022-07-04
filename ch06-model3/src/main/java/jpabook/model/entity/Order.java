package jpabook.model.entity;

import javax.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by holyeye on 2014. 3. 11..
 */
@Entity
public class Order {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @OneToMany
    private List<OrderItem> orderItems = new ArrayList<OrderItem>();

    @OneToOne
    private Delivery delivery;

    private LocalDate orderDate;

    private OrderStatus status;

    public void setMember(Member member){
        //기존 멤버와 관례를 제거
        if(this.member != null){
            this.member.getOrders().remove(this);
        }

        //무한 루프에 빠지지 않도록 체크
        if(!member.getOrders().contains(this)){
            this.member = member;
            member.getOrders().add(this);
        }
    }


    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Member getMember() {
        return this.member;
    }


    public List<OrderItem> getOrderItems() {
        return this.orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public Delivery getDelivery() {
        return this.delivery;
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
    }

    public LocalDate getOrderDate() {
        return this.orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public OrderStatus getStatus() {
        return this.status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
    
}
