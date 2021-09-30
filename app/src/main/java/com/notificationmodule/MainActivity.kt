package com.notificationmodule

import android.app.Notification
import android.app.Notification.EXTRA_NOTIFICATION_ID
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.RemoteViews
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.RemoteInput
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var simpleNotiButton: Button
    lateinit var PendingIntentNotiButton: Button
    lateinit var tapactionNotification: Button
    lateinit var actionNotification: Button
    lateinit var progressNotification: Button
    lateinit var bigPictureNotification: Button
    lateinit var inboxStyleNotification: Button
    lateinit var replyNotification: Button
    lateinit var handsUpNotification: Button
    var CHANNEL_ID = "12345"
    var notificationId = 12345


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
        createNotificationChannel()
        simpleNotiButton.setOnClickListener { basicNotification() }
        PendingIntentNotiButton.setOnClickListener { pendingNotification() }
        tapactionNotification.setOnClickListener { tapactionNotification() }
        actionNotification.setOnClickListener { actionNotification() }
        progressNotification.setOnClickListener { progressNotification() }
        bigPictureNotification.setOnClickListener { bigPictureNotification() }
        inboxStyleNotification.setOnClickListener { inboxStyleNotification() }
        replyNotification.setOnClickListener { replyNotification() }
        handsUpNotification.setOnClickListener { handsUpNotification() }
    }

    fun initViews() {
        simpleNotiButton = findViewById(R.id.simpleNotiButton)
        PendingIntentNotiButton = findViewById(R.id.PendingIntentNotiButton)
        tapactionNotification = findViewById(R.id.tapactionNotification)
        actionNotification = findViewById(R.id.actionNotification)
        progressNotification = findViewById(R.id.progressNotification)
        bigPictureNotification = findViewById(R.id.bigPictureNotification)
        inboxStyleNotification = findViewById(R.id.inboxStyleNotification)
        replyNotification = findViewById(R.id.replyNotification)
        handsUpNotification = findViewById(R.id.handsUpNotification)

    }

    private fun basicNotification() {
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.oilab_logo)
            .setContentTitle("Test notification")
            .setContentText("Test Notification body")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        with(NotificationManagerCompat.from(this)) {
            notify(notificationId, builder.build())
        }
    }

    private fun pendingNotification() {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("My notification")
            .setContentText("Hello World!")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
        with(NotificationManagerCompat.from(this)) {
            notify(notificationId, builder.build())
        }
    }

    private fun tapactionNotification() {

        val intent = Intent(this, MyBroadCastReceiver::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

        }

        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.oilab_logo)
            .setContentTitle("My notification")
            .setContentText("Hello World!")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            // Set the intent that will fire when the user taps the notification
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
        with(NotificationManagerCompat.from(this)) {
            // notificationId is a unique int for each notification that you must define
            notify(notificationId, builder.build())
        }
    }

    private fun actionNotification() {
        // Create an explicit intent for an activity in this app
        val intent = Intent(this, MainActivity::class.java)
        intent.apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, 0)
        val buttonIntent = Intent(this, MyBroadCastReceiver::class.java)
        buttonIntent.apply {
            action = "Do Pending Task"
            putExtra("Action On Notification", "Done")
        }
        val buttonPendingIntent = PendingIntent.getBroadcast(this, 0, buttonIntent, 0)
        val notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.oilab_logo)
            .setContentTitle("Oilab Learning")
            .setContentText("test action notification")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .addAction(R.drawable.oilab_logo, "Do Task", buttonPendingIntent)

        with(NotificationManagerCompat.from(this)) {
            // notificationId is a unique int for each notification that you must define
            notify(notificationId, notificationBuilder.build())
        }
    }

    private fun bigPictureNotification() {
        val notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.oilab_logo)
            .setContentTitle("Notification Big Picture")
            .setContentText("Notification content text.")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setStyle(
                NotificationCompat.BigPictureStyle()
                    // Provide the bitmap to be used as the payload for the BigPicture notification.
                    .bigPicture(BitmapFactory.decodeResource(resources, R.drawable.oilab_logo))

                    // Override the large icon when the big notification is shown.
                    .bigLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.oilab_logo))

                    // Overrides ContentTitle in the big form of the template.
                    .setBigContentTitle("OILab Learning")

                    // Set the first line of text after the detail section in the big form of the template.
                    .setSummaryText("This is big picture summary")
            )


        with(NotificationManagerCompat.from(this)) {
            notify(notificationId, notificationBuilder.build())
        }

    }

    private fun progressNotification() {
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Download Task")
            .setContentText("Downloading your file...")
            .setSmallIcon(R.drawable.oilab_logo)

        val max = 100
        var progress = 0
        val handler = Handler()


        with(NotificationManagerCompat.from(this)) {
            builder.setProgress(max, progress, true)
            notify(notificationId, builder.build())

            Thread(Runnable {
                while (progress < 100) {
                    progress += 1

                    try {
                        Thread.sleep(100)
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }

                    handler.post(Runnable {
                        if (progress == 100) {
                            builder.setContentText("Download complete.")
                            builder.setProgress(0, 0, false)
                        } else {
                            builder.setContentText("complete $progress of $max")
                            builder.setProgress(max, progress, true)
                        }

                        notify(notificationId, builder.build())
                    })
                }
            }).start()
        }
    }

    private fun inboxStyleNotification() {
        val notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.oilab_logo)
            .setContentTitle("Oilab Learning Message")
            .setContentText("oilab learning test content text.")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setStyle(
                NotificationCompat.InboxStyle()
                    // Append a line to the digest section of the Inbox notification.
                    .addLine("This is first line")
                    .addLine("This is second line")
                    .addLine("This is third line")
                    .addLine("This is fourth line")
                    .addLine("This is fifth line")

                    // Overrides ContentTitle in the big form of the template.
                    .setBigContentTitle("This is Content Title.")

                    // Set the first line of text after the detail section in
                    // the big form of the template.
                    .setSummaryText("This is summary text.")
            )


        with(NotificationManagerCompat.from(this)) {
            notify(notificationId, notificationBuilder.build())
        }
    }

    private fun replyNotification() {
        // The direct reply action, introduced in Android 7.0 (API level 24)
        if (Build.VERSION.SDK_INT >= 24) {
            // Create an instance of remote input builder
            val remoteInput: RemoteInput = RemoteInput.Builder("KEY_TEXT_REPLY")
                .run {
                    setLabel("Write your message here")
                    build()
                }

            // Create an intent
            val intent = Intent(this, MyBroadCastReceiver::class.java)
            intent.action = "REPLY_ACTION"
            intent.putExtra("KEY_NOTIFICATION_ID", notificationId)
            intent.putExtra("KEY_CHANNEL_ID", CHANNEL_ID)
            intent.putExtra("KEY_MESSAGE_ID", 2)

            // Create a pending intent for the reply button
            val replyPendingIntent: PendingIntent = PendingIntent.getBroadcast(
                this,
                101,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )

            // Create reply action and add the remote input
            var action: NotificationCompat.Action = NotificationCompat.Action.Builder(
                R.drawable.oilab_logo,
                "Reply",
                replyPendingIntent
            ).addRemoteInput(remoteInput)
                .setAllowGeneratedReplies(true)
                .build()


            // Build a notification and add the action
            val builder = NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.oilab_logo)
                .setContentTitle("Jones")
                .setContentText("Hello! how are you?")
                .addAction(action)

            // Finally, issue the notification
            NotificationManagerCompat.from(this).apply {
                notify(notificationId, builder.build())
            }
        }

    }
    private fun handsUpNotification()
    {

     var   notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
       var builder = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
        notificationManager!!.cancel(notificationId)
       var remoteView = RemoteViews(packageName, R.layout.notification_layout)
        val switchIntent = Intent(this, MainActivity::class.java)
        val pendingSwitchIntent = PendingIntent.getBroadcast(this, 1212, switchIntent, 0)
        builder!!.setSmallIcon(R.drawable.ic_launcher_background)
        builder!!.setFullScreenIntent(pendingSwitchIntent, true)
        builder!!.priority = Notification.PRIORITY_HIGH
        builder!!.build().flags = Notification.FLAG_AUTO_CANCEL or Notification.FLAG_AUTO_CANCEL
        builder!!.setContent(remoteView)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "ChannelId"
            val channel = NotificationChannel(channelId, "MyChannelName", NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager!!.createNotificationChannel(channel)
            builder!!.setChannelId(channelId)
        }
       var notification = builder!!.build()
        notificationManager!!.notify(notificationId, notification)

    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ (Android 8.0) because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "test_notification"
            val descriptionText = "test_notification_description"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Finally register the channel with system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }


}