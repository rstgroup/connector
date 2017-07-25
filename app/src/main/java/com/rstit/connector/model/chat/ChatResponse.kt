package com.rstit.connector.model.chat

import android.os.Parcel
import com.google.gson.annotations.SerializedName
import com.rstit.connector.model.inbox.Message
import paperparcel.PaperParcel
import paperparcel.PaperParcelable


/**
 * @author Tomasz Trybala
 * @since 2017-07-25
 */
@PaperParcel
data class ChatResponse(
        @SerializedName("data")
        val entries: Collection<Message>? = null,

        @SerializedName("last_page")
        val isLastPage: Boolean = true
) : PaperParcelable {
    companion object {
        @JvmField
        val CREATOR = PaperParcelChatResponse.CREATOR
    }

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = PaperParcelChatResponse.writeToParcel(this, dest, flags)
}