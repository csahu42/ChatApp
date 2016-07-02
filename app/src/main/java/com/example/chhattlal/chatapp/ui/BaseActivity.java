package com.example.chhattlal.chatapp.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.example.chhattlal.chatapp.ChatApp;
import com.example.chhattlal.chatapp.di.component.ActivityComponent;
import com.example.chhattlal.chatapp.di.component.DaggerActivityComponent;
import com.example.chhattlal.chatapp.utils.AutoCancelTask.AutoCancelAsyncTask;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Chhattlal on 21-06-2016.
 */

public class BaseActivity extends AppCompatActivity {

  private ActivityComponent activityComponent;
  private final CopyOnWriteArrayList<AutoCancelAsyncTask> tasks = new CopyOnWriteArrayList<>();

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    activityComponent =
        DaggerActivityComponent.builder().applicationComponent(getApp().getAppComponent()).build();
  }

  protected ChatApp getApp() {
    return (ChatApp) getApplicationContext();
  }

  protected ActivityComponent getActivityComponent() {
    return activityComponent;
  }

  public void addLifecycleListener(AutoCancelAsyncTask task) {
    tasks.add(task);
  }

  public void removeLifecycleListener(AutoCancelAsyncTask task) {
    tasks.remove(task);
  }

  @Override protected void onStop() {
    super.onStop();
    final int size = tasks.size();
    if (size == 0) return;
    for (int i = 0; i < size; i++) {
      AutoCancelAsyncTask task = tasks.get(i);
      if (task == null) continue;
      task.onActivityStopped();
    }
    tasks.clear();
  }
}
