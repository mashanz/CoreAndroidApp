package com.vrumen.coreandroidapp.util

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.net.Uri
import android.provider.Settings
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.Snackbar
import android.support.v4.app.FragmentManager
import android.util.Patterns
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Filter
import android.widget.Toast
import com.vrumen.coreandroidapp.R
import com.vrumen.coreandroidapp.model.VotingImageModel
import com.vrumen.coreandroidapp.ui.dialog.ConfirmationDialog
import com.google.firebase.iid.FirebaseInstanceId
import org.json.JSONException
import org.json.JSONObject
import java.io.File
import java.text.NumberFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


/**
 * Created by Chandra on 7/14/17.
 * Need some help?
 * Contact me at y.pristyan.chandra@gmail.com
 */

object Util {

    fun getSimpleDonationNominal(nominal: String?): String {
        var result = "0"
        if (nominal != null) {
            nominal.replace(",", "")?.replace(".", "")
            when {
                nominal.length >= 13 -> result = "${nominal.substring(0, nominal.length - 12)} T"
                nominal.length in 10..12 -> result = "${nominal.substring(0, nominal.length - 9)} M"
                nominal.length in 7..9 -> result = "${nominal.substring(0, nominal.length - 6)} JT"
                nominal.length in 4..6 -> result = "${nominal.substring(0, nominal.length - 3)} RB"
            }
        }

        return result
    }

    fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun showSnackbar(activity: Activity?, message: String) {
        if (activity != null)
            Snackbar.make(activity.findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG).show()
    }

    fun getPhotosParameter(files: ArrayList<File>): Map<String, File> {
        val photo = HashMap<String, File>()
        if (files.size > 0) (0 until files.size).forEach { photo["pictures[$it]"] = files[it] }
        return photo
    }

    fun getPhotosParam(files: ArrayList<VotingImageModel>): Map<String, File> {
        val photo = HashMap<String, File>()
        var i : Int? = 0
        if (files.size > 0) (0 until files.size).forEach {
            if(files[it].type.equals("offline")) {
                photo["pictures[$i]"] = File(files[it].path)
                i = i?.plus(1)
            }
        }
        return photo
    }

    fun getPhotosGoods(files: ArrayList<VotingImageModel>): Map<String, File> {
        val photo = HashMap<String, File>()
        var i : Int? = 0
        if (files.size > 0) (0 until files.size).forEach {
            if(files[it].type.equals("offline")) {
                photo["userfile[$i]"] = File(files[it].path)
                i = i?.plus(1)
            }
        }
        return photo
    }

    fun getStandardBillNumber(input: String): String {
        return if (input.length in 8..14) {
            when {
                input.substring(0, 1) == "0" -> input
                input.substring(0, 2) == "62" -> "0" + input.substring(2)
                input.substring(0, 3) == "+62" -> "0" + input.substring(3)
                else -> input
            }
        } else input
    }

    fun getTime(feedTime: Long, currentTime: Long): String {
        val result: String
        val feedSec = feedTime / 1000
        val currentSec = currentTime / 1000
        val resSec = currentSec - feedSec
        when (resSec) {
            in 0..3599 -> {
                val minute = resSec / 60
                result = minute.toString() + " min ago"
            }
            in 3600..86399 -> {
                val hours = resSec / 3600
                result = hours.toString() + " hours ago"
            }
            in 86400..172799 -> result = "Yesterday"
            in 86400..2591999 -> {
                val days = resSec / 86400
                result = days.toString() + " days ago"
            }
            else -> {
                val month = resSec / 2592000
                result = month.toString() + " min ago"
                //result = month.toString() + " month ago"
            }
        }

        return result
    }

    fun getFormattedNumber(input: String?): String = try {
        if (input == null || input == "" || input == "null") "0"
        else {
            val inp = input.toLong()
            val numberFormat = NumberFormat.getNumberInstance(Locale.US)
            numberFormat.format(inp)
        }
    } catch (e: Exception) {
        e.printStackTrace()
        "0"
    }

    fun getStandardPhoneNumber(input: String): String {
        return if (input.length > 7) {
            when {
                input.substring(0, 1) == "0" -> "62" + input.substring(1)
                input.substring(0, 1) == "+" -> input.substring(1)
                else -> input
            }
        } else input
    }

    fun getMonth(mount: String): String {
        var result = ""
        return if (mount.contains("Bulan")) {
            result = mount
            result.replace(" Bulan", "")
        } else result
    }

    fun dpToPx(context: Context, dp: Int): Int {
        val displayMetrics = context.resources.displayMetrics
        return (dp * displayMetrics.density + 0.5).toInt()
    }

    fun showSnackbar(coordinatorLayout: CoordinatorLayout, message: String) {
        val snackbar = Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_SHORT)
        snackbar.show()
    }

    fun hideSoftKeyboard(context: Context, view: EditText) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun time(dateData: String): String {
        val year: String = dateData.substring(0, 4)
        val mountNumber: String = dateData.substring(5, 7)

        val date: String = dateData.substring(8, 10)

        return "$date ${month(mountNumber)} $year"
    }

    fun getTotalDay(dateData: String): String {
        val year: String = dateData.substring(0, 4)
        val mountNumber: String = dateData.substring(5, 7)
        val date: String = dateData.substring(8, 10)

        val now = Calendar.getInstance()
        val data = Calendar.getInstance()

        data.set(year.toInt(), mountNumber.toInt(), date.toInt())
        val millis1 = now.timeInMillis
        val millis2 = data.timeInMillis
        val diff = millis2 - millis1

        val diffDays = diff / (24 * 60 * 60 * 1000)

        return "$diffDays"
    }

    fun dateTime(dateData: String): String {
        val year: String = dateData.substring(0, 4)
        val mountNumber: String = dateData.substring(5, 7)

        val date: String = dateData.substring(8, 10)
        val time: String = dateData.substring(11, 16)

        return "$date ${month(mountNumber)} $year $time"
    }

    fun percent(n: Int, value: Int): Int {
        return if (value != 0)
            value * 100 / n
        else
            0
    }


    fun makeCall(context: Context, phoneNumber: String) = try {
        val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber))
        context.startActivity(intent)
    } catch (e: SecurityException) {
        e.printStackTrace()
    }

    private fun month(number: String): String = when (number) {
        "01" -> "Januari"
        "02" -> "Februari"
        "03" -> "Maret"
        "04" -> "April"
        "05" -> "Mei"
        "06" -> "Juni"
        "07" -> "Juli"
        "08" -> "Agustus"
        "09" -> "September"
        "10" -> "Oktober"
        "11" -> "November"
        else -> "Desember"
    }

    fun getFcmToken(): String? {
        val instanceID = FirebaseInstanceId.getInstance()
        return instanceID.token
    }

    fun showDialogGPS(ctx: Context, fragmentManager: FragmentManager) {
        val locManager = ctx.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (!locManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            DialogUtil.showConfirmation(fragmentManager, "Harap aktifkan GPS untuk menggunakan aplikasi ini")
                    .setOnMessageClosed(object : ConfirmationDialog.OnMessageClosed {
                        override fun onProcess() {
                            ctx.startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
                        }

                        override fun onCancel() {

                        }

                    })
        }
    }

    fun setTagging(data_tagging: String): String {
        var result = ""
        if (!data_tagging.isEmpty()) {
            try {
                val jsonMain = JSONObject(data_tagging)
                val jsonData = jsonMain.getJSONArray("data_tag")
                when {
                    jsonData.length() == 1 -> result = "- Bersama " + jsonData.getJSONObject(0).getString("tagName")
                    jsonData.length() == 2 -> result = "- Bersama " + jsonData.getJSONObject(0).getString("tagName") + " dan " + jsonData.getJSONObject(1).getString("tagName")
                    jsonData.length() > 2 -> result = "- Bersama " + jsonData.getJSONObject(0).getString("tagName") + " dan " + (jsonData.length() - 1).toString() + " Others"
                    jsonData.length() == 0 -> result = "Tandai teman anda"
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }

            return result
        }
        return result
    }

    fun mergeIdTag(data_tagging: String): String {
        var data = ""
        try {
            val jsonMain = JSONObject(data_tagging)
            val jsonData = jsonMain.getJSONArray("data_tag")
            if (jsonData.length() == 1) {
                data = jsonData.getJSONObject(0).getString("tagId")
            } else {
                for (i in 0 until jsonData.length()) {
                    when (i) {
                        0 -> data = jsonData.getJSONObject(i).getString("tagId") + ","
                        jsonData.length() - 1 -> data += jsonData.getJSONObject(i).getString("tagId")
                        else -> data = data + jsonData.getJSONObject(i).getString("tagId") + ","
                    }
                }
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        return data
    }

    fun checkNull(source: String, placeholder: String): String {
        var input = source
        return if (input == "" || input == "null")
            placeholder
        else {
            input = input.substring(0, 1).toUpperCase() + input.substring(1)
            input
        }
    }

    @SuppressLint("SimpleDateFormat")
    fun getMillisecondFromDate(datetime: String): Long = try {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val date = sdf.parse(datetime)
        date.time
    } catch (e: ParseException) {
        e.printStackTrace()
        -1
    }

    @SuppressLint("SimpleDateFormat")
    fun getMillisecondExpired(datetime: String): Long = try {
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val date = sdf.parse(datetime)
        date.time
    } catch (e: ParseException) {
        e.printStackTrace()
        -1
    }

    @SuppressLint("SimpleDateFormat")
    fun getCurrentDateTime24(): Long {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val date = Date()
        val currentDate = dateFormat.parse(dateFormat.format(date))
        return currentDate.time
    }

    fun getTopicColor(input: String): Int {
        when (input) {
            "A" -> {
                return R.color.red300
            }
            "B" -> {
                return R.color.blue300
            }
            "C" -> {
                return R.color.green300
            }
            "D" -> {
                return R.color.yellow300
            }
            "E" -> {
                return R.color.brown300
            }
            "F" -> {
                return R.color.teal300
            }
            "G" -> {
                return R.color.orange300
            }
            "H" -> {
                return R.color.pink300
            }
            "I" -> {
                return R.color.purple300
            }
            "J" -> {
                return R.color.lightGreen300
            }
            "K" -> {
                return R.color.lightBlue300
            }
            "L" -> {
                return R.color.lime300
            }
            "M" -> {
                return R.color.indigo300
            }
            "N" -> {
                return R.color.deepPurple300
            }
            "O" -> {
                return R.color.cyan300
            }
            "P" -> {
                return R.color.amber300
            }
            "Q" -> {
                return R.color.blueGrey500
            }
            "R" -> {
                return R.color.indigo500
            }
            "S" -> {
                return R.color.green500
            }
            "T" -> {
                return R.color.lightBlue500
            }
            "U" -> {
                return R.color.yellow500
            }
            "V" -> {
                return R.color.grey700
            }
            "W" -> {
                return R.color.amber600
            }
            "X" -> {
                return R.color.indigo500
            }
            "Y" -> {
                return R.color.pink500
            }
            else -> {
                return R.color.purple500
            }
        }
    }

    fun merge(word: ArrayList<String>): String {
        var xx = ""
        for (i in word.indices) {
            xx = if (xx.isEmpty())
                word[i]
            else
                xx + ", " + word[i]
        }
        return xx
    }

    fun extractUrls(input: String): List<String> {
        val result = ArrayList<String>()
        val words = input.split("\\s+".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val pattern = Patterns.WEB_URL
        for (i in words.indices) {
            if (pattern.matcher(words[i]).find()) {
                if (!words[i].toLowerCase().contains("http://") && !words[i].toLowerCase().contains("https://")) {
                    words[i] = "http://" + words[i]
                }
                result.add(words[i])
            }
        }
        return result
    }
}
