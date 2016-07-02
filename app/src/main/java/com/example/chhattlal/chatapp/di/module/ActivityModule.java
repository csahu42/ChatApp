package com.example.chhattlal.chatapp.di.module;

import android.app.Activity;
import android.content.Context;
import com.example.chhattlal.chatapp.scope.AcitivityScope;
import dagger.Module;
import dagger.Provides;
import javax.inject.Named;

/**
 * Created by Chhattlal on 21-06-2016.
 */
@Module public class ActivityModule {

  private final Activity activity;

  public ActivityModule(Activity activity) {
    this.activity = activity;
  }

  @Provides @Named("context") @AcitivityScope Context getProvidedContext()

  {
    return activity.getApplicationContext();
  }

  @Provides @Named("activity") @AcitivityScope Context getProvidedActivity()

  {
    return activity;
  }
}
