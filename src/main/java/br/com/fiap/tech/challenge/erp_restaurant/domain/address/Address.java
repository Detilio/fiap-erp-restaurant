package br.com.fiap.tech.challenge.erp_restaurant.domain.address;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Address {

    private Long id;
    private String state;
    private String city;
    private String street;
    private int number;
    private String complement;
    private LocalDateTime updatedAt;
    private LocalDateTime createdAt;


    public Address(String state, String city, String street, int number, String complement) {
        this.state = state;
        this.city = city;
        this.street = street;
        this.number = number;
        this.complement = complement;
        this.updatedAt = LocalDateTime.now();
        this.createdAt = LocalDateTime.now();
    }
}
