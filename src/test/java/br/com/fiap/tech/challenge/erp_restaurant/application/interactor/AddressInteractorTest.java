package br.com.fiap.tech.challenge.erp_restaurant.application.interactor;

import br.com.fiap.tech.challenge.erp_restaurant.application.exception.NotFoundException;
import br.com.fiap.tech.challenge.erp_restaurant.application.gateway.AddressGateway;
import br.com.fiap.tech.challenge.erp_restaurant.domain.address.Address;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AddressInteractorTest {

    private AddressGateway addressGateway;
    private AddressInteractor interactor;

    @BeforeEach
    public void setup() {
        addressGateway = mock(AddressGateway.class);
        interactor = new AddressInteractor(addressGateway);
    }

    @Test
    public void testFindByIdSuccess() {
        Address address = createSampleAddress();
        address.setId(1L);
        when(addressGateway.findById(1L)).thenReturn(address);

        Address result = interactor.findById(1L);

        assertNotNull(result);
        assertEquals("São Paulo", result.getCity());
        verify(addressGateway, times(1)).findById(1L);
    }

    @Test
    public void testFindByIdNotFound() {
        when(addressGateway.findById(1L)).thenReturn(null);

        assertThrows(NotFoundException.class, () -> interactor.findById(1L));
    }

    @Test
    public void testCreate() {
        Address address = createSampleAddress();
        Address savedAddress = createSampleAddress();
        when(addressGateway.save(address)).thenReturn(savedAddress);

        Address result = interactor.create(address);
        result.setId(1L);
        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(addressGateway, times(1)).save(address);
    }

    @Test
    public void testUpdateSuccess() {
        Address existing = createSampleAddress();
        Address updated = createAddressToUpdate();
        existing.setId(1L);
        updated.setId(2L);
        when(addressGateway.findById(1L)).thenReturn(existing);
        when(addressGateway.save(any(Address.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Address result = interactor.update(1L, updated);

        assertEquals("RJ", result.getState());
        assertEquals("Rio de Janeiro", result.getCity());
        assertEquals("Av. Atlântica", result.getStreet());
        assertEquals(1000, result.getNumber());
        assertEquals("Apto 202", result.getComplement());

        verify(addressGateway).findById(1L);
        verify(addressGateway).save(any(Address.class));
    }

    @Test
    public void testDelete() {
        doNothing().when(addressGateway).delete(1L);

        interactor.delete(1L);

        verify(addressGateway, times(1)).delete(1L);
    }

    private Address createSampleAddress() {
        return new Address("SP", "São Paulo", "Av. Atlântica", 1000, "Apto 02");
    }

    private Address createAddressToUpdate() {
        return new Address("RJ", "Rio de Janeiro", "Av. Atlântica", 1000, "Apto 202");
    }
}