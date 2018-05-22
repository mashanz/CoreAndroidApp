package com.bilinedev.ikasmariagitma.util;

import android.content.Context;
import android.support.v4.content.ContextCompat;

/**
 * Created by Chandra on 11/16/17.
 * Need some help?
 * Contact me at y.pristyan.chandra@gmail.com
 */

public class ColorUtil {

    public static String getHex(Context context, int color) {
        return "#" + Integer.toHexString(ContextCompat.getColor(context, color));
    }
}
