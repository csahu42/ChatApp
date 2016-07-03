package com.example.chhattlal.chatapp.di.module;

import android.support.annotation.NonNull;
import com.example.chhattlal.chatapp.ChatApp;
import com.example.chhattlal.chatapp.config.ApplicationState;
import com.example.chhattlal.chatapp.network.ApiService;
import com.example.chhattlal.chatapp.network.HeaderInterceptor;
import com.squareup.moshi.Moshi;
import dagger.Module;
import dagger.Provides;
import java.io.File;
import java.util.concurrent.TimeUnit;
import javax.inject.Singleton;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

import static com.jakewharton.byteunits.DecimalByteUnit.MEGABYTES;

@Module public class NetworkModule {

  private static final int DISK_CACHE_SIZE = (int) MEGABYTES.toBytes(50);
  private static final String LOCAL_URL = "http://192.168.1.118:5100/";

  @NonNull private static OkHttpClient.Builder createOkHttpClient(ChatApp app) {
    // Install an HTTP cache in the application cache directory.
    File cacheDir = new File(app.getCacheDir(), "http");
    if (!cacheDir.exists()) {
      //noinspection ResultOfMethodCallIgnored
      cacheDir.mkdirs();
    }
    Cache cache = new Cache(cacheDir, DISK_CACHE_SIZE);

    return new OkHttpClient.Builder().readTimeout(40, TimeUnit.SECONDS).cache(cache);
  }

  @Provides @Singleton OkHttpClient provideOkHttpClient(ChatApp app) {
    return createOkHttpClient(app).build();
  }

  @Provides @Singleton Moshi provideMoshi() {
    return new Moshi.Builder().build();
  }

  @Provides @Singleton Retrofit provideRetrofit(Moshi moshi, OkHttpClient client,
      ApplicationState state) {
    client = client.newBuilder().addInterceptor(new HeaderInterceptor(state)).build();
    return new Retrofit.Builder().baseUrl(LOCAL_URL)
        .client(client)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build();
  }

  @Provides @Singleton ApiService provideApiService(Retrofit retrofit) {
    return retrofit.create(ApiService.class);
  }
}