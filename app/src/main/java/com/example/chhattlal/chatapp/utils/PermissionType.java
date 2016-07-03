package com.example.chhattlal.chatapp.utils;

import android.support.annotation.IntDef;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static com.example.chhattlal.chatapp.utils.PermissionUtils.ACCOUNTS;
import static com.example.chhattlal.chatapp.utils.PermissionUtils.AUDIO;
import static com.example.chhattlal.chatapp.utils.PermissionUtils.CALL_LOG;
import static com.example.chhattlal.chatapp.utils.PermissionUtils.CAMERA;
import static com.example.chhattlal.chatapp.utils.PermissionUtils.CONTACTS;
import static com.example.chhattlal.chatapp.utils.PermissionUtils.INTERNET;
import static com.example.chhattlal.chatapp.utils.PermissionUtils.INTERNET_STATE;
import static com.example.chhattlal.chatapp.utils.PermissionUtils.LOCATION;
import static com.example.chhattlal.chatapp.utils.PermissionUtils.PHONE;
import static com.example.chhattlal.chatapp.utils.PermissionUtils.SMS;
import static com.example.chhattlal.chatapp.utils.PermissionUtils.STORAGE;
import static com.example.chhattlal.chatapp.utils.PermissionUtils.VIBRATE_ACCESS;
import static com.example.chhattlal.chatapp.utils.PermissionUtils.WAKE;
import static java.lang.annotation.ElementType.PARAMETER;

@Retention(RetentionPolicy.SOURCE) @Target(PARAMETER) @IntDef(value = {
    INTERNET, INTERNET_STATE, ACCOUNTS, CONTACTS, PHONE, CALL_LOG, SMS, STORAGE, AUDIO, CAMERA,
    LOCATION, WAKE, VIBRATE_ACCESS
}, flag = true) public @interface PermissionType {

}
