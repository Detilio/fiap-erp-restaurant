package br.com.fiap.tech.challenge.erp_restaurant.repository;

import br.com.fiap.tech.challenge.erp_restaurant.entity.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<AddressEntity, Long> {

    // Você pode adicionar métodos de consulta específicos aqui, se necessário.
    // Por exemplo, para buscar endereços por user_id:
    List<AddressEntity> findByUser_Id(Long userId);
}