package pl.inpost.recruitmenttask.util

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

//TODO -
@RequiresApi(Build.VERSION_CODES.O)
fun ZonedDateTime.setFormattedDate(): String {
    val format = DateTimeFormatter.ofPattern("EEE. | dd.MM.yy | HH:mm", Locale.ENGLISH)
    Log.d("Panda_test", this.format(format))
    return this.format(format)
}