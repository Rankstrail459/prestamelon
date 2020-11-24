package com.tomasdarioam.prestamelon;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.Environment;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BitmapUtils {
    public static Bitmap getBitmapFromPhotoPath(String photoPath, int targetWidth, int targetHeight) {
        Bitmap bitmap;

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;

        BitmapFactory.decodeFile(photoPath, options);

        int photoWidth = options.outWidth;
        int photoHeight = options.outHeight;

        options.inJustDecodeBounds = false;

        int scaleFactor;
        if(targetWidth != 0 && targetHeight != 0) {
            scaleFactor = Math.max(
                    1,
                    Math.min(photoWidth / targetWidth, photoHeight / targetHeight)
            );

        } else {
            scaleFactor = 2;
        }

        options.inSampleSize = scaleFactor;

        bitmap = BitmapFactory.decodeFile(photoPath, options);

        ExifInterface ei = null;
        try {
            ei = new ExifInterface(photoPath);
        } catch(IOException exception) {
            return null;
        }

        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_UNDEFINED);

        Bitmap rotatedBitmap = null;
        switch(orientation) {

            case ExifInterface.ORIENTATION_ROTATE_90:
                rotatedBitmap = rotateImage(bitmap, 90);
                break;

            case ExifInterface.ORIENTATION_ROTATE_180:
                rotatedBitmap = rotateImage(bitmap, 180);
                break;

            case ExifInterface.ORIENTATION_ROTATE_270:
                rotatedBitmap = rotateImage(bitmap, 270);
                break;

            case ExifInterface.ORIENTATION_NORMAL:
            default:
                rotatedBitmap = bitmap;
        }

        bitmap = rotatedBitmap;

        return bitmap;
    }

    private static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }

}
