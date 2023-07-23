package com.alphacrash.demographics.controller;

import com.alphacrash.demographics.entity.Address;
import com.alphacrash.demographics.payload.AddressDTO;
import com.alphacrash.demographics.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/consumers/{consumerId}/addresses")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @GetMapping
    public List<Address> getAllAddressesByConsumerId(@PathVariable Long consumerId) {
        return addressService.getAllAddressesByConsumerId(consumerId);
    }

    @GetMapping("/{addressType}")
    public Address getAddressById(@PathVariable Long consumerId, @PathVariable String addressType) {
        return addressService.getAddressByType(consumerId, addressType);
    }

    @PostMapping
    public Address createAddress(@PathVariable Long consumerId, @RequestBody AddressDTO addressDTO) {
        return addressService.createAddress(addressDTO, consumerId);
    }

    @PutMapping
    public Address updateAddress(@PathVariable Long consumerId, @RequestBody AddressDTO addressDTO) {
        return addressService.updateAddress(addressDTO, consumerId);
    }

    @DeleteMapping("/{addressType}")
    public void deleteAddress(@PathVariable Long consumerId, @PathVariable String addressType) {
        addressService.deleteAddress(consumerId, addressType);
    }
}
