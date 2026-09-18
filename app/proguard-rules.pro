# R8 / ProGuard rules for Galpika (గల్పిక)

# Keep Models for Firebase Firestore deserialization
-keepattributes Signature
-keepattributes *Annotation*
-keepattributes EnclosingMethod
-keepattributes InnerClasses

-keep class com.alfanewgen.galpika.data.model.** { *; }

# Kotlin Coroutines
-keepnames class kotlinx.coroutines.internal.MainDispatcherFactory {}
-keepnames class kotlinx.coroutines.CoroutineExceptionHandler {}
-keepclassmembers class kotlinx.coroutines.** {
    volatile <fields>;
}

# Firebase & Play Services
-keep class com.google.firebase.** { *; }
-dontwarn com.google.firebase.**
-keep class com.google.android.gms.** { *; }
-dontwarn com.google.android.gms.**

# Coil Image Loader
-keep class coil.** { *; }
-dontwarn coil.**

# Compose runtime
-keep class androidx.compose.runtime.** { *; }

# AndroidX Navigation & Lifecycle
-keep class androidx.lifecycle.** { *; }
-keep class androidx.navigation.** { *; }

# Allow R8 optimizations
-repackageclasses
-allowaccessmodification
