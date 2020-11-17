package id.ac.ui.cs.mobileprogramming.natasyameidianaakhda.savingco.ui.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import id.ac.ui.cs.mobileprogramming.natasyameidianaakhda.savingco.R;
import id.ac.ui.cs.mobileprogramming.natasyameidianaakhda.savingco.persistence.userLogin.UserLogin;
import id.ac.ui.cs.mobileprogramming.natasyameidianaakhda.savingco.ui.createAccount.CreateAccountActivity;
import id.ac.ui.cs.mobileprogramming.natasyameidianaakhda.savingco.ui.home.HomeActivity;
import id.ac.ui.cs.mobileprogramming.natasyameidianaakhda.savingco.util.SavingcoConstant;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private LoginViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedpreferences = this.getApplication().getSharedPreferences(SavingcoConstant.PREFERENCES_NAME, Context.MODE_PRIVATE);
        String userLogin = sharedpreferences.getString(SavingcoConstant.KEY_USER_ID, "");
        if (userLogin != null && !userLogin.isEmpty()) {
            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }

        setContentView(R.layout.activity_login);
        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        final View createAccount = findViewById(R.id.button_register);
        final View loginButton = findViewById(R.id.button_login);

        createAccount.setOnClickListener(this);
        loginButton.setOnClickListener(this);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_register:
                Intent intent = new Intent(this, CreateAccountActivity.class);
                startActivity(intent);
                break;
            case R.id.button_login:
                final EditText inputUsername = (EditText) findViewById(R.id.field_username);
                final EditText inputPassword = (EditText) findViewById(R.id.field_password);

                String username = inputUsername.getText().toString();
                String password = inputPassword.getText().toString();

                viewModel.getUserLoginByUsernameAndPassword(username, password).observe(this, new Observer<UserLogin>() {
                    @Override
                    public void onChanged(@Nullable UserLogin user) {
                        if (user != null) {
                            SharedPreferences sharedpreferences = getSharedPreferences(SavingcoConstant.PREFERENCES_NAME, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.putString(SavingcoConstant.KEY_USER_ID, String.valueOf(user.getId()));
                            editor.apply();

                            Toast toast = Toast.makeText(getApplicationContext(), R.string.toastLoginSuccess,
                                    Toast.LENGTH_SHORT);
                            toast.show();

                            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        } else {
                            Toast toast = Toast.makeText(getApplicationContext(), R.string.loginFailed,
                                    Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    }
                });
                break;
        }
    }
}