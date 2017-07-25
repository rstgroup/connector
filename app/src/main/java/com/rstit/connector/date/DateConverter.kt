package com.rstit.connector.date

import java.util.*

/**
 * @author Tomasz Trybala
 * @since 2017-07-25
 */
interface DateConverter {
    fun convert(date: Date): String
}