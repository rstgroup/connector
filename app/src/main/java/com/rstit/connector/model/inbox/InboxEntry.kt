package com.rstit.connector.model.inbox

import android.os.Parcel
import com.google.gson.annotations.SerializedName
import com.rstit.connector.model.user.User
import paperparcel.PaperParcel
import paperparcel.PaperParcelable

/**
 * @author Tomasz Trybala
 * @since 2017-07-21
 */
@PaperParcel
data class InboxEntry(
        @SerializedName("user")
        val name: User? = null,

        @SerializedName("last_message")
        val lastMessage: Message? = null
) : PaperParcelable {
    companion object {
        @JvmField
        val CREATOR = PaperParcelInboxEntry.CREATOR
    }

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = PaperParcelInboxEntry.writeToParcel(this, dest, flags)
}