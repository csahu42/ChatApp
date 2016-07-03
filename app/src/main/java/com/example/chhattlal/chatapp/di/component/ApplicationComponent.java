package com.example.chhattlal.chatapp.di.component;

import com.example.chhattlal.chatapp.ChatApp;
import com.example.chhattlal.chatapp.di.module.ApplicationModule;
import com.example.chhattlal.chatapp.di.module.NetworkModule;
import dagger.Component;
import javax.inject.Singleton;
import okhttp3.OkHttpClient;


@Singleton @Component(modules = { ApplicationModule.class, NetworkModule.class })
public interface ApplicationComponent {

  ChatApp chatApp();

  OkHttpClient okHttpClient();
}
