package br.com.fiap.tech.challenge.erp_restaurant.mapper;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import br.com.fiap.tech.challenge.erp_restaurant.domain.address.Address;
import br.com.fiap.tech.challenge.erp_restaurant.infrastructure.controller.dto.address.AddressResponseDTO;
import br.com.fiap.tech.challenge.erp_restaurant.infrastructure.persistence.AddressEntity;

class AddressMapperTest {

    @Test
    @DisplayName("Deve mapear domain para dto")
    void shouldMapDomainToDto() {
        Address domain = new Address("São Paulo", "Santo Andre", "Rua X", 5, "Casa 01");
        domain.setId(1L);
        domain.setState("SP");
        domain.setCity("São Paulo");
        domain.setStreet("Rua A");
        domain.setNumber(100);
        domain.setComplement("Apto 101");

        AddressResponseDTO dto = AddressMapper.INSTANCE.objectToDto(domain);

        assertNotNull(dto);
        assertEquals(domain.getId(), dto.getId());
        assertEquals(domain.getState(), dto.getState());
        assertEquals(domain.getCity(), dto.getCity());
        assertEquals(domain.getStreet(), dto.getStreet());
        assertEquals(domain.getNumber(), dto.getNumber());
        assertEquals(domain.getComplement(), dto.getComplement());
    }

    @Test
    @DisplayName("Deve mapear domain para entity")
    void shouldMapDomainToEntity() {
        Address domain = new Address("São Paulo", "Santo Andre", "Rua X", 5, "Casa 01");
        domain.setId(1L);
        domain.setState("SP");
        domain.setCity("São Paulo");
        domain.setStreet("Rua A");
        domain.setNumber(100);
        domain.setComplement("Apto 101");

        AddressEntity entity = AddressMapper.INSTANCE.toEntity(domain);

        assertNotNull(entity);
        assertEquals(domain.getId(), entity.getId());
        assertEquals(domain.getState(), entity.getState());
        assertEquals(domain.getCity(), entity.getCity());
        assertEquals(domain.getStreet(), entity.getStreet());
        assertEquals(domain.getNumber(), entity.getNumber());
        assertEquals(domain.getComplement(), entity.getComplement());
    }

    @Test
    @DisplayName("Deve mapear entity para domain")
    void shouldMapEntityToDomain() {
        AddressEntity entity = new AddressEntity();
        entity.setId(1L);
        entity.setState("SP");
        entity.setCity("São Paulo");
        entity.setStreet("Rua A");
        entity.setNumber(100);
        entity.setComplement("Apto 101");

        Address domain = AddressMapper.INSTANCE.toDomain(entity);

        assertNotNull(domain);
        assertEquals(entity.getId(), domain.getId());
        assertEquals(entity.getState(), domain.getState());
        assertEquals(entity.getCity(), domain.getCity());
        assertEquals(entity.getStreet(), domain.getStreet());
        assertEquals(entity.getNumber(), domain.getNumber());
        assertEquals(entity.getComplement(), domain.getComplement());
    }
}

