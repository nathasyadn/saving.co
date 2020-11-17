package id.ac.ui.cs.mobileprogramming.natasyameidianaakhda.savingco.ui.createAccount;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import id.ac.ui.cs.mobileprogramming.natasyameidianaakhda.savingco.core.entity.UserLoginEntity;
import id.ac.ui.cs.mobileprogramming.natasyameidianaakhda.savingco.core.entity.UserProfileEntity;
import id.ac.ui.cs.mobileprogramming.natasyameidianaakhda.savingco.core.repository.UserLoginRepository;
import id.ac.ui.cs.mobileprogramming.natasyameidianaakhda.savingco.core.repository.UserProfileRepository;

public class CreateAccountViewModel extends AndroidViewModel {
    private UserLoginRepository userLoginRepository;
    private UserProfileRepository userProfileRepository;

    public CreateAccountViewModel(@NonNull Application application) {
        super(application);
        userLoginRepository = new UserLoginRepository(application);
        userProfileRepository = new UserProfileRepository(application);
    }

    public void createUser(String username, String password) {
        UserLoginEntity entity = new UserLoginEntity();
        entity.setUsername(username);
        entity.setPassword(password);
        userLoginRepository.createUser(entity);
    }

    public void createUserProfile(String name, String numberPhone) {
        UserProfileEntity entity = new UserProfileEntity();
        entity.setName(name);
        entity.setNumberPhone(numberPhone);
        userProfileRepository.createUserProfile(entity);
    }
}
