# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in C:\Users\Chhattlal\AppData\Local\Android\Sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

#Common for all
-keepattributes Signature
-keepattributes *Annotation*

#For Fabric
-keepattributes SourceFile,LineNumberTable,*Annotation*
-keep public class * extends java.lang.Exception
-keep class com.crashlytics.** { *; }
-dontwarn com.crashlytics.**

#For RetroLambda
-dontwarn java.lang.invoke.*

#For EventBus
-keepclassmembers class ** {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }

#For Picasso
-dontwarn com.squareup.okhttp.**

#For OkHttp3
-dontwarn okhttp3.**
-dontwarn okio.*

#For Retrofit
-dontwarn retrofit.**
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }

#For JobManager
-dontwarn com.birbit.android.jobqueue.**