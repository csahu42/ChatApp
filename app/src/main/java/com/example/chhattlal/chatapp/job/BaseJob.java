package com.example.chhattlal.chatapp.job;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.birbit.android.jobqueue.Job;
import com.birbit.android.jobqueue.Params;
import com.birbit.android.jobqueue.RetryConstraint;
import com.example.chhattlal.chatapp.di.component.ApplicationComponent;
import javax.inject.Inject;
import org.greenrobot.eventbus.EventBus;
import retrofit2.Response;

public abstract class BaseJob<T> extends Job {

  @Inject transient protected EventBus eventBus;

  public BaseJob(@NonNull final Params params) {
    super(params);
  }

  public abstract void inject(@NonNull final ApplicationComponent appComponent);

  @Override public abstract void onAdded();

  @Override public abstract void onRun() throws Throwable;

  protected final void handleResponse(@NonNull final Response<T> response) {
    if (response.isSuccessful()) {
      onSuccess(response.body());
    } else {
      throw new NetworkException(response.code());
    }
  }

  protected abstract void onSuccess(@Nullable final T response);

  protected void handleAuthFailError() {
    // TODO: 11/05/16
  }

  @Override protected RetryConstraint shouldReRunOnThrowable(@NonNull final Throwable throwable,
      final int runCount, final int maxRunCount) {
    if (throwable instanceof NetworkException && shouldRetry(throwable)) {
      RetryConstraint constraint = RetryConstraint.createExponentialBackoff(runCount, 250);
      constraint.setApplyNewDelayToGroup(true);
      return constraint;
    }
    return RetryConstraint.CANCEL;
  }

  private boolean shouldRetry(@NonNull final Throwable throwable) {
    if (throwable instanceof JobCancelException) return false;
    if (throwable instanceof NetworkException) {
      NetworkException exception = (NetworkException) throwable;
      final int errorCode = exception.getErrorCode();
     // AppLogger.v("Network Exception:" + errorCode, this);
      if (errorCode >= 400 && errorCode < 500) {
        handleAuthFailError();
        return false;
      } else {
        return true;
      }
    }
    return true;
  }

  @Override
  protected abstract void onCancel(final int cancelReason, @Nullable final Throwable throwable);
}
