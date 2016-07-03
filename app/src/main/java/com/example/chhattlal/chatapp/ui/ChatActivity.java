package com.example.chhattlal.chatapp.ui;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import com.example.chhattlal.chatapp.R;
import com.example.chhattlal.chatapp.data.schema.Message;
import com.example.chhattlal.chatapp.data.schema.User;
import com.example.chhattlal.chatapp.databinding.ActivityChatRoomBinding;
import com.example.chhattlal.chatapp.utils.DividerItemDecoration;
import io.realm.Realm;
import io.realm.RealmChangeListener;

public class ChatActivity extends BaseActivity {

  static Intent getIntentFor(Context context, long userId) {
    final Intent intent = new Intent(context, ChatActivity.class);
    intent.putExtra(USER_ID, userId);
    return intent;
  }

  private Realm realm;
  private RealmChangeListener realmListener;
  private ActivityChatRoomBinding chatRoomBinding;
  private static final String USER_ID = "userId";
  private long userId;
  private User user;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    getActivityComponent().inject(this);
    realm = Realm.getDefaultInstance();
    if (savedInstanceState != null) {
      userId = savedInstanceState.getLong(USER_ID);
    } else {
      userId = getIntent().getLongExtra(USER_ID, 101);
    }
    chatRoomBinding = DataBindingUtil.setContentView(this, R.layout.activity_chat_room);
    setUpRecyclerView();

    chatRoomBinding.messageEditText.addTextChangedListener(new TextWatcher() {
      @Override public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
      }

      @Override public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (charSequence.toString().trim().length() > 0) {
          chatRoomBinding.sendButton.setEnabled(true);
        } else {
          chatRoomBinding.sendButton.setEnabled(false);
        }
      }

      @Override public void afterTextChanged(Editable editable) {
      }
    });

    chatRoomBinding.sendButton.setOnClickListener(view -> {
      sendMessageCall(chatRoomBinding.messageEditText.getText().toString().trim());
      chatRoomBinding.messageEditText.setText("");
    });
  }

  @Override protected void setRootView() {
    rootView = chatRoomBinding.getRoot();
  }

  private void setUpRecyclerView() {
    user = realm.where(User.class).equalTo("userId", userId).findFirst();
    user.addChangeListener(realmListener);
    final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
    layoutManager.setStackFromEnd(true);
    chatRoomBinding.messageList.setLayoutManager(layoutManager);
    ChatAdapter chatAdapter = new ChatAdapter(this, user.getMessages());
    chatRoomBinding.messageList.setAdapter(chatAdapter);
    chatRoomBinding.messageList.setHasFixedSize(true);
    chatRoomBinding.messageList.addItemDecoration(
        new DividerItemDecoration(ContextCompat.getDrawable(this, R.drawable.divider_horizontal)));
    realmListener = element -> {

    };
  }

  private void sendMessageCall(String textMessage) {
    Message message = realm.createObject(Message.class);
    realm.beginTransaction();
    message.setMessageId(System.currentTimeMillis());
    message.setMessage(textMessage);
    user.getMessages().add(message);
    realm.commitTransaction();
  }

  void deleteItem(Message item) {
    final long id = item.getMessageId();
    realm.executeTransactionAsync(
        realm1 -> realm1.where(Message.class).equalTo("msgId", id).findAll().deleteAllFromRealm());
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    user.removeChangeListeners();
    realm.close();
  }
}
