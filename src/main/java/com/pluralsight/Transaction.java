package com.pluralsight;

import java.time.LocalDate;

import java.time.LocalTime;

// we have a 5 variables here
public class Transaction {
    private LocalDate date;
    private LocalTime time;
    private String description;
    private String vendor;
    private double amount;

    // bu veraybilleri diger siniftada rahatlikla kullanabilmek icin i crated for here
    // my construcker has 5 parameter
    public Transaction(LocalDate date, LocalTime time, String description, String vendor, double amount) {
        this.date = date;
        this.time = time;
        this.description = description;
        this.vendor = vendor;
        this.amount = amount;
    }

    //her variable has get and set
    // yukardaki variablller public degil bu sinifta kullanabilirim. ama get set yaparsam diger siniflardada kullanabilirim.
    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    //string olarak display edecek.
    @Override
    public String toString() {
        return "Transaction{" + "dateTime=" + date + ", description='" + description + '\'' + ", vendor='" + vendor + '\'' + ", amount=" + amount + '}';
    }
}
