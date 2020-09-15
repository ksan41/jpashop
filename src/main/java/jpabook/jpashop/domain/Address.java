package jpabook.jpashop.domain;


import lombok.Getter;

import javax.persistence.Embeddable;

// 값타입. 변경 불가능하게 설계해야 함.

@Getter
@Embeddable
public class Address {

    private String city;
    private String street;
    private String zipcode;

    protected Address() {
    }

    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
