package com.example.chhattlal.chatapp;

import android.app.Application;
import com.example.chhattlal.chatapp.data.Migration;
import com.example.chhattlal.chatapp.di.component.ApplicationComponent;
import com.example.chhattlal.chatapp.di.component.DaggerApplicationComponent;
import com.example.chhattlal.chatapp.di.module.ApplicationModule;
import io.realm.Realm;
import io.realm.RealmConfiguration;

public class ChatApp extends Application {

  private ApplicationComponent appComponent;

  public ApplicationComponent getAppComponent() {
    return appComponent;
  }

  @Override public void onCreate() {
    super.onCreate();
    initRealm();
    appComponent =
        DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(this)).build();
  }

  private void initRealm() {
    final String realmName = getPackageName() + ".realm";
   /* RealmConfiguration config = new RealmConfiguration.Builder(this).name(realmName)
        .deleteRealmIfMigrationNeeded()
        .build();*/
    RealmConfiguration config = new RealmConfiguration.Builder(this).name(realmName)
        .schemaVersion(0)
        .migration(new Migration())
        .build();
    Realm.setDefaultConfiguration(config);
  }
}
