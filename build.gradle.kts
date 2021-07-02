// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
//    ext.kotlin_version = "1.4.32"

    val kotlinVersion = "1.5.10"
    val hiltVersion = "2.35"
    val navVersion = "2.3.5"


    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:4.1.3")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
        classpath("com.google.dagger:hilt-android-gradle-plugin:$hiltVersion")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:$navVersion")
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {
    repositories {
        google()
        jcenter()
    }
}

//task clean(type: Delete) {
//    delete rootProject.buildDir
//}
//


tasks.create<Delete>("cleanRP") {
    group = "rp"
    delete = setOf(
        "rp-out", "rp-zip"
    )
}