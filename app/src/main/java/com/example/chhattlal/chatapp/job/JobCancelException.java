package com.example.chhattlal.chatapp.job;

public class JobCancelException extends RuntimeException {

  private final int cancelReason;

  public JobCancelException(int cancelReason) {
    this.cancelReason = cancelReason;
  }

  public int getCancelReason() {
    return cancelReason;
  }
}
