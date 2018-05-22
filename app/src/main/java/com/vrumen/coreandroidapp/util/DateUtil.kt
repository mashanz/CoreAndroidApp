package com.bilinedev.ikasmariagitma.util

import android.annotation.SuppressLint
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by deny on bandung.
 */

object DateUtil {

    fun getFullDateTime(dateInput: String?): String {
        return if (dateInput != null) {
            val year = dateInput?.substring(0, 4)
            val month = dateInput?.substring(5, 7)
            val date = dateInput?.substring(8, 10)
            val m = Integer.valueOf(month)!! - 1
            date + " " + getMonth(m) + " " + year + dateInput?.substring(10, 16)
        } else ""
    }

    fun getFullDate(dateInput: String?): String {
        val year = dateInput?.substring(0, 4)
        val month = dateInput?.substring(5, 7)
        val date = dateInput?.substring(8, 10)
        val m = Integer.valueOf(month)!! - 1
        return date + " " + getMonth(m) + " " + year
    }

    private fun getMonth(month: Int): String = when (month) {
        0 -> "Jan"
        1 -> "Feb"
        2 -> "Mar"
        3 -> "Apr"
        4 -> "May"
        5 -> "Jun"
        6 -> "Jul"
        7 -> "Aug"
        8 -> "Sep"
        9 -> "Oct"
        10 -> "Nov"
        else -> "Dec"
    }

    @SuppressLint("SimpleDateFormat")
    fun getCurrentDateTime(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val date = Date()
        return dateFormat.format(date)
    }

    fun getFeedTime(feedTime: Long, currentTime: Long): String {
        val result: String
        val feedSec = feedTime / 1000
        val currentSec = currentTime / 1000
        val resSec = currentSec - feedSec
        if (resSec in 0..3599) {
            val minute = resSec / 60
            result = "$minute" + " min ago"
        } else if (resSec in 3600..86399) {
            val hours = resSec / 3600
            result = "$hours" + " hours ago"
        } else if (resSec in 86400..172799) {
            result = "Yesterday"
        } else if (resSec in 86400..2591999) {
            val days = resSec / 86400
            result = "$days" + " days ago"
        } else {
            val month = resSec / 2592000
            result = "$month" + " month ago"
        }
        return result
    }

    @SuppressLint("SimpleDateFormat")
    fun getMillis(dateTime: String): Long {
        return try {
            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            val date = sdf.parse(dateTime)
            date.time
        } catch (e: ParseException) {
            e.printStackTrace()
            0
        }
    }


}