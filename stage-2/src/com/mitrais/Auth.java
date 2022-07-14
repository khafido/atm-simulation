package com.mitrais;

import com.mitrais.entity.Account;
import com.mitrais.repository.AccountRepository;

import java.util.List;
import java.util.stream.Collectors;

public class Auth {
    private static Account loggedAccount;
    private Auth(){}

    public static Account getLoggedAccount() {
        return loggedAccount;
    }

    public static void setLoggedAccount(Account newUser) {
        loggedAccount = newUser;
    }

    public static Account login(String accountNumber, String pin){
        List<Account> accounts = AccountRepository.findAll().stream().filter(a -> a.getAccountNumber().equals(accountNumber) && a.getPin().equals(pin)).collect(Collectors.toList());

        if (!accounts.isEmpty()){
            Account account = accounts.get(0);
            setLoggedAccount(account);
            return account;
        }
        return null;
    }
}
