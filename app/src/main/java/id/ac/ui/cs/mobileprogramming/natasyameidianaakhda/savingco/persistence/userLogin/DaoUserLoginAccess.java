package id.ac.ui.cs.mobileprogramming.natasyameidianaakhda.savingco.persistence.userLogin;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface DaoUserLoginAccess {
    @Insert
    Long createUser(UserLogin userLogin);

    @Query("SELECT * FROM UserLogin WHERE username =:username AND password =:password")
    LiveData<UserLogin> getUserByUsernameAndPassword(String username, String password);

    @Query("SELECT * FROM UserLogin WHERE username =:username")
    LiveData<UserLogin> getUserByUsername(String username);

    @Query("SELECT * FROM UserLogin WHERE id=:id")
    LiveData<UserLogin> getUserById(int id);

    @Query("SELECT * FROM UserLogin")
    LiveData<List<UserLogin>> getAllUser();

    @Update
    void updateUser(UserLogin userLogin);

    @Delete
    void deleteUser(UserLogin userLogin);
}