package com.example.demo.controller;

import com.example.demo.model.Address;
import com.example.demo.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class AddressController {

    @Autowired
    private AddressRepository addressRepository;

    @GetMapping("/{addressStr}")
    public Address processAddress(@PathVariable String addressStr) {
        return addressRepository.getAddressFromAddressString(addressStr);
    }

}