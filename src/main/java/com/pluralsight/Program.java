package com.pluralsight;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Program {
    static Scanner scanner = new Scanner(System.in);
    static ArrayList<Transaction> transactions = new ArrayList<>();
    static String fileName = "transactions.csv";

    public static void main(String[] args) {
        loadTransactions();
        runHomeScreen();

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

    public static void runHomeScreen() {
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
                case "L" -> runLedgerScreen();
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
        LocalTime currentTime = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String formattedTime = currentTime.format(formatter);
        LocalTime parseTime = LocalTime.parse(formattedTime);

        System.out.print("Amount: ");
        double amount = Double.parseDouble(scanner.nextLine());
        Transaction newDeposit = new Transaction(
                LocalDate.now(),
                parseTime,
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
        LocalTime currentTime = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String formattedTime = currentTime.format(formatter);
        LocalTime parseTime = LocalTime.parse(formattedTime);

        System.out.print("Amount: ");
        double amount = Double.parseDouble(scanner.nextLine());
        amount = -Math.abs(amount);
        Transaction newPayment = new Transaction(
                LocalDate.now(),
                parseTime,
                description,
                vendor,
                amount
        );

        transactions.add(newPayment);
        saveTransaction(newPayment);
    }


    public static void runLedgerScreen(){
        boolean running = true;
        while (running){
            System.out.print("""
                    =====LEDGER=====
                  --------------------
                  
                  (A) All entries.
                  (D) Deposits.
                  (P) Payments.
                  (R) Reports.
                  (H) Home.
                  
                  Enter command here: """);
            String choice = scanner.nextLine().toUpperCase();
            switch(choice){
                case "A" -> displayAllEntries();
                case "D" -> displayDeposits();
                case "P" -> displayPayments();
                case "R" -> runReportsScreen();
                case "H" -> running = false;
                default -> System.out.println("Invalid option, please try again.");
            }

        }

    }

    public static void displayAllEntries(){
        Collections.reverse(transactions);
        for (Transaction transaction : transactions) {

            System.out.printf("%s | %s | %s | %s | $%.2f\n",
                    transaction.getDate(),
                    transaction.getTime(),
                    transaction.getDescription(),
                    transaction.getVendor(),
                    transaction.getAmount());

        }
        Collections.reverse(transactions);

    }

    public static void displayDeposits() {

        for (Transaction deposit : transactions) {

            if (deposit.getAmount() > 0) {

                System.out.printf("%s | %s | %s | %s | $%.2f\n",
                        deposit.getDate(),
                        deposit.getTime(),
                        deposit.getDescription(),
                        deposit.getVendor(),
                        deposit.getAmount());

            }

        }
    }

    public static void displayPayments() {

        for (Transaction payment : transactions) {

            if (payment.getAmount() < 0) {

                System.out.printf("%s | %s | %s | %s | $%.2f\n",
                        payment.getDate(),
                        payment.getTime(),
                        payment.getDescription(),
                        payment.getVendor(),
                        payment.getAmount());

            }

        }
    }

    public static void runReportsScreen() {
        boolean running = true;
        while (running) {

            System.out.print("""
                       =====REPORTS=====
                     --------------------
                    \s
                     (A) Month to date.
                     (B) Previous month.
                     (C) Year to date.
                     (D) Previous year.
                     (E) Search by vendor.
                     (X) Back.\s
                    \s
                     Enter command here:""");
            String choice = scanner.nextLine().toUpperCase();
            switch (choice) {
                case "A" -> getMonthToDate();
                case "B" -> getPreviousMonth();
                case "C" -> getYearToDate();
                case "D" -> getPreviousYear();
                case "E" -> searchVendor();
                case "X" -> running = false;
            }
        }
    }

    public static void getMonthToDate(){
        LocalDate today = LocalDate.now();
        LocalDate firstDayOfMonth = today.withDayOfMonth(1);
        for(Transaction transaction: transactions){
            LocalDate date = transaction.getDate();
            if(date.isAfter(firstDayOfMonth) && date.isBefore(today)){
                System.out.printf("%s | %s | %s | %s | $%.2f\n",
                        date,
                        transaction.getTime(),
                        transaction.getDescription(),
                        transaction.getVendor(),
                        transaction.getAmount());
            }
        }
    }
    public static void getPreviousMonth(){
        LocalDate today = LocalDate.now();
        LocalDate firstDayLastMonth = today.minusMonths(1).withDayOfMonth(1);
        LocalDate lastDayLastMonth = today.withDayOfMonth(1).minusDays(1);
        for (Transaction transaction : transactions){
            LocalDate date = transaction.getDate();
            if(!date.isBefore(firstDayLastMonth) && !date.isAfter(lastDayLastMonth)){
                System.out.printf("%s | %s | %s | %s | $%.2f\n",
                        date,
                        transaction.getTime(),
                        transaction.getDescription(),
                        transaction.getVendor(),
                        transaction.getAmount());
            }

        }
    }
    public static void  getYearToDate(){
        LocalDate firstDayOfYear = LocalDate.now().withDayOfYear(1);
        for (Transaction transaction : transactions){
            LocalDate date = transaction.getDate();
            if(!date.isBefore(firstDayOfYear)){
                System.out.printf("%s | %s | %s | %s | $%.2f\n",
                        date,
                        transaction.getTime(),
                        transaction.getDescription(),
                        transaction.getVendor(),
                        transaction.getAmount());
            }

        }

    }
    public static void getPreviousYear(){
        LocalDate start = LocalDate.now().minusYears(1).withDayOfYear(1);
        LocalDate end = start.withMonth(12).withDayOfMonth(31);
        for (Transaction transaction : transactions){
            LocalDate date = transaction.getDate();
            if (!date.isBefore(start) && !date.isAfter(end)){
                System.out.printf("%s | %s | %s | %s | $%.2f\n",
                        date,
                        transaction.getTime(),
                        transaction.getDescription(),
                        transaction.getVendor(),
                        transaction.getAmount());
            }
        }

    }
    public static void searchVendor(){
        System.out.print("Enter vendor name: ");
        String vendorName = scanner.nextLine();
        for (Transaction transaction : transactions){
            String vendor = transaction.getVendor();
            if(vendor.equalsIgnoreCase(vendorName)){
                System.out.printf("%s | %s | %s | %s | $%.2f\n",
                        transaction.getDate(),
                        transaction.getTime(),
                        transaction.getDescription(),
                        transaction.getVendor(),
                        transaction.getAmount());
            }
        }
    }


}
