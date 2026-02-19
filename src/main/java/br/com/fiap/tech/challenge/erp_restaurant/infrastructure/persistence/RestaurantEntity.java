package br.com.fiap.tech.challenge.erp_restaurant.infrastructure.persistence;

import java.time.LocalTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity(name = "RESTAURANT")
public class RestaurantEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String kitchenType;

	@Column(nullable = false)
	private LocalTime openingAt;

	@Column(nullable = false)
	private LocalTime closingAt;
	
	@OneToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private UserEntity owner;
	
	@OneToMany(mappedBy = "id" ,cascade = CascadeType.MERGE)
	private List<DishEntity> menu;

	public RestaurantEntity() {
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

	public String getKitchenType() {
		return kitchenType;
	}

	public void setKitchenType(String kitchenType) {
		this.kitchenType = kitchenType;
	}

	public LocalTime getOpeningAt() {
		return openingAt;
	}

	public void setOpeningAt(LocalTime openingAt) {
		this.openingAt = openingAt;
	}

	public LocalTime getClosingAt() {
		return closingAt;
	}

	public void setClosingAt(LocalTime closingAt) {
		this.closingAt = closingAt;
	}

	public UserEntity getOwner() {
		return owner;
	}

	public void setOwner(UserEntity owner) {
		this.owner = owner;
	}

	public List<DishEntity> getMenu() {
		return menu;
	}

	public void setMenu(List<DishEntity> menu) {
		this.menu = menu;
	}
	
	
	
}
