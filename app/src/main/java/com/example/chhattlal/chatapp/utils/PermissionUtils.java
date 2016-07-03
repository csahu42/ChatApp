package com.example.chhattlal.chatapp.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import com.example.chhattlal.chatapp.R;
import com.example.chhattlal.chatapp.ui.BaseActivity;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unused") public final class PermissionUtils {

  public static final byte PERMISSION_REQUEST = Byte.MAX_VALUE;
  public static final byte INTERNET = 0b0;
  public static final byte INTERNET_STATE = 0b1;
  public static final byte ACCOUNTS = 0b10;
  public static final byte CONTACTS = 0b11;
  public static final byte PHONE = 0b100;
  public static final byte CALL_LOG = 0b101;
  public static final byte SMS = 0b110;
  public static final byte STORAGE = 0b111;
  public static final byte AUDIO = 0b1000;
  public static final byte CAMERA = 0b1001;
  public static final byte LOCATION = 0b1010;
  public static final byte WAKE = 0b1011;
  public static final byte VIBRATE_ACCESS = 0b1100;

  public static final String ACCESS_INTERNET = Manifest.permission.INTERNET;
  public static final String NETWORK_STATE = Manifest.permission.ACCESS_NETWORK_STATE;
  public static final String GET_ACCOUNTS = Manifest.permission.GET_ACCOUNTS;
  public static final String READ_CONTACTS = Manifest.permission.READ_CONTACTS;
  public static final String READ_PHONE_STATE = Manifest.permission.READ_PHONE_STATE;
  public static final String READ_CALL_LOG = Manifest.permission.READ_CALL_LOG;
  public static final String READ_SMS = Manifest.permission.READ_SMS;

  public static final String WRITE_EXTERNAL_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;
  public static final String RECORD_AUDIO = Manifest.permission.RECORD_AUDIO;
  public static final String RECORD_CAMERA = Manifest.permission.CAMERA;
  public static final String ACCESS_FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;

  public static final String WAKE_LOCK = Manifest.permission.WAKE_LOCK;
  public static final String VIBRATE = Manifest.permission.VIBRATE;

  private static final Map<Byte, String> PERMISSION_STRING;

  static {
    Map<Byte, String> aMap = new HashMap<>(13);
    aMap.put(INTERNET, ACCESS_INTERNET);
    aMap.put(INTERNET_STATE, NETWORK_STATE);
    aMap.put(ACCOUNTS, GET_ACCOUNTS);
    aMap.put(CONTACTS, READ_CONTACTS);
    aMap.put(PHONE, READ_PHONE_STATE);
    aMap.put(CALL_LOG, READ_CALL_LOG);
    aMap.put(SMS, READ_SMS);
    aMap.put(STORAGE, WRITE_EXTERNAL_STORAGE);
    aMap.put(AUDIO, RECORD_AUDIO);
    aMap.put(CAMERA, RECORD_CAMERA);
    aMap.put(LOCATION, ACCESS_FINE_LOCATION);
    aMap.put(WAKE, WAKE_LOCK);
    aMap.put(VIBRATE_ACCESS, VIBRATE);
    PERMISSION_STRING = Collections.unmodifiableMap(aMap);
  }

  /**
   * Verify whether the requested permission is granted or not and if not then request for that
   * permission if specified.
   *
   * @param activity Activity from which request has been generated.
   * @param permission The permission which needs to be verified.
   * @param makeRequest Whether to request for permission in case of {@link
   * PackageManager#PERMISSION_DENIED}
   */
  public static boolean isPermissionGranted(@NonNull BaseActivity activity,
      @PermissionType byte permission, boolean makeRequest) {
    final String permissionString = PERMISSION_STRING.get(permission);
    final boolean isPermitted =
        verifyPermissions(ContextCompat.checkSelfPermission(activity, permissionString));
    if (!isPermitted && makeRequest) requestPermission(activity, permission, permissionString);
    return isPermitted;
  }

  /**
   * Check that all given permissions have been granted by verifying that each entry in the
   * given array is of the value {@link PackageManager#PERMISSION_GRANTED}.
   *
   * @see Activity#onRequestPermissionsResult(int, String[], int[])
   */
  public static boolean verifyPermissions(int... grantResults) {
    // At least one result must be checked.
    if (grantResults.length < 1) {
      return false;
    }
    // Verify that each required permission has been granted, otherwise return false.
    for (int result : grantResults) {
      if (result != PackageManager.PERMISSION_GRANTED) {
        return false;
      }
    }
    return true;
  }

  /**
   * Request for the permission and displays the rationale for the permission for API >= 23.
   */
  private static void requestPermission(BaseActivity activity, @SuppressLint("SupportAnnotationUsage")
  @PermissionType byte permission,
      String permissionString) {
    final String[] permissionRationale =
        activity.getResources().getStringArray(R.array.permissionRationale);
    final String rationale = permissionRationale[permission];
    if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permissionString)) {
      activity.showSnack(rationale, Snackbar.LENGTH_INDEFINITE,
          view -> ActivityCompat.requestPermissions(activity, new String[] { permissionString },
              PERMISSION_REQUEST));
    } else {
      ActivityCompat.requestPermissions(activity, new String[] { permissionString },
          PERMISSION_REQUEST);
    }
  }
}
