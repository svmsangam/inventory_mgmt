package com.inventory.web.error;

/**
 * Created by dhiraj on 10/10/17.
 */
public class PaymentInfoError {

    private String amount;

    private String paymentMethod;

    private String remark;

    private String chequeDate;

    private String commitedDateOfCheque;

    private String bankOfCheque;

    private String bankAccountNumber;

    private String invoice;

    private boolean valid;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getChequeDate() {
        return chequeDate;
    }

    public void setChequeDate(String chequeDate) {
        this.chequeDate = chequeDate;
    }

    public String getCommitedDateOfCheque() {
        return commitedDateOfCheque;
    }

    public void setCommitedDateOfCheque(String commitedDateOfCheque) {
        this.commitedDateOfCheque = commitedDateOfCheque;
    }

    public String getBankOfCheque() {
        return bankOfCheque;
    }

    public void setBankOfCheque(String bankOfCheque) {
        this.bankOfCheque = bankOfCheque;
    }

    public String getBankAccountNumber() {
        return bankAccountNumber;
    }

    public void setBankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }

    public String getInvoice() {
        return invoice;
    }

    public void setInvoice(String invoice) {
        this.invoice = invoice;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }
}
