package id.ac.ui.cs.mobileprogramming.natasyameidianaakhda.savingco.util;

import java.sql.Timestamp;
import java.util.Date;

public class Utilities {
    public static String getCurrentTimestamp() {
        Date date = new Date();
        long time = date.getTime();
        Timestamp timestamp = new Timestamp(time);
        return String.valueOf(timestamp);
    }
}