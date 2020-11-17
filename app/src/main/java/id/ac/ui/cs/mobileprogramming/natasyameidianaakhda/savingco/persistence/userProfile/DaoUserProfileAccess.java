package id.ac.ui.cs.mobileprogramming.natasyameidianaakhda.savingco.persistence.userProfile;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface DaoUserProfileAccess {
    @Insert
    Long createUserProfile(UserProfile userProfile);

    @Query("SELECT * FROM UserProfile WHERE id=:id")
    LiveData<UserProfile> getUserProfileById(int id);

    @Update
    void updateUserProfile(UserProfile userProfile);

    @Delete
    void deleteUserProfile(UserProfile userProfile);
}