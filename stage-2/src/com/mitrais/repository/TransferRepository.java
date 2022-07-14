package com.mitrais.repository;

import com.mitrais.entity.Account;
import com.mitrais.entity.Transfer;

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

public class TransferRepository {
    private static final String PATH_FILE = "transfer.csv";
    private static Logger logger = Logger.getLogger(TransferRepository.class.getName());

    public List<Transfer> findAll(){
        List<Transfer> result = new ArrayList<>();
        try {
            List<String> data = Files.readAllLines(Paths.get(PATH_FILE), StandardCharsets.UTF_8);

            result = data
                    .stream()
                    .skip(1)
                    .map(value -> value.split(","))
                    .map(column -> {
                        Account origin = AccountRepository.findByAccountNumber(column[1]);
                        Account destination = AccountRepository.findByAccountNumber(column[2]);
                        return new Transfer(column[0], origin, destination, Integer.parseInt(column[3]), LocalDateTime.parse(column[4]));
                    })
                    .collect(Collectors.toList());
        } catch (IOException e) {
            logger.log(Level.WARNING, "Failed Read CSV!");
        }
        return result;
    }

    public static boolean write(Transfer transfer) {
        try (FileWriter withdrawFile = new FileWriter(PATH_FILE, true)){
            StringBuilder newWithdraw = new StringBuilder();
            newWithdraw.append(transfer.getId() + ",");
            newWithdraw.append(transfer.getOrigin().getAccountNumber() + ",");
            newWithdraw.append(transfer.getDestination().getAccountNumber() + ",");
            newWithdraw.append(transfer.getAmount() + ",");
            newWithdraw.append(transfer.getDate());

            withdrawFile.append(System.lineSeparator());
            withdrawFile.append(newWithdraw);
            withdrawFile.flush();

            return true;
        } catch (IOException e) {
            logger.log(Level.WARNING, "Failed Write CSV!");
            return false;
        }
    }

    public static void reset(){
        try (FileWriter withdrawFile = new FileWriter(PATH_FILE, false);){
            withdrawFile.write("Id,Origin,Destination,Amount,Date");
            withdrawFile.flush();
        } catch (IOException e) {
//            logger.log(Level.WARNING, "Failed reset CSV!");
        }
    }
}
