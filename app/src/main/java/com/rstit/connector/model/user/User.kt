package com.rstit.connector.model.user

import android.os.Parcel
import com.google.gson.annotations.SerializedName
import paperparcel.PaperParcel
import paperparcel.PaperParcelable

/**
 * @author Tomasz Trybala
 * @since 2017-07-21
 */
@PaperParcel
data class User(
        @SerializedName("id")
        val id: Int? = 0,

        @SerializedName("name")
        val name: String? = null,

        @SerializedName("lastName")
        val lastName: String? = null,

        @SerializedName("email")
        val email: String? = null,

        @SerializedName("avatar")
        val avatar: String? = null,

        @SerializedName("role")
        val role: String? = null
) : PaperParcelable {
    companion object {
        @JvmField
        val CREATOR = PaperParcelUser.CREATOR
    }

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = PaperParcelUser.writeToParcel(this, dest, flags)

    fun getUserRole(): UserRole = UserRole.from(role)
}