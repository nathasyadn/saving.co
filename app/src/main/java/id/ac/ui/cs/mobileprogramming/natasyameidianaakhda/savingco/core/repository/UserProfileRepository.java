package id.ac.ui.cs.mobileprogramming.natasyameidianaakhda.savingco.core.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import id.ac.ui.cs.mobileprogramming.natasyameidianaakhda.savingco.core.entity.UserProfileEntity;
import id.ac.ui.cs.mobileprogramming.natasyameidianaakhda.savingco.persistence.SavingcoDatabase;
import id.ac.ui.cs.mobileprogramming.natasyameidianaakhda.savingco.persistence.userProfile.DaoUserProfileAccess;
import id.ac.ui.cs.mobileprogramming.natasyameidianaakhda.savingco.persistence.userProfile.UserProfile;
import id.ac.ui.cs.mobileprogramming.natasyameidianaakhda.savingco.util.Utilities;

public class UserProfileRepository {
    private DaoUserProfileAccess daoUserProfileAccess;
    private LiveData<UserProfile> profile;

    public UserProfileRepository(Application application) {
        SavingcoDatabase db = SavingcoDatabase.getDatabase(application);
        daoUserProfileAccess = db.daoUserProfileAccess();
    }

    public UserProfileRepository(Application application, int userId) {
        SavingcoDatabase db = SavingcoDatabase.getDatabase(application);
        daoUserProfileAccess = db.daoUserProfileAccess();
        profile = daoUserProfileAccess.getUserProfileById(userId);
    }

    public void createUserProfile(UserProfileEntity entity) {
        UserProfile userProfile = new UserProfile();
        userProfile.setName(entity.getName());
        userProfile.setNumberPhone(entity.getNumberPhone());
        userProfile.setCreatedAt(Utilities.getCurrentTimestamp());
        userProfile.setModifiedAt(Utilities.getCurrentTimestamp());
        new insertUserProfileAsync(daoUserProfileAccess).execute(userProfile);
    }

    public void updateUserProfile(final UserProfile userProfile) {
        new updateUserProfileAsync(daoUserProfileAccess).execute(userProfile);
    }

    public LiveData<UserProfile> getUserProfileById() {
        return profile;
    }

    private static class insertUserProfileAsync extends AsyncTask<UserProfile, Void, Long> {

        private DaoUserProfileAccess daoUserProfileAccess;

        insertUserProfileAsync(DaoUserProfileAccess daoUserProfileAccess) {
            this.daoUserProfileAccess = daoUserProfileAccess;
        }

        @Override
        protected Long doInBackground(UserProfile... userProfile) {
            long id = daoUserProfileAccess.createUserProfile(userProfile[0]);
            return id;
        }

        @Override
        protected void onPostExecute(Long aLong) {
            super.onPostExecute(aLong);
        }
    }

    private static class updateUserProfileAsync extends AsyncTask<UserProfile, Void, Void> {

        private DaoUserProfileAccess daoUserProfileAccess;

        updateUserProfileAsync(DaoUserProfileAccess daoUserProfileAccess) {
            this.daoUserProfileAccess = daoUserProfileAccess;
        }

        @Override
        protected Void doInBackground(UserProfile... userProfile) {
            daoUserProfileAccess.updateUserProfile(userProfile[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }
}