package br.com.fiap.tech.challenge.erp_restaurant.infrastructure.persistence;

import java.math.BigDecimal;
import java.net.URL;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;

@Entity(name = "DISH")
public class DishEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	private String name;

	@NotNull
	private String description;

	@NotNull
	private BigDecimal price;

	@NotNull
	private Boolean onlyToGo;

	@NotNull
	private URL photo;

	@ManyToOne
	@JoinColumn(name = "restaurant_id", nullable = false )
	private RestaurantEntity restaurant;

	public DishEntity() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Boolean getOnlyToGo() {
		return onlyToGo;
	}

	public void setOnlyToGo(Boolean onlyToGo) {
		this.onlyToGo = onlyToGo;
	}

	public URL getPhoto() {
		return photo;
	}

	public void setPhoto(URL photo) {
		this.photo = photo;
	}

	public RestaurantEntity getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(RestaurantEntity restaurant) {
		this.restaurant = restaurant;
	}
	
	
	

}
