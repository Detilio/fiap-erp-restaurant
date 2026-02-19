package br.com.fiap.tech.challenge.erp_restaurant.domain.user;

import java.time.LocalDateTime;

import br.com.fiap.tech.challenge.erp_restaurant.domain.address.Address;
import br.com.fiap.tech.challenge.erp_restaurant.domain.restaurant.Restaurant;
import br.com.fiap.tech.challenge.erp_restaurant.shared.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
@EqualsAndHashCode
public class User {

    private Long id;
    private String name;
    private String email;
    private String login;
    private String password;
    private LocalDateTime updatedAt;
    private LocalDateTime createdAt;
    private Role role;
    private Address address;
    private Restaurant restaurant;

    public User(Long id, String name, String email, String login, String password, Role role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.login = login;
        this.password = password;
        this.role = role == null ? Role.CUSTOMER : role;
        this.updatedAt = LocalDateTime.now();
        this.createdAt = LocalDateTime.now();
    }

	public void setRole(Role role) {
		this.role = role == null ? Role.CUSTOMER : role;
	}
	
	public void setAddress(Address address) {
		this.address = address;
	}

}
