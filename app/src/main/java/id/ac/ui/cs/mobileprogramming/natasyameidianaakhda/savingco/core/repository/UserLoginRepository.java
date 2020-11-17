package id.ac.ui.cs.mobileprogramming.natasyameidianaakhda.savingco.core.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

import id.ac.ui.cs.mobileprogramming.natasyameidianaakhda.savingco.core.entity.UserLoginEntity;
import id.ac.ui.cs.mobileprogramming.natasyameidianaakhda.savingco.persistence.SavingcoDatabase;
import id.ac.ui.cs.mobileprogramming.natasyameidianaakhda.savingco.persistence.userLogin.DaoUserLoginAccess;
import id.ac.ui.cs.mobileprogramming.natasyameidianaakhda.savingco.persistence.userLogin.UserLogin;
import id.ac.ui.cs.mobileprogramming.natasyameidianaakhda.savingco.util.Utilities;

public class UserLoginRepository {
    private DaoUserLoginAccess daoUserLoginAccess;
    private LiveData<List<UserLogin>> mUserLogin;

    public UserLoginRepository(Application application) {
        SavingcoDatabase db = SavingcoDatabase.getDatabase(application);
        daoUserLoginAccess = db.daoUserLoginAccess();
        mUserLogin = daoUserLoginAccess.getAllUser();
    }

    public LiveData<List<UserLogin>> getAllUsers() {
        return mUserLogin;
    }

    public void createUser(UserLoginEntity entity) {
        final UserLogin userLogin = new UserLogin();
        userLogin.setUsername(entity.getUsername());
        userLogin.setPassword(entity.getPassword());
        userLogin.setCreatedAt(Utilities.getCurrentTimestamp());
        new insertUserLoginAsync(daoUserLoginAccess).execute(userLogin);
    }

    public LiveData<UserLogin> getUserLoginByUsernameAndPassword(String username, String password) {
        return daoUserLoginAccess.getUserByUsernameAndPassword(username, password);
    }

    private static class insertUserLoginAsync extends AsyncTask<UserLogin, Void, Long> {

        private DaoUserLoginAccess daoUserLoginAccess;

        insertUserLoginAsync(DaoUserLoginAccess daoUserLoginAccess) {
            this.daoUserLoginAccess = daoUserLoginAccess;
        }

        @Override
        protected Long doInBackground(UserLogin... userLogin) {
            long id = daoUserLoginAccess.createUser(userLogin[0]);
            return id;
        }

        @Override
        protected void onPostExecute(Long aLong) {
            super.onPostExecute(aLong);
        }
    }
}