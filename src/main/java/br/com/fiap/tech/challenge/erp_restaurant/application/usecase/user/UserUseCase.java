package br.com.fiap.tech.challenge.erp_restaurant.application.usecase.user;

import java.util.List;

import br.com.fiap.tech.challenge.erp_restaurant.domain.user.User;

public interface UserUseCase{

	List<User> findAllUsers(int page, int size);
	
	User findUserById(Long id);
	
	User save(User u);
	
	User update(User u);
	
	User updateRole(User u);
	
	User updateAddress(User u);
	
	void delete(Long id);
}
