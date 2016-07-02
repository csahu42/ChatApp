package com.example.chhattlal.chatapp;

import android.app.Application;
import com.example.chhattlal.chatapp.di.component.ApplicationComponent;
import com.example.chhattlal.chatapp.di.component.DaggerApplicationComponent;
import com.example.chhattlal.chatapp.di.module.ApplicationModule;

/**
 * Created by Chhattlal on 21-06-2016.
 */

public class ChatApp extends Application {

  private ApplicationComponent appComponent;

  public ApplicationComponent getAppComponent() {
    return appComponent;
  }

  @Override public void onCreate() {
    super.onCreate();
    appComponent = DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(this)).build();
  }
}
