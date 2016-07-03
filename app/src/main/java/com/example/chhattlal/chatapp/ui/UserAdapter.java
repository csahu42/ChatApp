package com.example.chhattlal.chatapp.ui;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.chhattlal.chatapp.R;
import com.example.chhattlal.chatapp.data.schema.User;
import com.example.chhattlal.chatapp.databinding.UserItemBinding;
import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;

/**
 * Created by Sahu on 7/2/2016.
 */

public class UserAdapter extends RealmRecyclerViewAdapter<User, UserAdapter.ViewHolder> {

  private final MainActivity activity;
  private LayoutInflater inflater;
  private User user;

  UserAdapter(MainActivity activity, OrderedRealmCollection<User> data) {
    super(activity, data, true);
    this.activity = activity;
  }

  @Override public UserAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    if (inflater == null) inflater = LayoutInflater.from(parent.getContext());
    UserItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.user_item, parent, false);
    return new UserAdapter.ViewHolder(binding);
  }

  @Override public void onBindViewHolder(UserAdapter.ViewHolder holder, int position) {
    //noinspection ConstantConditions
    user = getData().get(position);
    assert user != null;
    holder.getBinding().setItem(user);
    holder.getBinding().executePendingBindings();
  }

  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private UserItemBinding binding;

    ViewHolder(UserItemBinding binding) {
      super(binding.getRoot());
      this.binding = binding;
      binding.getRoot().setOnClickListener(this);
    }

    UserItemBinding getBinding() {
      return binding;
    }

    @Override public void onClick(View v) {
      activity.startChatRoom(user.getUserId());
    }
  }
}