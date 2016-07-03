package com.example.chhattlal.chatapp.config;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import java.util.HashSet;
import java.util.Set;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

/**
 * This class manages all the code related to the information saved locally in
 * {@link SharedPreferences}.
 */
@Singleton public class ChatAppPreferences {

  private final SharedPreferences preferences;
  private final String COOKIE = "cookie";
  private final String USER_ID = "userId";
  private final String IS_REGISTERED = "isRegistered";
  private final String NAME = "name";

  @Inject ChatAppPreferences(@Named("app") Context appContext) {
    final String appName = appContext.getPackageName();
    preferences = appContext.getSharedPreferences(appName, Context.MODE_PRIVATE);
  }

  @NonNull Set<String> getCookie() {
    return preferences.getStringSet(COOKIE, new HashSet<>());
  }

  void setCookie(@NonNull final Set<String> cookie) {
    preferences.edit().putStringSet(COOKIE, cookie).apply();
  }



  long getUserId() {
    return preferences.getLong(USER_ID, -1);
  }

  void setUserId(final long userId) {
    preferences.edit().putLong(USER_ID, userId).apply();
  }



  boolean isRegistrationComplete() {
    return preferences.getBoolean(IS_REGISTERED, false);
  }

  void setRegistrationComplete(final boolean registrationComplete) {
    preferences.edit().putBoolean(IS_REGISTERED, registrationComplete).apply();
  }

  @NonNull public String getName() {
    return preferences.getString(NAME, "");
  }

  public void setName(@NonNull final String name) {
    preferences.edit().putString(NAME, name).apply();
  }
}
