package id.ac.ui.cs.mobileprogramming.natasyameidianaakhda.savingco.core.entity;

public class TransactionEntity {
    private int transactionId;
    private int userId;
    private char isIncome;
    private double amount;
    private String notes;
    private String createdAt;

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public char getIsIncome() {
        return isIncome;
    }

    public void setIsIncome(char isIncome) {
        this.isIncome = isIncome;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}