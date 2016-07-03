package com.example.chhattlal.chatapp.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.example.chhattlal.chatapp.R;
import com.example.chhattlal.chatapp.data.schema.Message;
import com.example.chhattlal.chatapp.data.schema.User;
import com.example.chhattlal.chatapp.databinding.FragmentAddUserBinding;
import com.example.chhattlal.chatapp.utils.StringUtil;
import io.realm.Realm;
import io.realm.RealmList;

public class AddUserFragment extends Fragment {

  private FragmentAddUserBinding addBinding;
  private MainActivity activity;

  public AddUserFragment() {
    // Required empty public constructor
  }

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    activity = (MainActivity) getActivity();
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    addBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_user, container, false);
    return addBinding.getRoot();
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    addBinding.add.setOnClickListener(v -> {
      String userName = addBinding.userName.getText().toString().trim();
      if (!StringUtil.isBlank(userName)) addUser(userName);
    });
  }

  private void addUser(String userName) {
    Realm realm = Realm.getDefaultInstance();
    realm.executeTransactionAsync(bgRealm -> {
      User user = realm.createObject(User.class);
      user.setUserId(System.currentTimeMillis());
      user.setUserName(userName);
      RealmList<Message> messages = new RealmList<>();
      user.setMessages(messages);
    }, () -> {
      activity.FragmentTransaction(new UserListFragment());
      // Transaction was a success.
    }, error -> {
      // Transaction failed and was automatically canceled.
      Toast.makeText(activity, "Not added, try again!!!!", Toast.LENGTH_SHORT).show();
    });
  }
}