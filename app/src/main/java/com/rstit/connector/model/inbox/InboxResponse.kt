package com.rstit.connector.model.inbox

import android.os.Parcel
import com.google.gson.annotations.SerializedName
import paperparcel.PaperParcel
import paperparcel.PaperParcelable

/**
 * @author Tomasz Trybala
 * @since 2017-07-21
 */
@PaperParcel
data class InboxResponse(
        @SerializedName("data")
        val entries: Collection<InboxEntry>? = null,

        @SerializedName("last_page")
        val isLastPage: Boolean = true
) : PaperParcelable {
    companion object {
        @JvmField
        val CREATOR = PaperParcelInboxResponse.CREATOR
    }

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = PaperParcelInboxResponse.writeToParcel(this, dest, flags)
}