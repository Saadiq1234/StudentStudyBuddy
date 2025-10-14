package com.studybuddy.app.notifications

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        super.onNewToken(token)
     // send token to your server if needed
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        val title = remoteMessage.notification?.title ?: "Study Buddy"
        val body = remoteMessage.notification?.body ?: ""
        NotificationHelper.showNotification(applicationContext, title, body)
    }
}
