package id.ac.ui.cs.mobileprogramming.natasyameidianaakhda.savingco.ui.login;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import id.ac.ui.cs.mobileprogramming.natasyameidianaakhda.savingco.core.repository.UserLoginRepository;
import id.ac.ui.cs.mobileprogramming.natasyameidianaakhda.savingco.persistence.userLogin.UserLogin;

public class LoginViewModel extends AndroidViewModel {
    private UserLoginRepository userLoginRepository;

    public LoginViewModel(Application application) {
        super(application);
        userLoginRepository = new UserLoginRepository(application);
    }

    public LiveData<UserLogin> getUserLoginByUsernameAndPassword(String username, String password) {
        return userLoginRepository.getUserLoginByUsernameAndPassword(username, password);
    }
}
