package id.ac.ui.cs.mobileprogramming.natasyameidianaakhda.savingco.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import java.sql.Timestamp;
import java.util.Date;

public class Utilities {
    public static String getCurrentTimestamp() {
        Date date = new Date();
        long time = date.getTime();
        Timestamp timestamp = new Timestamp(time);
        return String.valueOf(timestamp);
    }

    public static Bitmap getResizedBitmap(Bitmap bm) {
        int newWidth = 480;
        int newHeight = 480;

        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);

        return Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
    }

    public static Bitmap decodeBase64(byte[] decodedByte) {
        return BitmapFactory.decodeByteArray(decodedByte, 0,decodedByte.length);
    }
}