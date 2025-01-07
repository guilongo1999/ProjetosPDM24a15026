package pt.ipca.shopping_cart_app.data.room.converter

import android.util.Log
import androidx.room.TypeConverter
import java.util.Date


open class DateConverter {



    @TypeConverter
    fun toDate(timestamp: Long?): Date? {
        Log.d("DateConverter", "Converting timestamp to Date: $timestamp")

        return timestamp?.let { Date(it) }
    }

    @TypeConverter
    fun fromDate(date: Date?): Long? {
        Log.d("DateConverter", "Converting Date to timestamp: $date")

        return date?.time
    }


    }


