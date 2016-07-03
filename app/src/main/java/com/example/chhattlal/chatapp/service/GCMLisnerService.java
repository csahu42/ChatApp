package com.example.chhattlal.chatapp.service;

import android.os.Bundle;
import android.util.Log;
import com.google.android.gms.gcm.GcmListenerService;

public class GCMLisnerService extends GcmListenerService {
  //@Inject private NotificationController notificationController;
  private static final String TAG = "MyGcmListenerService";

  /**
   * Called when message is received.
   *
   * @param from SenderID of the sender.
   * @param data Data bundle containing message data as key/value pairs.
   * For Set of keys use data.keySet().
   */
  // [START receive_message]
  @Override public void onMessageReceived(String from, Bundle data) {
    String message = data.getString("message");
    Log.d(TAG, "From: " + from);
    Log.d(TAG, "Message: " + message);

    if (from.startsWith("/topics/")) {
      // message received from some topic.
    } else {
      // normal downstream message.
    }
    sendNotification(message);
  }

  /**
   * Create and show a simple notification containing the received GCM message.
   *
   * @param message GCM message received.
   */
  private void sendNotification(String message) {
  // notificationController.notify(1, message);
  }
}