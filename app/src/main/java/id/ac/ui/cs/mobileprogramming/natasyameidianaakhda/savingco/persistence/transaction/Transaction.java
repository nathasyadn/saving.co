package id.ac.ui.cs.mobileprogramming.natasyameidianaakhda.savingco.persistence.transaction;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

@Entity
public class Transaction implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int transactionId;

    @ColumnInfo(name = "user_id")
    private int userId;

    @ColumnInfo(name = "is_income")
    private char isIncome;

    @ColumnInfo(name = "amount")
    private double amount;

    @ColumnInfo(name = "notes")
    private String notes;

    @ColumnInfo(name = "created_at")
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

    public String getAmountString() {
        DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();

        formatRp.setCurrencySymbol("Rp. ");
        formatRp.setMonetaryDecimalSeparator(',');
        formatRp.setGroupingSeparator('.');

        kursIndonesia.setDecimalFormatSymbols(formatRp);
        return kursIndonesia.format(amount);
    }

    public String getCreatedAtString(){
        return createdAt.substring(0,10);
    }
}