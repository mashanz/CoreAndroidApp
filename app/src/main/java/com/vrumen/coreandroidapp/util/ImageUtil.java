package com.vrumen.coreandroidapp.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.widget.ImageView;

import com.bilinedev.ikasmariagitma.util.imagecompressor.ImageUtilThumb;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import java.io.File;

/**
 * Created by Chandra on 11/22/16.
 * Need some help?
 * Contact me at y.pristyan.chandra@gmail.com
 */

public class ImageUtil {

    public static File compressImage(Context context, File file) {
        ImageUtilThumb imageUtilThumb = new ImageUtilThumb();
        return imageUtilThumb.compressImage(context, Uri.fromFile(file), 1000, 1000,
                Bitmap.CompressFormat.JPEG, 80, Environment.getExternalStorageDirectory().getPath());
    }

    public static File compressImageCustom(Context context, int width, int height, File file) {
        if (width > 1000 || height > 1000) {
            ImageUtilThumb imageUtilThumb = new ImageUtilThumb();
            return imageUtilThumb.compressImage(context, Uri.fromFile(file), 1000, 1000,
                    Bitmap.CompressFormat.JPEG, 80, Environment.getExternalStorageDirectory().getPath());
        } else {
            ImageUtilThumb imageUtilThumb = new ImageUtilThumb();
            return imageUtilThumb.compressImage(context, Uri.fromFile(file), width - 40, height - 40,
                    Bitmap.CompressFormat.JPEG, 80, Environment.getExternalStorageDirectory().getPath());
        }
    }

    public static Bitmap getScaledBitmapAtLongestSide(Bitmap bitmap, int targetSize) {
        if (bitmap == null || bitmap.getWidth() <= targetSize && bitmap.getHeight() <= targetSize) {
            // Do not resize.
            return bitmap;
        }

        int targetWidth, targetHeight;
        if (bitmap.getHeight() > bitmap.getWidth()) {
            // Resize portrait bitmap
            targetHeight = targetSize;
            float percentage = (float) targetSize / bitmap.getHeight();
            targetWidth = (int) (bitmap.getWidth() * percentage);
        } else {
            // Resize landscape or square image
            targetWidth = targetSize;
            float percentage = (float) targetSize / bitmap.getWidth();
            targetHeight = (int) (bitmap.getHeight() * percentage);
        }

        return Bitmap.createScaledBitmap(bitmap, targetWidth, targetHeight, true);
    }

    @SuppressLint("Range")
    public static void loadGlide(final Context context, String url, final ImageView img) {
        Glide.with(context).load(url).apply(RequestOptions.overrideOf(Target.SIZE_ORIGINAL)).into(img);
    }

    @SuppressLint("Range")
    public static void loadGlide(final Context context, String url, int errorImage, final ImageView img) {
        Glide.with(context).load(url).apply(RequestOptions.errorOf(errorImage)).into(img);
    }

    @SuppressLint("Range")
    public static void loadGlide(final Context context, int resources, int errorImage, final ImageView img) {
        Glide.with(context).load(resources).apply(RequestOptions.errorOf(errorImage)).into(img);
    }

    @SuppressLint("Range")
    public static void loadGlide(final Activity act, int file, int errorImage, final ImageView img) {
        Glide.with(act).load(file).apply(RequestOptions.errorOf(errorImage)).into(img);
    }

    @SuppressLint("Range")
    public static void loadGlide(final Context context, File file, int errorImage, final ImageView img) {
        Glide.with(context).load(file).apply(RequestOptions.errorOf(errorImage)).into(img);
    }
}
