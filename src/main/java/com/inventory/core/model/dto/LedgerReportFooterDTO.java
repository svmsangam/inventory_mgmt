package com.inventory.core.model.dto;

/**
 * Created by dhiraj on 10/24/17.
 */
public class LedgerReportFooterDTO {

    private double dr;

    private double cr;

    private double balance;

    public double getDr() {
        return dr;
    }

    public void setDr(double dr) {
        this.dr = dr;
    }

    public double getCr() {
        return cr;
    }

    public void setCr(double cr) {
        this.cr = cr;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
