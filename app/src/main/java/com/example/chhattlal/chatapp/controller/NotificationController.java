package com.example.chhattlal.chatapp.controller;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.ContextCompat;
import com.birbit.android.jobqueue.JobManager;
import com.example.chhattlal.chatapp.ChatApp;
import com.example.chhattlal.chatapp.R;
import com.example.chhattlal.chatapp.ui.ChatActivity;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton public class NotificationController {

  private final ChatApp app;
  private final JobManager jobManager;
  private final NotificationManagerCompat manager;
  private final long[] pattern;
  private final Uri soundUri;

  @Inject public NotificationController(ChatApp app, JobManager jobManager) {
    this.app = app;
    this.jobManager = jobManager;
    this.manager = NotificationManagerCompat.from(app);
    this.pattern = new long[] { 500, 500 };
    this.soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
  }

  @SuppressLint("SwitchIntDef")
  public void notify(int mType, String text) {
    NotificationCompat.Builder builder = new NotificationCompat.Builder(app);
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      builder.setSmallIcon(R.mipmap.ic_launcher);
      builder.setColor(ContextCompat.getColor(app, R.color.colorPrimaryDark));
      builder.setCategory(Notification.CATEGORY_MESSAGE);
    } else {
      builder.setSmallIcon(R.mipmap.ic_launcher);
    }
    builder.setContentTitle("ChatApp");

        builder.setTicker(text);

    Intent resultIntent;
      resultIntent = new Intent(app.getApplicationContext(), ChatActivity.class);
    builder.setPriority(Notification.PRIORITY_HIGH);
    PendingIntent pendingIntent =
        PendingIntent.getActivity(app, 123, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    builder.setContentIntent(pendingIntent);
    builder.setWhen(System.currentTimeMillis());
    builder.setAutoCancel(true);
    builder.setOnlyAlertOnce(true);
    builder.setLights(Color.WHITE, 500, 500);
    builder.setVibrate(pattern);
    builder.setSound(soundUri);
    builder.setLocalOnly(true);
    manager.notify(2016, builder.build());
  }
}
