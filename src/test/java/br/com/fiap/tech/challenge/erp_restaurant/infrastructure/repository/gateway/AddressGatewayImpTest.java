package br.com.fiap.tech.challenge.erp_restaurant.infrastructure.repository.gateway;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import br.com.fiap.tech.challenge.erp_restaurant.application.gateway.AddressGateway;
import br.com.fiap.tech.challenge.erp_restaurant.domain.address.Address;
import br.com.fiap.tech.challenge.erp_restaurant.infrastructure.persistence.AddressEntity;
import br.com.fiap.tech.challenge.erp_restaurant.infrastructure.repository.AddressRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class AddressGatewayImpTest {

    @Mock
    private AddressRepository addressRepository;

    private AddressGateway gateway;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        gateway = new AddressGatewayImp(addressRepository);
    }

    @Test
    @DisplayName("Deve buscar endereço por Id com sucesso")
    public void testFindById_found() {
        Long id = 1L;
        AddressEntity entity = new AddressEntity();
        entity.setId(id);
        entity.setCity("São Paulo");
        entity.setState("SP");
        entity.setStreet("Av. Paulista");
        entity.setNumber(1000);

        when(addressRepository.findById(id)).thenReturn(Optional.of(entity));

        Address address = gateway.findById(id);

        assertNotNull(address);
        assertEquals("São Paulo", address.getCity());
        assertEquals("SP", address.getState());

        verify(addressRepository).findById(id);
    }

    @Test
    @DisplayName("Deve retornar null ao buscar endereço com id inválido")
    public void testFindById_notFound() {
        Long id = 99L;

        when(addressRepository.findById(id)).thenReturn(Optional.empty());

        Address result = gateway.findById(id);

        assertNull(result);
        verify(addressRepository).findById(id);
    }

    @Test
    @DisplayName("Deve salvar endereço")
    public void testSave() {
        Address domainAddress = new Address("São Paulo", "SP", "Rua A", 5, "Apto 15");

        AddressEntity savedEntity = new AddressEntity();
        savedEntity.setId(1L);
        savedEntity.setState("SP");
        savedEntity.setCity("São Paulo");
        savedEntity.setStreet("Av. Brasil");
        savedEntity.setNumber(200);
        savedEntity.setComplement("apto 10");

        when(addressRepository.save(any(AddressEntity.class))).thenReturn(savedEntity);

        Address saved = gateway.save(domainAddress);

        assertNotNull(saved);
        assertEquals(1L, saved.getId());
        assertEquals("São Paulo", saved.getCity());
        verify(addressRepository).save(any(AddressEntity.class));
    }

    @Test
    @DisplayName("Deve deletar endereço")
    public void testDelete() {
        Long id = 5L;

        doNothing().when(addressRepository).deleteById(id);

        gateway.delete(id);

        verify(addressRepository).deleteById(id);
    }
}
