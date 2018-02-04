# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/jingli/opt/android-sdk/tools/proguard/proguard-android.txt
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
-renamesourcefileattribute SourceFile
-keepattributes SourceFile,LineNumberTable
-keep class com.basgeekball.awesomevalidation.** { *; }
-keep public class com.google.common.base.** { public *; }
-keep public class com.google.common.annotations.** { public *; }
-keep class com.google.appengine.api.ThreadManager
-keep class com.google.apphosting.api.ApiProxy
-keep, includedescriptorclasses public class com.google.common.collect.Range
-keepclassmembers class com.google.common.collect.Range** { *; }
-dontwarn com.google.errorprone.annotations.**
-dontwarn javax.annotation.ParametersAreNonnullByDefault
-dontwarn java.lang.ClassValue
-dontwarn sun.misc.Unsafe
-dontwarn afu.org.checkerframework.**
-dontwarn org.checkerframework.**
