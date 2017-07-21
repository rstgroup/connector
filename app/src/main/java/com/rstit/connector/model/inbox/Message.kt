package com.rstit.connector.model.inbox

import android.os.Parcel
import com.google.gson.annotations.SerializedName
import paperparcel.PaperParcel
import paperparcel.PaperParcelable
import java.util.*

/**
 * @author Tomasz Trybala
 * @since 2017-07-21
 */
@PaperParcel
data class Message(
        @SerializedName("id")
        val id: Int? = 0,

        @SerializedName("content")
        val content: String? = null,

        @SerializedName("status")
        val status: String? = null,

        @SerializedName("created_at")
        val createdAt: Date? = null,

        @SerializedName("is_my_message")
        val isMyMessage: Boolean? = null
) : PaperParcelable {
    companion object {
        @JvmField
        val CREATOR = PaperParcelMessage.CREATOR
    }

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = PaperParcelMessage.writeToParcel(this, dest, flags)
}