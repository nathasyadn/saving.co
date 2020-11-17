package id.ac.ui.cs.mobileprogramming.natasyameidianaakhda.savingco.persistence;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import id.ac.ui.cs.mobileprogramming.natasyameidianaakhda.savingco.persistence.transaction.DaoTransactionAccess;
import id.ac.ui.cs.mobileprogramming.natasyameidianaakhda.savingco.persistence.transaction.Transaction;
import id.ac.ui.cs.mobileprogramming.natasyameidianaakhda.savingco.persistence.userLogin.DaoUserLoginAccess;
import id.ac.ui.cs.mobileprogramming.natasyameidianaakhda.savingco.persistence.userLogin.UserLogin;
import id.ac.ui.cs.mobileprogramming.natasyameidianaakhda.savingco.persistence.userProfile.DaoUserProfileAccess;
import id.ac.ui.cs.mobileprogramming.natasyameidianaakhda.savingco.persistence.userProfile.UserProfile;

@Database(entities = {Transaction.class, UserLogin.class, UserProfile.class}, version = 1, exportSchema = false)
public abstract class SavingcoDatabase extends RoomDatabase {
    public abstract DaoTransactionAccess daoTransactionAccess();
    public abstract DaoUserLoginAccess daoUserLoginAccess();
    public abstract DaoUserProfileAccess daoUserProfileAccess();

    private static SavingcoDatabase INSTANCE;

    public static SavingcoDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (SavingcoDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            SavingcoDatabase.class, "saving.co_database")
                            .allowMainThreadQueries()
                            .build();

                }
            }
        }
        return INSTANCE;
    }
}