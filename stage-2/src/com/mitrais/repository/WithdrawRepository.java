package com.mitrais.repository;

import com.mitrais.entity.Withdraw;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class WithdrawRepository {
    private static final String PATH_FILE = "withdraw.csv";
    private static Logger logger = Logger.getLogger(WithdrawRepository.class.getName());

    private WithdrawRepository(){}

    public static List<Withdraw> findAll(String pathFile){
        List<Withdraw> result = new ArrayList<>();
        try {
            List<String> data = Files.readAllLines(Paths.get(pathFile), StandardCharsets.UTF_8);

            result = data
                    .stream()
                    .skip(1)
                    .map(value -> value.split(","))
                    .map(column -> new Withdraw(column[0], AccountRepository.findByAccountNumber(column[1]), Integer.parseInt(column[2]), LocalDateTime.parse(column[3])))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static boolean write(Withdraw withdraw) {
        try (FileWriter withdrawFile = new FileWriter(PATH_FILE, true);){
            StringBuilder newWithdraw = new StringBuilder();
            newWithdraw.append(withdraw.getId()+",");
            newWithdraw.append(withdraw.getAccount().getAccountNumber()+",");
            newWithdraw.append(withdraw.getAmount()+",");
            newWithdraw.append(withdraw.getDate());

            withdrawFile.append(System.lineSeparator());
            withdrawFile.append(newWithdraw);
            withdrawFile.flush();

            return true;
        } catch (IOException e) {
            logger.log(Level.WARNING, "Failed write CSV!");
            return false;
        }
    }

    public static void reset(){
        try (FileWriter withdrawFile = new FileWriter(PATH_FILE, false)){
            withdrawFile.write("Id,Account,Amount,Date");
            withdrawFile.flush();
        } catch (IOException e) {
//            logger.log(Level.WARNING, "Failed reset CSV!");
        }
    }
}
