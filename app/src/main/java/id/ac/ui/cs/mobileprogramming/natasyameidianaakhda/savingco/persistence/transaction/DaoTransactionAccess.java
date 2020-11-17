package id.ac.ui.cs.mobileprogramming.natasyameidianaakhda.savingco.persistence.transaction;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DaoTransactionAccess {
    @Insert
    Long createTransaction(Transaction transaction);

    @Query("SELECT * FROM `Transaction` WHERE user_id = :userId")
    LiveData<List<Transaction>> getAllTransactionByUserId(int userId);

    @Query("SELECT * FROM `Transaction` WHERE user_id=:userId and is_income=:isIncome")
    LiveData<List<Transaction>> getAllTransactionByUserIdAndIsIncome(int userId, char isIncome);

    @Delete
    void deleteTransaction(Transaction transaction);
}