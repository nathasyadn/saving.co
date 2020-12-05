package id.ac.ui.cs.mobileprogramming.natasyameidianaakhda.savingco.ui.calculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import id.ac.ui.cs.mobileprogramming.natasyameidianaakhda.savingco.R;

public class CalculatorActivity extends AppCompatActivity implements View.OnClickListener{
    private CalculatorViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        viewModel = ViewModelProviders.of(this).get(CalculatorViewModel.class);

        View buttonAdd = findViewById(R.id.buttonPlus);
        View buttonMinus = findViewById(R.id.buttonMinus);
        View buttonMultiply = findViewById(R.id.buttonMultiply);
        View buttonDivide = findViewById(R.id.buttonDivide);

        buttonAdd.setOnClickListener(this);
        buttonMinus.setOnClickListener(this);
        buttonMultiply.setOnClickListener(this);
        buttonDivide.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        EditText numberOne = (EditText) findViewById(R.id.inputOne);
        String numberOneString = numberOne.getText().toString();

        EditText numberTwo = (EditText) findViewById(R.id.inputTwo);
        String numberTwoString = numberTwo.getText().toString();

        boolean flagField = true;
        Double result = 0.0;
        Double number1 = 0.0;
        Double number2 = 0.0;

        if (numberOneString.isEmpty() || numberTwoString.isEmpty()){
            Toast toast = Toast.makeText(getApplicationContext(), R.string.requiredInput,
                    Toast.LENGTH_SHORT);
            toast.show();
            flagField = false;
        } else {
            number1 = Double.parseDouble(numberOneString);
            number2 = Double.parseDouble(numberTwoString);
        }

        switch (v.getId()){
            case R.id.buttonPlus:
                if (flagField){
                    result = viewModel.add(number1, number2);
                }
                break;
            case R.id.buttonMinus:
                if (flagField){
                    result = viewModel.minus(number1, number2);
                }
                break;
            case R.id.buttonMultiply:
                if (flagField){
                    result = viewModel.multiply(number1, number2);
                }
                break;
            case R.id.buttonDivide:
                if (flagField){
                    result = viewModel.divide(number1, number2);
                }
                break;
        }
        changeResult(result);
    }

    private void changeResult(Double result){
        EditText textResult = (EditText) findViewById(R.id.result);
        textResult.setText(String.valueOf(result));
    }
}