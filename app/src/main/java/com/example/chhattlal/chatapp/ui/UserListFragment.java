package com.example.chhattlal.chatapp.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.chhattlal.chatapp.R;
import com.example.chhattlal.chatapp.data.schema.User;
import com.example.chhattlal.chatapp.databinding.FragmentUserListBinding;
import com.example.chhattlal.chatapp.utils.DividerItemDecoration;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserListFragment extends Fragment {

  private FragmentUserListBinding listBinding;
  private MainActivity activity;
  private RealmResults<User> users;
  private UserAdapter userAdapter;
  private Realm realm;

  public UserListFragment() {
    // Required empty public constructor
  }

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    activity = (MainActivity) getActivity();
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    listBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_user_list, container, false);
    return listBinding.getRoot();
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    realm = Realm.getDefaultInstance();
    users = realm.where(User.class).findAllAsync();
    userAdapter = new UserAdapter((MainActivity) getActivity(), users);
    listBinding.userList.setAdapter(userAdapter);
    listBinding.userList.addItemDecoration(new DividerItemDecoration(
        ContextCompat.getDrawable(getContext(), R.drawable.divider_horizontal)));
  }

  @Override public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    users.addChangeListener(element -> userAdapter.notifyDataSetChanged());
  }

  @Override public void onDestroy() {
    super.onDestroy();
    realm.close();
    users.removeChangeListeners();
  }
}
