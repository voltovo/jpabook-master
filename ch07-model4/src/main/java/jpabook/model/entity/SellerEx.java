package jpabook.model.entity;

import javax.persistence.Entity;

@Entity
public class SellerEx extends BaseEntityEx {
    
    private String shopName;


    public String getShopName() {
        return this.shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

}
