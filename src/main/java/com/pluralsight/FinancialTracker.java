package com.pluralsight;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import java.util.Scanner;

public class FinancialTracker {
    // niye transaction : her tipi kullanabilmek icin
    private static ArrayList<Transaction> transactions = new ArrayList<Transaction>();
    private static final String FILE_NAME = "transactions.csv"; //dosyanin adini degistirilmemesi icin.
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final String TIME_FORMAT = "HH:mm:ss";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern(TIME_FORMAT);


    public static void main(String[] args) {
        loadTransactions(FILE_NAME);
        Scanner scanner = new Scanner(System.in);
        //loop a sokmak icin
        boolean running = true;

        while (running) {
            System.out.println("Welcome to TransactionApp");
            System.out.println("Choose an option:");
            System.out.println("D) Add Deposit");
            System.out.println("P) Make Payment (Debit)");
            System.out.println("L) Ledger");
            System.out.println("X) Exit");

            String input = scanner.nextLine().trim();

            switch (input.toUpperCase()) {
                case "D":
                    addDeposit(scanner);
                    break;
                case "P":
                    addPayment(scanner);
                    break;
                case "L":
                    ledgerMenu(scanner);
                    break;
                case "X":
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option");
                    break;
            }
        }

        scanner.close();
    }

    public static void loadTransactions(String fileName) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName)); // dosyayi okumak icin
            String input;
            while ((input = bufferedReader.readLine()) != null) {
                String[] parts = input.split("\\|");
                LocalDate date = LocalDate.parse(parts[0]);
                LocalTime time = LocalTime.parse(parts[1]);
                String description = parts[2];
                String vendor = parts[3];
                double price = Double.parseDouble(parts[4]);
                transactions.add(new Transaction(date, time, description, vendor, price));
            }
            bufferedReader.close();
        } catch (Exception e) {

            System.err.println("Related file does not exist.");
        }
    }


    private static void addDeposit(Scanner scanner) {

        System.out.println("Enter the date of the deposit" + "(yyyy-MM-dd)");
        LocalDate date = LocalDate.parse(scanner.nextLine(), DATE_FORMATTER);

        System.out.println("Enter the time of the deposit (HH:mm:ss)");
        LocalTime time = LocalTime.parse(scanner.nextLine(), TIME_FORMATTER);

        System.out.println("Enter the description of the deposit");
        String description = scanner.nextLine();

        System.out.println("Enter the vendor of the deposit");
        String vendor = scanner.nextLine();

        System.out.println("Enter the amount of the deposit");
        double amount = scanner.nextDouble();

        if (amount <= 0) {
            System.out.println("invalid input");
            return;


        }


        transactions.add(new Transaction(date, time, description, vendor, amount)); // i created new transaction object and added to transaction array list.

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            bufferedWriter.write(date.format(DATE_FORMATTER) + "|" +
                    time.format(TIME_FORMATTER) + "|" +
                    description + "|" +
                    vendor + "|" +
                    amount);
            bufferedWriter.newLine(); // Yeni satır ekleyin
        } catch (IOException e) {
            System.out.println("There is an error.");
            e.printStackTrace();
        }


    }

    private static void addPayment(Scanner scanner) {

        System.out.println("Enter the date of the payment" + "(yyyy-MM-dd)");
        LocalDate date = LocalDate.parse(scanner.nextLine(), DATE_FORMATTER);

        System.out.println("Enter the time of the payment (HH:mm:ss)");
        LocalTime time = LocalTime.parse(scanner.nextLine(), TIME_FORMATTER);

        System.out.println("Enter the description of the payment");
        String description = scanner.nextLine();

        System.out.println("Enter the vendor of the payment");
        String vendor = scanner.nextLine();

        System.out.println("Enter the amount of the payment");
        double amount = scanner.nextDouble();

        if (amount <= 0) {
            System.out.println("invalid input");
            return;


        }
        double negativeAmount = -amount;


        transactions.add(new Transaction(date, time, description, vendor, negativeAmount)); // Negatif tutarı ekleyin

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            bufferedWriter.write(date.format(DATE_FORMATTER) + "|" +
                    time.format(TIME_FORMATTER) + "|" +
                    description + "|" +
                    vendor + "|" +
                    negativeAmount);
            bufferedWriter.newLine(); // Yeni satır ekleyin
        } catch (IOException e) {
            System.out.println("There is an error.");
            e.printStackTrace();
        }
    }


    private static void ledgerMenu(Scanner scanner) {
        boolean running = true;
        while (running) {
            System.out.println("Ledger");
            System.out.println("Choose an option:");
            System.out.println("A) A`ll");
            System.out.println("D) Deposits");
            System.out.println("P) Payments");
            System.out.println("R) Reports");
            System.out.println("H) Home");

            String input = scanner.nextLine().trim();

            switch (input.toUpperCase()) {
                case "A":
                    displayLedger();
                    break;
                case "D":
                    displayDeposits();
                    break;
                case "P":
                    displayPayments();
                    break;
                case "R":
                    reportsMenu(scanner);
                    break;
                case "H":
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option");
                    break;
            }
        }
    }

    private static void displayLedger() {

        System.out.println("Date|Time|Description|Vendor|Amount");
        for (Transaction transaction : transactions) {
            System.out.println(transaction);
        }
    }

    private static void displayDeposits() {

        System.out.println("Date|Time|Description|Vendor|Amount");
        for (Transaction transaction : transactions) {
            if (transaction.getAmount() > 0) {
                System.out.println(transaction);
            }
        }
    }

    private static void displayPayments() {

        System.out.println("Date|Time|Description|Vendor|Amount");
        for (Transaction transaction : transactions) {
            if (transaction.getAmount() < 0) {
                System.out.println(transaction);
            }
        }


    }

    private static void reportsMenu(Scanner scanner) {
        boolean running = true;
        while (running) {
            System.out.println("Reports");
            System.out.println("Choose an option:");
            System.out.println("1) Month To Date");
            System.out.println("2) Previous Month");
            System.out.println("3) Year To Date");
            System.out.println("4) Previous Year");
            System.out.println("5) Search by Vendor");
            System.out.println("0) Back");

            String input = scanner.nextLine().trim();

            switch (input) {
                case "1":
                    LocalDate startOfCurrentMonth = LocalDate.now().withDayOfMonth(1);
                    LocalDate today = LocalDate.now();
                    filterTransactionsByDate(startOfCurrentMonth, today);
                    break;
                case "2":
                    LocalDate startOfPreviousMonth = LocalDate.now().minusMonths(1).withDayOfMonth(1);
                    LocalDate endOfPreviousMonth = LocalDate.now().withDayOfMonth(1).minusDays(1);
                    filterTransactionsByDate(startOfPreviousMonth, endOfPreviousMonth);
                    break;
                case "3":
                    LocalDate startOfCurrentYear = LocalDate.now().withDayOfYear(1);
                    filterTransactionsByDate(startOfCurrentYear, LocalDate.now());
                    break;

                case "4":
                    LocalDate startOfPreviousYear = LocalDate.now().minusYears(1).withDayOfYear(1);
                    LocalDate endOfPreviousYear = LocalDate.now().withDayOfYear(1).minusDays(1);
                    filterTransactionsByDate(startOfPreviousYear, endOfPreviousYear);
                    break;
                case "5":
                    System.out.println("Enter vendor name: ");
                    String vendor = scanner.nextLine().trim();
                    filterTransactionsByVendor(vendor);
                    break;
                case "0":
                    running = false;
                default:
                    System.out.println("Invalid option");
                    break;
            }
        }
    }


    private static void filterTransactionsByDate(LocalDate startDate, LocalDate endDate) {

        System.out.println("Transactions between " + startDate + " - " + endDate + ": ");
        boolean running = false;
        for (Transaction transaction : transactions) {
            if (transaction.getDate().isAfter(startDate) && transaction.getDate().isBefore(endDate)) {
                System.out.println(transaction.toString());
                running = true;
            }
        }
        if (!running) {

            System.out.println("No transactions found.");


        }
    }

    private static void filterTransactionsByVendor(String vendor) {

        System.out.println("Transactions for vendor: " + vendor);
        boolean running = false;

        for (Transaction transaction : transactions) {
            if (transaction.getVendor().equalsIgnoreCase(vendor)) {
                System.out.println(transaction);
                running = true;
            }
        }

        if (!running) {
            System.out.println("No transactions found for vendor: " + vendor);
        }
    }
}