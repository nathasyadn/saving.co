package id.ac.ui.cs.mobileprogramming.natasyameidianaakhda.savingco.ui.createTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import id.ac.ui.cs.mobileprogramming.natasyameidianaakhda.savingco.R;
import id.ac.ui.cs.mobileprogramming.natasyameidianaakhda.savingco.ui.home.HomeActivity;
import id.ac.ui.cs.mobileprogramming.natasyameidianaakhda.savingco.util.SavingcoConstant;

public class CreateTransactionActivity extends AppCompatActivity implements View.OnClickListener {
    private CreateTransactionViewModel viewModel;
    private int userId;
    private Boolean isIncome = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_transaction);
        SharedPreferences sharedpreferences = this.getApplication().getSharedPreferences(SavingcoConstant.PREFERENCES_NAME, Context.MODE_PRIVATE);
        userId = Integer.parseInt(sharedpreferences.getString(SavingcoConstant.KEY_USER_ID, ""));
        viewModel = ViewModelProviders.of(this).get(CreateTransactionViewModel.class);

        final View createTransaction = findViewById(R.id.buttonCreateTransactionData);
        createTransaction.setOnClickListener(this);
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case R.id.radioButtonIncome:
                if (checked) {
                    isIncome = true;
                }
                break;
            case R.id.radioButtonOutcome:
                if (checked) {
                    isIncome = false;
                }
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonCreateTransactionData:
                final EditText inputAmount = (EditText) findViewById(R.id.editAmount);
                final EditText inputNotes = (EditText) findViewById(R.id.editNotes);

                String amountString = inputAmount.getText().toString();
                String notes = inputNotes.getText().toString();

                if (amountString.isEmpty()) {
                    Toast toast = Toast.makeText(getApplicationContext(), R.string.toastTransaction,
                            Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    double amount = Double.parseDouble(amountString);
                    char income = isIncome ? 'Y' : 'N';

                    viewModel.createTransaction(userId, income, amount, notes);
                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
                break;
        }
    }
}