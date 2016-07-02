package com.example.chhattlal.chatapp.utils;

import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

@SuppressWarnings("unused") public final class StringUtil {

  private StringUtil() {
    throw new AssertionError("No instance please");
  }

  @CheckResult
  public static String valueOrDefault(@Nullable String string, @NonNull String defaultString) {
    return isBlank(string) ? defaultString : string;
  }

  @CheckResult public static boolean isBlank(@Nullable CharSequence cs) {
    return (cs == null || cs.toString().trim().length() == 0);
  }

  @CheckResult public static String truncateAt(@NonNull String string, int length) {
    return string.length() > length ? string.substring(0, length) : string;
  }

  @CheckResult public static boolean areEqual(@Nullable String s1, @Nullable String s2) {
    return !(s1 == null || s2 == null) && s1.equals(s2);
  }
}
