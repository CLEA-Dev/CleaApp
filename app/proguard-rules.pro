# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Preserve line numbers for debuggable stack traces in release builds.
-keepattributes SourceFile,LineNumberTable
-renamesourcefileattribute SourceFile

# kotlinx.serialization: keep generated serializers and their descriptors.
-keepattributes *Annotation*, InnerClasses
-dontnote kotlinx.serialization.**
-keepclasseswithmembers class com.drcmind.cleaapp.** {
    kotlinx.serialization.KSerializer serializer(...);
}
-keep,includedescriptorclasses class com.drcmind.cleaapp.**$$serializer { *; }
-keepclassmembers class com.drcmind.cleaapp.** {
    *** Companion;
}
