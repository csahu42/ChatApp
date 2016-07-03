package com.example.chhattlal.chatapp.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.example.chhattlal.chatapp.ChatApp;
import com.example.chhattlal.chatapp.R;
import com.example.chhattlal.chatapp.di.component.ActivityComponent;
import com.example.chhattlal.chatapp.di.component.DaggerActivityComponent;
import com.example.chhattlal.chatapp.utils.AutoCancelTask.AutoCancelAsyncTask;
import com.example.chhattlal.chatapp.utils.PermissionUtils;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class BaseActivity extends AppCompatActivity {

  private ActivityComponent activityComponent;
  protected View rootView;
  private int backPressCount = 0;
  private final CopyOnWriteArrayList<AutoCancelAsyncTask> tasks = new CopyOnWriteArrayList<>();

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    activityComponent =
        DaggerActivityComponent.builder().applicationComponent(getApp().getAppComponent()).build();
  }

  @Override protected void onStart() {
    super.onStart();
    setRootView();
  }

  abstract protected void setRootView();

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

  public void showSnack(String message, @Snackbar.Duration int duration,
      @NonNull View.OnClickListener clickListener) {
    Snackbar.make(rootView, message, duration).setAction(R.string.ok, clickListener).show();
  }

  protected void showSnack(@NonNull View view, @StringRes int message,
      @Snackbar.Duration int duration) {
    Snackbar.make(view, message, duration).show();
  }

  public void showSnack(@StringRes int message, @Snackbar.Duration int duration) {
    showSnack(rootView, message, duration);
  }

  @Override public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[],
      @NonNull int[] grantResults) {
    switch (requestCode) {
      case PermissionUtils.PERMISSION_REQUEST:
        onPermissionResult(permissions, grantResults);
      default:
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
  }

  protected void onPermissionResult(@NonNull String permissions[], @NonNull int[] grantResults) {
    //This method must be overridden by the child activity
  }

  @Override public void onBackPressed() {
    backPressCount++;
    final Timer timer = new Timer("BackPress", false);
    timer.schedule(new TimerTask() {
      @Override public void run() {
        backPressCount = 0;
        timer.cancel();
      }
    }, 2000);
    if (backPressCount == 1) {
      showSnack(rootView, R.string.exitMessage, Snackbar.LENGTH_SHORT);
    } else {
      super.onBackPressed();
    }
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
