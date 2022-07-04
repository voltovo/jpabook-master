package jpabook.model.entity;

import javax.persistence.*;

/**
 * Created by holyeye on 2014. 3. 11..
 */
@Entity
@Table(name = "ORDER_ITEM")
public class OrderItem {

    @Id @GeneratedValue
    @Column(name = "ORDER_ITEM_ID")
    private Long id;

    private Item item;

    @ManyToOne
    @JoinColumn(name = "ORDER_ID")
    private Order order;

    private int orderPrice;

    private int count;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Item getItem() {
        return this.item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Order getOrder() {
        return this.order;
    }

    public void setOrder(Order order) {
        //기존 order와 관계를 제거
        if(this.order != null){
            this.order.getOrderItems().remove(this);
        }
        this.order = order;
    }

    public int getOrderPrice() {
        return this.orderPrice;
    }

    public void setOrderPrice(int orderPrice) {
        this.orderPrice = orderPrice;
    }

    public int getCount() {
        return this.count;
    }

    public void setCount(int count) {
        this.count = count;
    }


}
