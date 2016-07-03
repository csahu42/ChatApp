package com.example.chhattlal.chatapp.data.schema;

import android.support.annotation.NonNull;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class Message extends RealmObject {

  @PrimaryKey private long msgId;
  @Required private String message;
  @Required private String userName;
  @Required private long createdTime;

  public long getMessageId() {
    return msgId;
  }

  public void setMessageId(long msgId) {
    this.msgId = msgId;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(@NonNull String userName) {
    this.userName = userName;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(@NonNull String message) {
    this.message = message;
  }

  public long getCreatedTime() {
    return createdTime;
  }

  public void setCreatedTime(long createdTime) {
    this.createdTime = createdTime;
  }
}
