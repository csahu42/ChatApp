package com.example.chhattlal.chatapp.config;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import com.example.chhattlal.chatapp.utils.StringUtil;
import java.util.Set;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * This class is the interface between {@link ChatAppPreferences} and other classes. It is used so
 * that an item once queried from {@link SharedPreferences}, which are maintained for this app
 * through {@link ChatAppPreferences}, is not queried again and maintained in memory.
 *
 * It also stores the temporary states which shouldn't be stored in {@link SharedPreferences}.
 */
@SuppressWarnings("unused") @Singleton public final class ApplicationState {

  private final ChatAppPreferences preferences;
  private Set<String> cookie;
  private long userId;
  private String name;
  private String email;

  @Inject public ApplicationState(ChatAppPreferences preferences) {
    this.preferences = preferences;
    this.userId = -1;
  }

  @NonNull public Set<String> getCookie() {
    if (cookie == null) cookie = preferences.getCookie();
    return cookie;
  }

  public void setCookie(@NonNull final Set<String> cookie) {
    preferences.setCookie(cookie);
    this.cookie = cookie;
  }

  public long getUserId() {
    if (userId < 0) userId = preferences.getUserId();
    return userId;
  }

  public void setUserId(final long userId) {
    preferences.setUserId(userId);
    this.userId = userId;
  }

  @NonNull public String getName() {
    if (StringUtil.isBlank(name)) name = preferences.getName();
    return name;
  }

  public void setName(@NonNull final String name) {
    preferences.setName(name);
    this.name = name;
  }
  public boolean isRegistrationComplete() {
    return preferences.isRegistrationComplete();
  }

  public void setRegistrationComplete(final boolean registrationComplete) {
    preferences.setRegistrationComplete(registrationComplete);
  }
}
