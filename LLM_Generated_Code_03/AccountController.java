package com.petstore.controller;

import com.petstore.model.Account;
import com.petstore.service.AccountService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/account")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/signup")
    public Account signup(@RequestBody Account account) {
        return accountService.signup(account);
    }

    @PostMapping("/login")
    public Account login(@RequestParam String username, @RequestParam String password) {
        return accountService.login(username, password);
    }

    @PutMapping("/{username}")
    public Account editProfile(@PathVariable String username, @RequestBody Account updated) {
        return accountService.editProfile(username, updated);
    }

    @GetMapping("/{username}")
    public Account getAccount(@PathVariable String username) {
        return accountService.getAccount(username);
    }
}
