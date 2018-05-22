package com.vrumen.coreandroidapp.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.support.v4.content.ContextCompat
import android.widget.ImageView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.vrumen.coreandroidapp.R

/**
 * Created by Chandra on 7/14/17.
 * Need some help?
 * Contact me at y.pristyan.chandra@gmail.com
 */

object MapUtil {

    fun getIconMarker(context: Context): Bitmap {
        val height = 110
        val width = 110
        val bitmapDrawable = ContextCompat.getDrawable(context, R.drawable.ic_poi) as BitmapDrawable
        val b = bitmapDrawable.bitmap
        return Bitmap.createScaledBitmap(b, width, height, false)
    }

    fun getAlumniMarker(context: Context): Bitmap {
        val height = 110
        val width = 85
        val bitmapDrawable = ContextCompat.getDrawable(context, R.drawable.ic_placeholder) as BitmapDrawable
        val b = bitmapDrawable.bitmap
        return Bitmap.createScaledBitmap(b, width, height, false)
    }

    fun setCamera(latLng: LatLng?, googleMap: GoogleMap?) {
        if (latLng != null && googleMap != null) {
            val cameraPosition = CameraPosition.Builder().target(latLng).zoom(13f).build()
            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
        }
    }

    fun loadStaticMapIntoImageView(context: Context, latitude: Double, longitude: Double, imageView: ImageView) {
        // todo (by hans comment) uncomment!
//        val location = latitude.toString() + "," + longitude
//        val url = "https://maps.googleapis.com/maps/api/staticmap?center=" + location +
//                "&zoom=17.0&size=1000x400&maptype=roadmap&markers=color:blue%7C" + location +
//                "&key=" + context.getString(R.string.maps_api_key)
//        println(url)
//        ImageUtil.loadGlide(context, url, 0, imageView)

        /*Glide.with(context)
                .load(url)
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView)*/
    }
}
