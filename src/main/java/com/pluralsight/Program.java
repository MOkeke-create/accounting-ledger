package com.pluralsight;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Program {
    static Scanner scanner = new Scanner(System.in);
    static ArrayList<Transaction> transactions = new ArrayList<>();
    static String fileName = "transactions.csv";

    public static void main(String[] args) {
        loadTransactions();
        displayHomeScreen();

    }

    public static void loadTransactions() {

        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));

            String line;
            reader.readLine();

            while ((line = reader.readLine()) != null) {

                String[] parts = line.split("\\|");

                LocalDate date = LocalDate.parse(parts[0]);
                LocalTime time = LocalTime.parse(parts[1]);
                String description = parts[2];
                String vendor = parts[3];
                double amount = Double.parseDouble(parts[4]);

                Transaction transaction = new Transaction(date, time, description, vendor, amount);

                transactions.add(transaction);
            }
            reader.close();

        } catch (Exception e) {
            System.out.println("Error loading file.");
        }
    }

    public static void saveTransaction(Transaction transaction) {

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));

            writer.write(
                    transaction.getDate() + "|" +
                            transaction.getTime() + "|" +
                            transaction.getDescription() + "|" +
                            transaction.getVendor() + "|" +
                            transaction.getAmount()
            );

            writer.newLine();
            writer.close();

        } catch (Exception e) {
            System.out.println("Error writing to file.");
        }

    }

    public static void displayHomeScreen() {
        boolean running = true;

        while (running) {

            System.out.print("""
                =====HOME SCREEN=====
               -----------------------
               (D) Add deposit.
               (P) Make a payment.
               (L) Access ledger.
               (X) Exit.
               
               Enter command here: """);

            String choice = scanner.nextLine().toUpperCase();

            switch (choice){
                case "D" -> addDeposit();
                case "P" -> makePayment();
                case "L" -> displayLedgerScreen();
                case "X" -> running = false;
                default -> System.out.println("Invalid option, please try again!");
            }
        }

    }

    public static void addDeposit(){
        System.out.print("Description: ");
        String description = scanner.nextLine();

        System.out.print("Vendor: ");
        String vendor = scanner.nextLine();

        System.out.print("Amount: ");
        double amount = Double.parseDouble(scanner.nextLine());
        Transaction newDeposit = new Transaction(
                LocalDate.now(),
                LocalTime.now(),
                description,
                vendor,
                amount
        );
        transactions.add(newDeposit);
        saveTransaction(newDeposit);


    }

    public static void makePayment(){
        System.out.print("Description: ");
        String description = scanner.nextLine();

        System.out.print("Vendor: ");
        String vendor = scanner.nextLine();

        System.out.print("Amount: ");
        double amount = Double.parseDouble(scanner.nextLine());
        amount = -Math.abs(amount);
        Transaction newPayment = new Transaction(
                LocalDate.now(),
                LocalTime.now(),
                description,
                vendor,
                amount
        );

        transactions.add(newPayment);
        saveTransaction(newPayment);
    }


    public static void displayLedgerScreen(){
        boolean running = true;
        while (running){
            System.out.print("""
                    =====LEDGER=====
                  --------------------
                  
                  (A) All entries.
                  (D) Deposits.
                  (P) Payments.
                  (H) Home.
                  
                  Enter command here: """);
            String choice = scanner.nextLine().toUpperCase();
            switch(choice){
                case "A" -> displayAllEntries();
                case "D" -> displayDeposits();
                case "P" -> displayPayments();
                case "H" -> running = false;
                default -> System.out.println("Invalid option, please try again.");
            }

        }

    }

    public static void displayAllEntries(){
        Collections.reverse(transactions);
        for (Transaction transaction : transactions) {

            System.out.printf("%s | %s | %s | %s | %.2f\n",
                    transaction.getDate(),
                    transaction.getTime(),
                    transaction.getDescription(),
                    transaction.getVendor(),
                    transaction.getAmount());

        }
        Collections.reverse(transactions);

    }







}
