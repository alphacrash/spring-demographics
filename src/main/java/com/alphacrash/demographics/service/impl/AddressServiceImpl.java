package com.alphacrash.demographics.service.impl;

import com.alphacrash.demographics.entity.Address;
import com.alphacrash.demographics.entity.User;
import com.alphacrash.demographics.exception.ResourceNotFoundException;
import com.alphacrash.demographics.repository.AddressRepository;
import com.alphacrash.demographics.repository.UserRepository;
import com.alphacrash.demographics.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {
    
    @Autowired
    private AddressRepository addressRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Override
    public List<Address> getAllAddressesByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        return user.getAddresses();
    }
    
    @Override
    public Address getAddressById(Long id) {
        return addressRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Address", "id", id));
    }
    
    @Override
    public Address createAddress(Address address, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        address.setUser(user);
        return addressRepository.save(address);
    }
    
    @Override
    public Address updateAddress(Long id, Address address, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        Address existingAddress = addressRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Address", "id", id));
        if (!existingAddress.getUser().equals(user)) {
            throw new ResourceNotFoundException("Address", "id", id);
        }
        existingAddress.setStreet(address.getStreet());
        existingAddress.setCity(address.getCity());
        existingAddress.setState(address.getState());
        existingAddress.setZipCode(address.getZipCode());
        return addressRepository.save(existingAddress);
    }
    
    @Override
    public void deleteAddress(Long id) {
        addressRepository.deleteById(id);
    }
}