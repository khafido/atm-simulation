package com.mitrais.repository;

import com.mitrais.entity.Account;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class AccountRepository {
    private static List<Account> accounts = new ArrayList<>();

    private static final String PATH_FILE = "data.csv";

    private AccountRepository (){}

    public static List<Account> readFile(){
        List<Account> result = new ArrayList<>();
        try {
            List<String> data = Files.readAllLines(Paths.get(PATH_FILE), StandardCharsets.UTF_8);

            result = data
                    .stream()
                    .skip(1)
                    .map(value -> value.split(","))
                    .map(column -> {
                        Account newAccount = new Account(column[0], column[1], column[2], Integer.parseInt(column[3]));

                        if (findByAccountNumber(newAccount.getAccountNumber())!=null){
                            System.out.println("Account number must be unique!");
                            System.exit(0);
                        }
                        save(newAccount);

                        return newAccount;
                    }).collect(toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static List<Account> findAll(){
        return accounts;
    }

    public static void save(Account account){
        accounts.add(account);
    }

    public static Account findByAccountNumber(String accountNumber){
        var result = accounts.stream().filter(v -> v.getAccountNumber().equalsIgnoreCase(accountNumber)).collect(toList());

        if (!result.isEmpty()){
            return result.get(0);
        } else {
            return null;
        }
    }
}
