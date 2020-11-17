package id.ac.ui.cs.mobileprogramming.natasyameidianaakhda.savingco.ui.account;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import id.ac.ui.cs.mobileprogramming.natasyameidianaakhda.savingco.core.repository.UserProfileRepository;
import id.ac.ui.cs.mobileprogramming.natasyameidianaakhda.savingco.persistence.userProfile.UserProfile;
import id.ac.ui.cs.mobileprogramming.natasyameidianaakhda.savingco.util.SavingcoConstant;

public class AccountViewModel extends AndroidViewModel {
    private UserProfileRepository userProfileRepository;
    private LiveData<UserProfile> profile;
    private int userId;

    public AccountViewModel(Application application) {
        super(application);
        SharedPreferences sharedpreferences = this.getApplication().getSharedPreferences(SavingcoConstant.PREFERENCES_NAME, Context.MODE_PRIVATE);
        userId = Integer.parseInt(sharedpreferences.getString(SavingcoConstant.KEY_USER_ID, ""));

        userProfileRepository = new UserProfileRepository(application, userId);
        profile = userProfileRepository.getUserProfileById();
    }

    public void editUserProfile(UserProfile userProfile) {
        userProfile.setId(userId);
        userProfileRepository.updateUserProfile(userProfile);
    }

    public LiveData<UserProfile> getUserProfile() {
        return profile;
    }
}