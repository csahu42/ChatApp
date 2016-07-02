package com.example.chhattlal.chatapp.di.component;

import com.example.chhattlal.chatapp.di.module.ActivityModule;
import com.example.chhattlal.chatapp.scope.AcitivityScope;
import com.example.chhattlal.chatapp.ui.MainActivity;
import dagger.Component;

/**
 * Created by Chhattlal on 21-06-2016.
 */
@AcitivityScope
@Component(dependencies = ApplicationComponent.class, modules = { ActivityModule.class })
public interface ActivityComponent {
  void inject(MainActivity activity);
}
