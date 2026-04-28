package com.pluralsight;

import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;

public class Program {
    static Scanner scanner = new Scanner(System.in);
    static Scanner input = new Scanner(System.in);
    static ArrayList<Transaction> transactions = new ArrayList<>();
    static String fileName = "transactions.csv";

    public static void main(String[] args) {






    }
    public static void loadTransactions() {

        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));

            String line;
            reader.readLine(); // skip header

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
