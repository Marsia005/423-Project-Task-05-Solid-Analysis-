package com.petstore.service;

import com.petstore.model.Account;
import com.petstore.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public Account signup(Account account) {
        return accountRepository.save(account);
    }

    public Account login(String username, String password) {
        return accountRepository.findByUsernameAndPassword(username, password);
    }

    public Account editProfile(String username, Account updated) {
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

    public Account getAccount(String username) {
        return accountRepository.findById(username).orElseThrow();
    }
}
