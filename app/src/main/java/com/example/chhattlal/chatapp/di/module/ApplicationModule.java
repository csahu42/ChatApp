package com.example.chhattlal.chatapp.di.module;

import android.content.Context;
import com.birbit.android.jobqueue.JobManager;
import com.birbit.android.jobqueue.config.Configuration;
import com.example.chhattlal.chatapp.ChatApp;
import com.example.chhattlal.chatapp.job.BaseJob;
import com.example.chhattlal.chatapp.utils.AppLogger;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;
import dagger.Module;
import dagger.Provides;
import javax.inject.Named;
import javax.inject.Singleton;
import okhttp3.OkHttpClient;
import org.greenrobot.eventbus.EventBus;

@Module public class ApplicationModule {

  private final ChatApp chatApp;

  public ApplicationModule(ChatApp chatApp) {
    this.chatApp = chatApp;
  }

  @Provides @Named("context") @Singleton Context getProvidedAppContext() {
    return chatApp;
  }

  @Provides @Singleton ChatApp getProvidedApp() {
    return chatApp;
  }

  @Provides @Singleton EventBus provideEventBus() {
    return EventBus.builder().logNoSubscriberMessages(false).sendNoSubscriberEvent(false).build();
  }

  @Provides @Singleton JobManager provideJobManager() {
    Configuration.Builder builder =
        new Configuration.Builder(chatApp).customLogger(AppLogger.getLogger())
            .minConsumerCount(1)//always keep at least one consumer alive
            .maxConsumerCount(4)//up to 4 consumers at a time
            .loadFactor(3)//3 jobs per consumer
            .consumerKeepAlive(120)//wait 2 minute
            .injector(job -> {
              if (job instanceof BaseJob) ((BaseJob) job).inject(chatApp.getAppComponent());
            });

    return new JobManager(builder.build());
  }

  @Provides @Singleton Picasso providePicasso(OkHttpClient client) {
    return new Picasso.Builder(chatApp).downloader(new OkHttp3Downloader(client)).build();
  }
}