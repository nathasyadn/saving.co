package id.ac.ui.cs.mobileprogramming.natasyameidianaakhda.savingco.ui.createTransaction;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import id.ac.ui.cs.mobileprogramming.natasyameidianaakhda.savingco.core.entity.TransactionEntity;
import id.ac.ui.cs.mobileprogramming.natasyameidianaakhda.savingco.core.repository.TransactionRepository;

public class CreateTransactionViewModel extends AndroidViewModel {
    private TransactionRepository transactionRepository;

    public CreateTransactionViewModel(Application application) {
        super(application);
        transactionRepository = new TransactionRepository(application);
    }

    public void createTransaction(int userId, char isIncome, double amount, String notes) {
        TransactionEntity entity = new TransactionEntity();
        entity.setUserId(userId);
        entity.setIsIncome(isIncome);
        entity.setAmount(amount);
        entity.setNotes(notes);
        transactionRepository.createTransaction(entity);
    }
}