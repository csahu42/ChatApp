package com.example.chhattlal.chatapp.controller;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Process;
import android.support.annotation.Nullable;
import com.birbit.android.jobqueue.JobManager;
import com.example.chhattlal.chatapp.config.ApplicationState;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton public class ChatController {

  private final JobManager jobManager;
  private final NotificationController notification;
  private final long myId;
  private final ChatHandler handler;
  @Nullable private String activeConversation;

  @Inject public ChatController(ApplicationState state, JobManager manager,
      NotificationController notification) {
    this.jobManager = manager;
    this.myId = state.getUserId();
    HandlerThread thread = new HandlerThread("Chat", Process.THREAD_PRIORITY_BACKGROUND);
    thread.start();
    handler = new ChatHandler(thread.getLooper());
    this.notification = notification;
  }

  private class ChatHandler extends Handler {

    private ChatHandler(Looper looper) {
      super(looper);
    }

    @Override public void handleMessage(android.os.Message msg) {

    }
  }
}
