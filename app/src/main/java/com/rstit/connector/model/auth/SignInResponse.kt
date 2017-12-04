package com.rstit.connector.model.auth

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
data class SignInResponse(
        @SerializedName("token")
        val token: String? = null,

        @SerializedName("user")
        val user: User? = null
) : PaperParcelable {
    companion object {
        @JvmField
        val CREATOR = PaperParcelSignInResponse.CREATOR
    }

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = PaperParcelSignInResponse.writeToParcel(this, dest, flags)
}