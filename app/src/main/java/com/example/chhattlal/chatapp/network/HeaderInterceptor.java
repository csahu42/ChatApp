package com.example.chhattlal.chatapp.network;

import android.support.annotation.NonNull;
import com.example.chhattlal.chatapp.config.ApplicationState;
import com.example.chhattlal.chatapp.utils.StringUtil;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * HeaderInterceptor class intercepts all the incoming traffic for cookie from sever and outgoing
 * traffic to attach cookie. This cookie is used by server for authentication purposes.
 *
 * Cookie obtained from the incoming traffic is saved locally via {@link
 * ApplicationState#setCookie(Set)} and same cookie is obtained via {@link
 * ApplicationState#getCookie()} to attach to outgoing traffic.
 *
 * @see ApplicationState#setCookie(Set)
 * @see ApplicationState#getCookie()
 */
public final class HeaderInterceptor implements Interceptor {

  private static final String REQ_COOKIE = "Cookie";
  private static final String RES_COOKIE = "Set-Cookie";
  private final ApplicationState appState;

  public HeaderInterceptor(@NonNull ApplicationState state) {
    this.appState = state;
  }

  @Override public Response intercept(Chain chain) throws IOException {
    Set<String> cookies;

    Request.Builder builder = chain.request().newBuilder();

    cookies = appState.getCookie();
    for (String cookie : cookies) {
      builder.addHeader(REQ_COOKIE, cookie);
    }
    Response response = chain.proceed(builder.build());

    if (!response.headers(RES_COOKIE).isEmpty()) {
      cookies = new HashSet<>();
      for (String setCookie : response.headers(RES_COOKIE))
        if (!StringUtil.isBlank(setCookie)) cookies.add(setCookie);
      if (cookies.size() > 0) {
        appState.setCookie(cookies);
      }
    }

    return response;
  }
}
