package com.example.mybankaccount.controller;

import com.example.mybankaccount.models.Customer;
import com.example.mybankaccount.repo.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CustomerController {

    @Autowired
    CustomerRepository customerRepository;

    @GetMapping("/api/v1/customer")
    public List<Customer> findAllCustomers(
            @RequestParam(name="email",required=false) String email) {

        if (email!=null) {
            return (List<Customer>)customerRepository.findCustomerByEmail(email);
        }

        return (List<Customer>)customerRepository.findAll();
    }

    @GetMapping("/api/v1/customer/{userId}")
    public Customer findCustomerById(@PathVariable("userId") int id) {

        return customerRepository.findById(id).orElse(null);
    }


    @PostMapping("/api/v1/customer")
    public Customer createCustomer(@RequestBody Customer customer) {

        return customerRepository.save(customer);
    }

}