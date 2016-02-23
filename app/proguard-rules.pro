# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /home/xuie/Android/Sdk/tools/proguard/proguard-android.txt
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

-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder { *; }

-keepattributes Signature

-keepclassmembers class * {
   public <init>(org.json.JSONObject);
}
-keep public class * extends com.umeng.**
-dontwarn com.umeng.**
-keep class com.umeng.** { *; }

# OkHttp
-keepattributes Signature
-keepattributes *Annotation*
-keep class com.squareup.okhttp.** { *; }
-dontwarn okio.**
-keep interface com.squareup.okhttp.** { *; }
-dontwarn com.squareup.okhttp.**

-keep public class * extends android.os.Binder
-keep class com.zhy.http.okhttp.** { *; }



