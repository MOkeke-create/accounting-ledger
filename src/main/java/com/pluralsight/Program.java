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
    static DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String RESET = "\u001B[0m";

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
              \s
               Enter command here:\s""");

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
        System.out.println("Adding deposit....");
        System.out.println("Deposit added.");


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

        System.out.println("Payment was successful.");
    }


    public static void runLedgerScreen(){
        boolean running = true;
        while (running){
            System.out.print("""
                    =====LEDGER=====
                  --------------------
                 \s
                  (A) All entries.
                  (D) Deposits.
                  (P) Payments.
                  (R) Reports.
                  (H) Home.
                 \s
                  Enter command here:\s""");
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

        printHeader();

        for (Transaction transaction : transactions) {
            printTransaction(transaction);

        }
        Collections.reverse(transactions);

    }

    public static void displayDeposits() {

        printHeader();

        for (Transaction deposit : transactions) {

            if (deposit.getAmount() > 0) {
                printTransaction(deposit);
            }
        }
    }

    public static void displayPayments() {

        printHeader();

        for (Transaction payment : transactions) {

            if (payment.getAmount() < 0) {
                printTransaction(payment);
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
                     (F) Custom search.
                     (X) Back.
                     (H) Home.\s
                    \s
                     Enter command here:""");
            String choice = scanner.nextLine().toUpperCase();
            switch (choice) {
                case "A" -> getMonthToDate();
                case "B" -> getPreviousMonth();
                case "C" -> getYearToDate();
                case "D" -> getPreviousYear();
                case "E" -> searchVendor();
                case "F" -> runCustomSearch();
                case "X" -> running = false;
                case "H" -> runHomeScreen();
            }
        }
    }

    public static void getMonthToDate(){
        printHeader();

        LocalDate today = LocalDate.now();
        LocalDate firstDayOfMonth = today.withDayOfMonth(1);

        for (Transaction transaction : transactions) {

            LocalDate date = transaction.getDate();

            if (!date.isBefore(firstDayOfMonth) && !date.isAfter(today)) {
                printTransaction(transaction);
            }
        }
    }
    public static void getPreviousMonth(){
        printHeader();

        LocalDate today = LocalDate.now();
        LocalDate firstDayLastMonth = today.minusMonths(1).withDayOfMonth(1);
        LocalDate lastDayLastMonth = today.withDayOfMonth(1).minusDays(1);

        for (Transaction transaction : transactions) {

            LocalDate date = transaction.getDate();

            if (!date.isBefore(firstDayLastMonth) && !date.isAfter(lastDayLastMonth)) {
                printTransaction(transaction);
            }
        }
    }
    public static void  getYearToDate(){
        printHeader();

        LocalDate firstDayOfYear = LocalDate.now().withDayOfYear(1);

        for (Transaction transaction : transactions) {

            if (!transaction.getDate().isBefore(firstDayOfYear)) {
                printTransaction(transaction);
            }
        }

    }
    public static void getPreviousYear(){
        printHeader();

        LocalDate start = LocalDate.now().minusYears(1).withDayOfYear(1);
        LocalDate end = start.withMonth(12).withDayOfMonth(31);

        for (Transaction transaction : transactions) {

            LocalDate date = transaction.getDate();

            if (!date.isBefore(start) && !date.isAfter(end)) {
                printTransaction(transaction);
            }
        }

    }
    public static void searchVendor(){
        printHeader();

        System.out.print("Enter vendor name: ");
        String vendorName = scanner.nextLine();

        for (Transaction transaction : transactions) {

            if (transaction.getVendor().equalsIgnoreCase(vendorName)) {
                printTransaction(transaction);
            }
        }
    }
    public static void runCustomSearch(){
        printHeader();
        System.out.print("Start Date (yyyy-mm-dd) or press Enter to skip: ");
        String startDateInput = scanner.nextLine();

        System.out.print("End Date (yyyy-mm-dd) or press Enter to skip: ");
        String endDateInput = scanner.nextLine();

        System.out.print("Description or press Enter to skip: ");
        String description = scanner.nextLine().toLowerCase();

        System.out.print("Vendor or press Enter to skip: ");
        String vendor = scanner.nextLine().toLowerCase();

        System.out.print("Minimum Amount or press Enter to skip: ");
        String minAmountInput = scanner.nextLine();

        System.out.print("Maximum Amount or press Enter to skip: ");
        String maxAmountInput = scanner.nextLine();

        LocalDate startDate = null;
        LocalDate endDate = null;
        Double minAmount = null;
        Double maxAmount = null;

        if (!startDateInput.isEmpty()) {
            startDate = LocalDate.parse(startDateInput);
        }

        if (!endDateInput.isEmpty()) {
            endDate = LocalDate.parse(endDateInput);
        }

        if (!minAmountInput.isEmpty()) {
            minAmount = Double.parseDouble(minAmountInput);
        }

        if (!maxAmountInput.isEmpty()) {
            maxAmount = Double.parseDouble(maxAmountInput);
        }
        for(Transaction transaction : transactions){
            boolean match = true;
            if(startDate != null && transaction.getDate().isBefore(startDate)){
                match = false;
            }
            if (endDate != null && transaction.getDate().isAfter(endDate)){
                match = false;
            }
            if (!description.isEmpty() && !transaction.getDescription().toLowerCase().contains(description)){
                match = false;
            }
            if(!vendor.isEmpty() && !transaction.getVendor().toLowerCase().contains(vendor)){
                match = false;
            }
            double transactionAmount = Math.abs(transaction.getAmount());
            if (minAmount != null && transactionAmount < minAmount) {
                match = false;
            }

            if (maxAmount != null && transactionAmount > maxAmount) {
                match= false;
            }
            if(match){
                printTransaction(transaction);
            }

        }
    }
    public static void printHeader() {
        System.out.printf("%-12s %-10s %-25s %-20s %12s\n",
                "DATE", "TIME", "DESCRIPTION", "VENDOR", "AMOUNT");

        System.out.println("----------------------------------------------------------------------------------------");
    }
    public static void printTransaction(Transaction transaction) {
        double amount = transaction.getAmount();
        String color = amount < 0 ? RED : GREEN;

        System.out.printf("%s%s | %s | %s | %s | $%.2f%s\n",
                color,
                transaction.getDate(),
                transaction.getTime(),
                transaction.getDescription(),
                transaction.getVendor(),
                amount,
                RESET);
    }


}
