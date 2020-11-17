package id.ac.ui.cs.mobileprogramming.natasyameidianaakhda.savingco.ui.home;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import id.ac.ui.cs.mobileprogramming.natasyameidianaakhda.savingco.core.repository.TransactionRepository;
import id.ac.ui.cs.mobileprogramming.natasyameidianaakhda.savingco.persistence.transaction.Transaction;
import id.ac.ui.cs.mobileprogramming.natasyameidianaakhda.savingco.util.SavingcoConstant;

public class HomeViewModel extends AndroidViewModel {
    private TransactionRepository transactionRepository;
    private final LiveData<List<Transaction>> incomeList;
    private final LiveData<List<Transaction>> outcomeList;
    private int userId;


    public HomeViewModel(Application application) {
        super(application);
        SharedPreferences sharedpreferences = this.getApplication().getSharedPreferences(SavingcoConstant.PREFERENCES_NAME, Context.MODE_PRIVATE);
        userId = Integer.parseInt(sharedpreferences.getString(SavingcoConstant.KEY_USER_ID,""));

        transactionRepository = new TransactionRepository(application, userId);
        incomeList = transactionRepository.getAllIncome();
        outcomeList = transactionRepository.getAllOutcome();
    }

    public LiveData<List<Transaction>> getTransactionIncome(){
        return incomeList;
    }

    public LiveData<List<Transaction>> getTransactionOutcome(){
        return outcomeList;
    }
}