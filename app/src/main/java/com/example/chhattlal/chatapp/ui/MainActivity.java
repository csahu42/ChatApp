package com.example.chhattlal.chatapp.ui;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import com.example.chhattlal.chatapp.R;
import com.example.chhattlal.chatapp.databinding.ActivityMainBinding;
import com.example.chhattlal.chatapp.service.RegistrationIntentService;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import io.realm.Realm;

public class MainActivity extends BaseActivity {

  private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
  private static final String TAG = "MainActivity";

  private BroadcastReceiver mRegistrationBroadcastReceiver;
  private boolean isReceiverRegistered;

  private Realm realm;
  private ActivityMainBinding mainBinding;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    getActivityComponent().inject(this);
    mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
    realm = Realm.getDefaultInstance();
    realm.setAutoRefresh(true);
    // Registering BroadcastReceiver
    // registerReceiver();

    if (checkPlayServices()) {
      // Start IntentService to register this application with GCM.
      Intent intent = new Intent(this, RegistrationIntentService.class);
      startService(intent);
    }

    FragmentManager fragmentManager = getSupportFragmentManager();
    FragmentTransaction transaction = fragmentManager.beginTransaction();
    transaction.add(R.id.container, new UserListFragment());
    transaction.addToBackStack(null);
    transaction.commit();
  }

  @Override protected void onResume() {
    super.onResume();
    // registerReceiver();
  }

  @Override protected void setRootView() {
    rootView = mainBinding.getRoot();
  }

  @Override protected void onPause() {
    LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
    isReceiverRegistered = false;
    super.onPause();
  }

  public Realm getRealm() {
    return realm;
  }
   /* private void registerReceiver(){
        if(!isReceiverRegistered) {
            LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(QuickstartPreferences.REGISTRATION_COMPLETE));
            isReceiverRegistered = true;
        }
    }*/

  /**
   * Check the device to make sure it has the Google Play Services APK. If
   * it doesn't, display a dialog that allows users to download the APK from
   * the Google Play Store or enable it in the device's system settings.
   */
  private boolean checkPlayServices() {
    GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
    int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
    if (resultCode != ConnectionResult.SUCCESS) {
      if (apiAvailability.isUserResolvableError(resultCode)) {
        apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST).show();
      } else {
        Log.i(TAG, "This device is not supported.");
        finish();
      }
      return false;
    }
    return true;
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    realm.close();
  }

  void FragmentTransaction(Fragment fragment) {
    FragmentManager fragmentManager = getSupportFragmentManager();
    FragmentTransaction transaction = fragmentManager.beginTransaction();
    transaction.replace(R.id.container, fragment);
    transaction.addToBackStack(null);
    transaction.commit();
  }

  void startChatRoom(long userId) {
    startActivity(ChatActivity.getIntentFor(this, userId));
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.main_menu, menu);
    return true;
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.add_user:
        FragmentTransaction(new AddUserFragment());
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }
}
