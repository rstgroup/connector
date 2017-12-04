package com.rstit.connector.fcm

import android.app.PendingIntent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.media.RingtoneManager
import android.os.Handler
import android.os.Looper
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import android.support.v4.content.ContextCompat
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
import javax.inject.Inject

/**
 * @author Tomasz Trybala
 * @since 2017-07-26
 */

const val KEY_NEW_MESSAGE = "entry"
const val CHANNEL_ID = "RstConnectorChannel"
const val NOTIFICATION_ID = 982634

class RstFirebaseMessageService : FirebaseMessagingService() {
    @Inject
    lateinit var gson: Gson

    val imageSize: Int by lazy {
        this.resources.getDimensionPixelSize(R.dimen.padding_xxlarge)
    }

    override fun onCreate() {
        super.onCreate()

        ConnectorApplication.get(this)
                .appComponent
                .plus(FcmModule(this))
                .inject(this)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        remoteMessage?.let {
            it.data[KEY_NEW_MESSAGE]?.let {
                receiveMessage(it)
            }
        }
    }

    fun receiveMessage(json: String) {
        try {
            val entry: InboxEntry = gson.fromJson(json, InboxEntry::class.java)
            Handler(Looper.getMainLooper()).post { loadMessageBitmap(entry, imageSize) }
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
            val intent = PendingIntent.getActivity(this, 0,
                    ChatActivity.createIntent(this, inboxEntry.user),
                    PendingIntent.FLAG_UPDATE_CURRENT)

            val builder: NotificationCompat.Builder =
                    NotificationCompat.Builder(this, CHANNEL_ID)
                            .setAutoCancel(true)
                            .setSmallIcon(R.drawable.ic_notification_logo)
                            .setLargeIcon(bitmap)
                            .setColor(ContextCompat.getColor(this, R.color.colorPrimary))
                            .setContentText(inboxEntry.lastMessage?.content)
                            .setContentTitle("${inboxEntry.user.name} ${inboxEntry.user.lastName}")
                            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                            .setContentIntent(intent)

            NotificationManagerCompat.from(this).notify(NOTIFICATION_ID, builder.build())
        }
    }
}