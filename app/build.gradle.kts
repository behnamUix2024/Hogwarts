plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.behnamuix.hogwarts"
    compileSdk = 35
    buildFeatures {
        buildConfig = true
    }
    defaultConfig {
        applicationId = "com.behnamuix.hogwarts"
        minSdk = 24
        targetSdk = 35
        versionCode = 2
        versionName = "1.0.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        val marketApplicationId = "ir.mservices.market"
        val marketBindAddress = "ir.mservices.market.InAppBillingService.BIND"
        manifestPlaceholders.apply {
            this["marketApplicationId"] = marketApplicationId
            this["marketBindAddress"] = marketBindAddress
            this["marketPermission"] = "${marketApplicationId}.BILLING"
        }

        buildConfigField(
            "String",
            "IAB_PUBLIC_KEY",
            "\"MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCajGt/zDvDkoqC2rr9Rcw6eZyrLCxN+g/0FjWCkN8cBV1aPDzfIaNb/TmGblPvQOi+YqkBVPvGOReEg8Y35NkrT6IvfQJxyARxfXm7lW4+943Xi+wTs8/tMtJs3hozQCQN+lPUQAoa60OQ6+o2FsEpHDqpCh8D9mY3a72DpLECGQIDAQAB\""
        )

        buildTypes {
            release {
                isMinifyEnabled = false
                proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
                )
            }
        }
        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_11
            targetCompatibility = JavaVersion.VERSION_11
        }
        kotlinOptions {
            jvmTarget = "11"
        }
        buildFeatures {
            compose = true
        }
    }

    dependencies {

        implementation(libs.androidx.core.ktx)
        implementation(libs.androidx.lifecycle.runtime.ktx)
        implementation(libs.androidx.activity.compose)
        implementation(platform(libs.androidx.compose.bom))
        implementation(libs.androidx.ui)
        implementation(libs.androidx.ui.graphics)
        implementation(libs.androidx.ui.tooling.preview)
        implementation(libs.androidx.material3)
        testImplementation(libs.junit)
        androidTestImplementation(libs.androidx.junit)
        androidTestImplementation(libs.androidx.espresso.core)
        androidTestImplementation(platform(libs.androidx.compose.bom))
        androidTestImplementation(libs.androidx.ui.test.junit4)
        debugImplementation(libs.androidx.ui.tooling)
        debugImplementation(libs.androidx.ui.test.manifest)

        val voyagerVersion = "1.1.0-beta02"

        // Multiplatform

        // Navigator
        implementation(libs.voyager.navigator)

        implementation(libs.coil.compose)
        implementation("com.github.myketstore:myket-billing-client:1.6")

    }
}