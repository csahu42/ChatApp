package com.example.chhattlal.chatapp.ui;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.chhattlal.chatapp.R;
import com.example.chhattlal.chatapp.data.schema.Message;
import com.example.chhattlal.chatapp.databinding.ItemMessageBinding;
import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;

public class ChatAdapter extends RealmRecyclerViewAdapter<Message, ChatAdapter.ViewHolder> {

  private final ChatActivity activity;
  private LayoutInflater inflater;
  private Message message;

   ChatAdapter(ChatActivity activity, OrderedRealmCollection<Message> data) {
    super(activity, data, true);
    this.activity = activity;
  }

  @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    if (inflater == null) inflater = LayoutInflater.from(parent.getContext());
    ItemMessageBinding binding =
        DataBindingUtil.inflate(inflater, R.layout.item_message, parent, false);
    return new ViewHolder(binding);
  }

  @Override public void onBindViewHolder(ViewHolder holder, int position) {
    //noinspection ConstantConditions
    message = getData().get(position);
    assert message != null;
    holder.getBinding().setItem(message);
    holder.getBinding().executePendingBindings();
  }

  class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {

    private ItemMessageBinding binding;

    ViewHolder(ItemMessageBinding binding) {
      super(binding.getRoot());
      this.binding = binding;
      binding.getRoot().setOnLongClickListener(this);
    }

    ItemMessageBinding getBinding() {
      return binding;
    }

    @Override public boolean onLongClick(View v) {
      //activity.deleteItem(message);
      return true;
    }
  }
}