package com.example.chhattlal.chatapp.utils;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import com.birbit.android.jobqueue.log.CustomLogger;
import com.example.chhattlal.chatapp.BuildConfig;
import java.util.Locale;

public final class AppLogger {
  private static AppLogger instance;
 /* private final FirebaseAnalytics analytics;

  private AppLogger(@NonNull final FirebaseAnalytics analytics) {
    instance = this;
    this.analytics = analytics;
  }

  public static void initialise(@NonNull final FirebaseAnalytics analytics) {
    new AppLogger(analytics);
  }
*/
  public static CustomLogger getLogger() {
    return new CustomLogger() {
      @Override public boolean isDebugEnabled() {
        return BuildConfig.DEBUG;
      }

      @Override public void d(String text, Object... args) {
        AppLogger.d(text, args);
      }

      @Override public void e(Throwable t, String text, Object... args) {
        AppLogger.e(t, text, args);
      }

      @Override public void e(String text, Object... args) {
        AppLogger.v(text, args);
      }

      @Override public void v(String text, Object... args) {
        AppLogger.v(text, args);
      }
    };
  }

  @NonNull private static String getTag() {
    return Thread.currentThread().getName();
  }

  static void d(@NonNull final String msg, @NonNull final Object caller) {
    if (instance == null) return;
    instance.debugLog(msg, caller);
  }

  public static void v(@NonNull final String msg, @NonNull final Object caller) {
    if (instance == null) return;
    instance.verboseLog(msg, caller);
  }

  public static void e(@NonNull final Throwable t, @NonNull final Object caller) {
    if (instance == null) return;
    instance.errorLog(t, caller.getClass().getSimpleName());
  }

  private static void e(@NonNull final Throwable t, @NonNull final String text, Object... args) {
    if (instance == null) return;
    instance.errorLog(t, String.format(Locale.ENGLISH, text, args));
  }

  public static void logEvent(@NonNull final String event, @Nullable final Bundle bundle) {
    if (instance == null) return;
    instance.eventLog(event, bundle);
  }

  public static void addUserInfo(@NonNull final String key, @NonNull final String value) {
    if (instance == null) return;
    instance.addUserInformation(key, value);
  }

  private void debugLog(@NonNull final String msg, @NonNull final Object caller) {
    final String name = caller.getClass().getSimpleName();
    if (BuildConfig.DEBUG && !StringUtil.isBlank(msg)) Log.d(getTag(), name + ":" + msg);
  }

  private void verboseLog(@NonNull final String msg, @NonNull final Object caller) {
    final String name = caller.getClass().getSimpleName();
    if (!StringUtil.isBlank(msg)) {
      if (BuildConfig.DEBUG) {
        Log.v(getTag(), name + ":" + msg);
      } else {
        //FirebaseCrash.logcat(Log.VERBOSE, getTag(), name + ":" + msg);
      }
    }
  }

  private void errorLog(@NonNull final Throwable t, @NonNull final String message) {
    if (BuildConfig.DEBUG) {
      Log.e(getTag(), message, t);
    } else {
     /* FirebaseCrash.logcat(Log.ERROR, getTag(), message);
      FirebaseCrash.report(t);*/
    }
  }

  private void eventLog(@NonNull final String event, @Nullable final Bundle bundle) {
    if (BuildConfig.DEBUG) return;
    //analytics.logEvent(event, bundle);
  }

  private void addUserInformation(@NonNull final String key, @NonNull final String value) {
    //analytics.setUserProperty(key, value);
  }
}
