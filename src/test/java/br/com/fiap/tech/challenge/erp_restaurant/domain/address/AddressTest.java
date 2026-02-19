package br.com.fiap.tech.challenge.erp_restaurant.domain.address;

import br.com.fiap.tech.challenge.erp_restaurant.domain.address.Address;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AddressTest {

    @Test
    @DisplayName("Teste de criação de endereço com valores corretos")
    void deveCriarEnderecoComValoresCorretos() {
        String state = "SP";
        String city = "São Paulo";
        String street = "Av. Paulista";
        int number = 1000;
        String complement = "Apto 101";

        Address address = new Address(state, city, street, number, complement);

        assertThat(address.getState()).isEqualTo(state);
        assertThat(address.getCity()).isEqualTo(city);
        assertThat(address.getStreet()).isEqualTo(street);
        assertThat(address.getNumber()).isEqualTo(number);
        assertThat(address.getComplement()).isEqualTo(complement);
        assertThat(address.getCreatedAt()).isNotNull();
        assertThat(address.getUpdatedAt()).isNotNull();
    }
}
