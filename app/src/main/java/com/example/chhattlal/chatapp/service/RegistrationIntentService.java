package com.example.chhattlal.chatapp.service;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import com.example.chhattlal.chatapp.R;
import com.google.android.gms.gcm.GcmPubSub;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import java.io.IOException;

/**
 * Created by Sahu on 7/2/2016.
 */

public class RegistrationIntentService extends IntentService {

  private static final String TAG = "RegIntentService";
  private static final String[] TOPICS = { "global" };

  public RegistrationIntentService() {
    super(TAG);
  }

  @Override protected void onHandleIntent(Intent intent) {
    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
    try {
      InstanceID instanceID = InstanceID.getInstance(this);
      String token = instanceID.getToken(getString(R.string.gcm_defaultSenderId),
          GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
      // [END get_token]
      Log.i(TAG, "GCM Registration Token: " + token);

      // TODO: Implement this method to send any registration to your app's servers.
      sendRegistrationToServer(token);

      // Subscribe to topic channels
      subscribeTopics(token);
    } catch (Exception e) {
      Log.d(TAG, "Failed to complete token refresh", e);

      /*sharedPreferences.edit()
          .putBoolean(QuickstartPreferences.SENT_TOKEN_TO_SERVER, false)
          .apply();*/
    }
   /* Intent registrationComplete = new Intent(QuickstartPreferences.REGISTRATION_COMPLETE);
    LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete);*/
  }

  /**
   * Persist registration to third-party servers.
   *
   * Modify this method to associate the user's GCM registration token with any server-side account
   * maintained by your application.
   *
   * @param token The new token.
   */
  private void sendRegistrationToServer(String token) {
    // Add custom implementation, as needed.
  }

  /**
   * Subscribe to any GCM topics of interest, as defined by the TOPICS constant.
   *
   * @param token GCM token
   * @throws IOException if unable to reach the GCM PubSub service
   */
  // [START subscribe_topics]
  private void subscribeTopics(String token) throws IOException {
    GcmPubSub pubSub = GcmPubSub.getInstance(this);
    for (String topic : TOPICS) {
      pubSub.subscribe(token, "/topics/" + topic, null);
    }
  }
  // [END subscribe_topics]
}