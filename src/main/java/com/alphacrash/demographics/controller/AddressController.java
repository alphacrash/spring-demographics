package com.alphacrash.demographics.controller;

import com.alphacrash.demographics.entity.Address;
import com.alphacrash.demographics.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class AddressController {
    
    @Autowired
    private AddressService addressService;
    
    @GetMapping("/{userId}/addresses")
    public List<Address> getAllAddressesByUserId(@PathVariable Long userId) {
        return addressService.getAllAddressesByUserId(userId);
    }
    
    @GetMapping("/{userId}/addresses/{id}")
    public ResponseEntity<Address> getAddressById(@PathVariable Long userId, @PathVariable Long id) {
        Address address = addressService.getAddressById(id);
        return ResponseEntity.ok(address);
    }
    
    @PostMapping("/{userId}/addresses")
    public ResponseEntity<Address> createAddress(@PathVariable Long userId, @RequestBody Address address) {
        Address savedAddress = addressService.createAddress(address, userId);
        return new ResponseEntity<>(savedAddress, HttpStatus.CREATED);
    }
    
    @PutMapping("/{userId}/addresses/{id}")
    public ResponseEntity<Address> updateAddress(@PathVariable Long userId, @PathVariable Long id, @RequestBody Address address) {
        Address updatedAddress = addressService.updateAddress(id, address, userId);
        return ResponseEntity.ok(updatedAddress);
    }
    
    @DeleteMapping("/{userId}/addresses/{id}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Long userId, @PathVariable Long id) {
        addressService.deleteAddress(id);
        return ResponseEntity.noContent().build();
    }
}