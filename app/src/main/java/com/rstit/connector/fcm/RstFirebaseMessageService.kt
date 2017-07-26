package com.rstit.connector.fcm

import android.app.PendingIntent
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.media.RingtoneManager
import android.os.Handler
import android.os.Looper
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import android.support.v4.content.ContextCompat
import android.util.Log
import com.bumptech.glide.Glide
import com.bumptech.glide.request.animation.GlideAnimation
import com.bumptech.glide.request.target.SimpleTarget
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.gson.Gson
import com.rstit.connector.ConnectorApplication
import com.rstit.connector.R
import com.rstit.connector.di.fcm.FcmModule
import com.rstit.connector.model.inbox.InboxEntry
import com.rstit.connector.ui.chat.ChatActivity
import jp.wasabeef.glide.transformations.CropCircleTransformation
import org.json.JSONObject
import javax.inject.Inject

/**
 * @author Tomasz Trybala
 * @since 2017-07-26
 */

const val KEY_NEW_MESSAGE = "key_new_message"
const val CHANNEL_ID = "RstConnectorChannel"
const val NOTIFICATION_ID = 982634

class RstFirebaseMessageService : FirebaseMessagingService() {
    @Inject
    lateinit var gson: Gson

    override fun onCreate() {
        super.onCreate()

        ConnectorApplication.get(this)
                .appComponent
                .plus(FcmModule(this))
                .inject(this)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        remoteMessage?.let {
            when (it.messageType) {
                KEY_NEW_MESSAGE -> receiveMessage(it)
                else -> Log.e(RstFirebaseMessageService::class.java.name, "${it.messageType} is not recognized as push type!")
            }
        }
    }

    fun receiveMessage(remoteMessage: RemoteMessage) {
        try {
            val entry: InboxEntry = gson.fromJson(JSONObject(remoteMessage.data).toString(), InboxEntry::class.java)
            Handler(Looper.getMainLooper()).post({ loadMessageBitmap(entry, getImageSize(this)) })
        } catch (e: Exception) {
            e.printStackTrace()
            /**
            printStackTrace() for future debugging purposes
            notification won't be displayed
             */
        }
    }

    fun loadMessageBitmap(inboxEntry: InboxEntry, size: Int) {
        Glide.with(this)
                .load(inboxEntry.user?.avatar ?: "")
                .asBitmap()
                .transform(CropCircleTransformation(this))
                .into(object : SimpleTarget<Bitmap>(size, size) {
                    override fun onResourceReady(resource: Bitmap?, glideAnimation: GlideAnimation<in Bitmap>?) {
                        resource?.let {
                            displayMessageNotification(it, inboxEntry)
                        }
                    }

                    override fun onLoadFailed(e: Exception?, errorDrawable: Drawable?) {
                        loadMessageErrorBitmap(inboxEntry, size)
                    }
                })
    }

    fun loadMessageErrorBitmap(inboxEntry: InboxEntry, size: Int) {
        Glide.with(this)
                .load(R.drawable.ic_account_circle_grey)
                .asBitmap()
                .into(object : SimpleTarget<Bitmap>(size, size) {
                    override fun onResourceReady(resource: Bitmap?, glideAnimation: GlideAnimation<in Bitmap>?) {
                        resource?.let {
                            displayMessageNotification(it, inboxEntry)
                        }
                    }
                })
    }

    fun displayMessageNotification(bitmap: Bitmap, inboxEntry: InboxEntry) {
        if (inboxEntry.user != null) {
            val intent = PendingIntent.getActivity(this, 0, ChatActivity.createIntent(this, inboxEntry.user), 0)

            val builder: NotificationCompat.Builder =
                    NotificationCompat.Builder(this, CHANNEL_ID)
                            .setAutoCancel(true)
                            .setSmallIcon(R.drawable.ic_notification_logo)
                            .setLargeIcon(bitmap)
                            .setColor(ContextCompat.getColor(this, R.color.colorPrimary))
                            .setContentTitle(inboxEntry.lastMessage?.content)
                            .setContentText("${inboxEntry.user.name} ${inboxEntry.user.lastName}")
                            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                            .setContentIntent(intent)

            NotificationManagerCompat.from(this).notify(NOTIFICATION_ID, builder.build())
        }
    }

    fun getImageSize(context: Context) = context.resources.getDimensionPixelSize(R.dimen.padding_xxlarge)
}