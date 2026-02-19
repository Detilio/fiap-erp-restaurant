package br.com.fiap.tech.challenge.erp_restaurant.infrastructure.controller.v1;

import static br.com.fiap.tech.challenge.erp_restaurant.mapper.AddressMapper.INSTANCE;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.tech.challenge.erp_restaurant.application.usecase.address.AddressUseCase;
import br.com.fiap.tech.challenge.erp_restaurant.infrastructure.controller.dto.address.AddressResponseDTO;
import lombok.extern.log4j.Log4j2;
@Log4j2
@RestController
@RequestMapping("v1/address")
public class AddressControllerV1 {

    private final AddressUseCase addressUseCase;

    public AddressControllerV1(AddressUseCase addressUseCase) {
        this.addressUseCase = addressUseCase;
    }

    @GetMapping("{id}")
    public AddressResponseDTO findById(@PathVariable Long id) {
        return INSTANCE.objectToDto(addressUseCase.findById(id));
    }
}
