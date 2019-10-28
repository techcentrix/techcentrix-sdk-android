package com.techcentrix.sdk.demo_kotlin.fcm

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.techcentrix.sdk.TechCentrixSDK

class DemoFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        if (TechCentrixSDK.isTechCentrixPushMessage(message)) {
            TechCentrixSDK.handlePushMessage(this, message)
        } else {
            // Your existing code for handling push messages
        }
    }

}