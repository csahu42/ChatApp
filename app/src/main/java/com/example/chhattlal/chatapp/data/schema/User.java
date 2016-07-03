package com.example.chhattlal.chatapp.data.schema;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class User extends RealmObject {

  @PrimaryKey private long userId;
  @Required private String userName;
  @Ignore private String picURL;
  @Required private RealmList<Message> messages;

  public long getUserId() {
    return userId;
  }

  public void setUserId(long userId) {
    this.userId = userId;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getPicURL() {
    return picURL;
  }

  public void setPicURL(String picURL) {
    this.picURL = picURL;
  }

  public RealmList<Message> getMessages() {
    return messages;
  }

  public void setMessages(RealmList<Message> messages) {
    this.messages = messages;
  }
}

