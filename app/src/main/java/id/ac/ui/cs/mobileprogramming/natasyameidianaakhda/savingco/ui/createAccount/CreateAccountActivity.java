package id.ac.ui.cs.mobileprogramming.natasyameidianaakhda.savingco.ui.createAccount;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import id.ac.ui.cs.mobileprogramming.natasyameidianaakhda.savingco.R;
import id.ac.ui.cs.mobileprogramming.natasyameidianaakhda.savingco.ui.login.LoginActivity;

public class CreateAccountActivity extends AppCompatActivity implements View.OnClickListener {
    private CreateAccountViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        viewModel = new ViewModelProvider(this).get(CreateAccountViewModel.class);

        final View createAccount = findViewById(R.id.button_create);
        createAccount.setOnClickListener(this);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_create:
                createUser();
                break;
        }
    }

    private void createUser() {
        final EditText editUsername = (EditText) findViewById(R.id.field_username);
        final EditText editPassword = (EditText) findViewById(R.id.field_password);
        final EditText editName = (EditText) findViewById(R.id.field_name);
        final EditText editNumberPhone = (EditText) findViewById(R.id.field_phone);

        String username = editUsername.getText().toString();
        String password = editPassword.getText().toString();
        String name = editName.getText().toString();
        String phone = editNumberPhone.getText().toString();

        if (username.isEmpty() || password.isEmpty() || name.isEmpty() || phone.isEmpty()) {
            Toast toast = Toast.makeText(getApplicationContext(), R.string.required_field,
                    Toast.LENGTH_SHORT);
            toast.show();
        } else {
            viewModel.createUser(username, password);
            viewModel.createUserProfile(name, phone);

            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

        }
    }
}