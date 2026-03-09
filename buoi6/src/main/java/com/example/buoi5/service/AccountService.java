package com.example.buoi5.service;

import com.example.buoi5.model.Account;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AccountService extends UserDetailsService {
    Account save(Account account);
}
