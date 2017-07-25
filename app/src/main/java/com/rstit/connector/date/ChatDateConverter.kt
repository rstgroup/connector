package com.rstit.connector.date

import android.text.format.DateUtils
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author Tomasz Trybala
 * @since 2017-07-25
 */
const val FORMAT_TIME = "HH:mm"
const val FORMAT_DATE = "dd MMM"

class ChatDateConverter : DateConverter {
    override fun convert(date: Date): String =
            SimpleDateFormat(if (DateUtils.isToday(date.time)) FORMAT_TIME else FORMAT_DATE,
                    Locale.getDefault()).format(date)
}