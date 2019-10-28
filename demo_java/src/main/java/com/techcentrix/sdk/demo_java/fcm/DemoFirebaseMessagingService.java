package com.techcentrix.sdk.demo_java.fcm;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.techcentrix.sdk.TechCentrixSDK;

public class DemoFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        if (TechCentrixSDK.isTechCentrixPushMessage(message)) {
            TechCentrixSDK.handlePushMessage(this, message);
        } else {
            // Your existing code for handling push messages
        }
    }

}
