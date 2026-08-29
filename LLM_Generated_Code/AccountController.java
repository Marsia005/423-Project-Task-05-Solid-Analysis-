package com.petstore.controller;

import com.petstore.model.Account;
import com.petstore.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/account")
public class AccountController {

    @Autowired
    private AccountRepository accountRepository;

    @PostMapping("/signup")
    public Account signup(@RequestBody Account account) {
        return accountRepository.save(account);
    }

    @PostMapping("/login")
    public Account login(@RequestParam String username, @RequestParam String password) {
        return accountRepository.findByUsernameAndPassword(username, password);
    }

    @PutMapping("/{username}")
    public Account editProfile(@PathVariable String username, @RequestBody Account updated) {
        Account existing = accountRepository.findById(username).orElseThrow();
        existing.setEmail(updated.getEmail());
        existing.setFirstName(updated.getFirstName());
        existing.setLastName(updated.getLastName());
        existing.setAddress(updated.getAddress());
        existing.setCity(updated.getCity());
        existing.setState(updated.getState());
        existing.setZip(updated.getZip());
        existing.setCountry(updated.getCountry());
        existing.setPhone(updated.getPhone());
        return accountRepository.save(existing);
    }

    @GetMapping("/{username}")
    public Account getAccount(@PathVariable String username) {
        return accountRepository.findById(username).orElseThrow();
    }
}
