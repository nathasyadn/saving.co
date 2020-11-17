package id.ac.ui.cs.mobileprogramming.natasyameidianaakhda.savingco.core.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

import id.ac.ui.cs.mobileprogramming.natasyameidianaakhda.savingco.core.entity.TransactionEntity;
import id.ac.ui.cs.mobileprogramming.natasyameidianaakhda.savingco.persistence.SavingcoDatabase;
import id.ac.ui.cs.mobileprogramming.natasyameidianaakhda.savingco.persistence.transaction.DaoTransactionAccess;
import id.ac.ui.cs.mobileprogramming.natasyameidianaakhda.savingco.persistence.transaction.Transaction;
import id.ac.ui.cs.mobileprogramming.natasyameidianaakhda.savingco.util.SavingcoConstant;
import id.ac.ui.cs.mobileprogramming.natasyameidianaakhda.savingco.util.Utilities;

public class TransactionRepository {
    private DaoTransactionAccess daoTransactionAccess;
    private LiveData<List<Transaction>> incomeList;
    private LiveData<List<Transaction>> outcomeList;

    public TransactionRepository(Application application) {
        SavingcoDatabase db = SavingcoDatabase.getDatabase(application);
        daoTransactionAccess = db.daoTransactionAccess();
    }

    public TransactionRepository(Application application, int userId) {
        SavingcoDatabase db = SavingcoDatabase.getDatabase(application);
        daoTransactionAccess = db.daoTransactionAccess();
        incomeList = daoTransactionAccess.getAllTransactionByUserIdAndIsIncome(userId, SavingcoConstant.IS_INCOME);
        outcomeList = daoTransactionAccess.getAllTransactionByUserIdAndIsIncome(userId, SavingcoConstant.IS_OUTCOME);
    }

    public void createTransaction(TransactionEntity entity) {
        Transaction transaction = new Transaction();
        transaction.setUserId(entity.getUserId());
        transaction.setIsIncome(entity.getIsIncome());
        transaction.setAmount(entity.getAmount());
        transaction.setNotes(entity.getNotes());
        transaction.setCreatedAt(Utilities.getCurrentTimestamp());
        new insertTransactionAsync(daoTransactionAccess).execute(transaction);
    }

    public LiveData<List<Transaction>> getAllIncome() {
        return incomeList;
    }

    public LiveData<List<Transaction>> getAllOutcome() {
        return outcomeList;
    }

    private static class insertTransactionAsync extends AsyncTask<Transaction, Void, Long> {

        private DaoTransactionAccess daoTransactionAccess;

        insertTransactionAsync(DaoTransactionAccess daoTransactionAccess) {
            this.daoTransactionAccess = daoTransactionAccess;
        }

        @Override
        protected Long doInBackground(Transaction... transactions) {
            long id = daoTransactionAccess.createTransaction(transactions[0]);
            return id;
        }

        @Override
        protected void onPostExecute(Long aLong) {
            super.onPostExecute(aLong);
        }
    }
}