package com.rstit.connector.fcm

import android.util.Log
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.FirebaseInstanceIdService

/**
 * @author Tomasz Trybala
 * @since 2017-07-26
 */
class RstFirebaseInstanceIDService : FirebaseInstanceIdService() {
    override fun onTokenRefresh() {
        val token = FirebaseInstanceId.getInstance().token
        Log.d(RstFirebaseInstanceIDService::class.java.name, "Token: " + token)
        //todo send on server
    }
}