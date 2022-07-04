package jpabook.model.entity;


import javax.persistence.*;

/**
 * Created by holyeye on 2014. 3. 11..
 */
@Entity
public class Delivery {

    @Id @GeneratedValue
    @Column(name = "DELIVERY_ID")
   private Long id;

   @OneToOne(mappedBy = "delivery")
   private Order order;

   private String city;
   private String street;
   private String zipcode;

   @Enumerated(EnumType.STRING)
   private DeliveryStatus status;


    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Order getOrder() {
        return this.order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return this.street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZipcode() {
        return this.zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public DeliveryStatus getStatus() {
        return this.status;
    }

    public void setStatus(DeliveryStatus status) {
        this.status = status;
    }

}
