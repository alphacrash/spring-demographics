package com.alphacrash.demographics.service;

import com.alphacrash.demographics.entity.Address;

import java.util.List;

public interface AddressService {
    
    List<Address> getAllAddressesByUserId(Long userId);
    
    Address getAddressById(Long id);
    
    Address createAddress(Address address, Long userId);
    
    Address updateAddress(Long id, Address address, Long userId);
    
    void deleteAddress(Long id);
}