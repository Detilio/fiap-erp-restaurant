package br.com.fiap.tech.challenge.erp_restaurant.domain.restaurant.menu;

import java.math.BigDecimal;
import java.net.URL;

import br.com.fiap.tech.challenge.erp_restaurant.domain.restaurant.Restaurant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
@AllArgsConstructor
public class Dish {

	private Long id;
	private String name;
	private String description;
	private BigDecimal price;
	private Boolean onlyToGo;
	private URL photo;
	private Restaurant restaurant;
	
	public Dish(String name, String description, BigDecimal price, Boolean onlyToGo, URL photo) {
		this.name = validateName(name);
		this.description = validateDescription(description);
		this.price = validatePrice(price);
		this.onlyToGo = onlyToGo;
		this.photo = photo;
	}
	
	public void update(String name, String description, Boolean onlyToGo, BigDecimal price, URL photo) {
		this.name = validateName(name);
		this.description = validateDescription(description);
		this.price = validatePrice(price);
		this.onlyToGo = onlyToGo;
		this.photo = photo;
	}

	private String validateName(String name) {
		if (name == null || name.isEmpty()) {
			throw new RuntimeException("name must be not null");
		}
		return name;
	}

	private String validateDescription(String desc) {
		if (desc == null || desc.isEmpty()) {
			throw new RuntimeException("description must be not null");
		}
		return desc;
	}

	private BigDecimal validatePrice(BigDecimal price) {
		if (price.compareTo(BigDecimal.ZERO) != 1) {
			throw new RuntimeException("Price can't be negative or zero");
		}
		return price;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public Boolean getOnlyToGo() {
		return onlyToGo;
	}

	public URL getPhoto() {
		return photo;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	public Restaurant getRestaurant() {
		return restaurant;
	}



}
