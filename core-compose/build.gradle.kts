plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.nerevar.nba.core.compose"
    compileSdk = libs.versions.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.composeCompiler.get()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    api(libs.androidx.compose.activity)
    api(platform(libs.androidx.compose.bom))
    api(libs.androidx.compose.ui)
    api(libs.androidx.compose.ui.graphics)
    api(libs.androidx.compose.ui.tooling.preview)
    api(libs.androidx.compose.material3)
    api(libs.landscapist)
    api(libs.koin)
    api(libs.androidx.lifecycle.runtime.ktx)
    api(libs.androidx.lifecycle.runtime.compose)
    api(libs.kotlinx.coroutines.core)
    debugApi(libs.androidx.compose.ui.tooling)
    debugApi(libs.androidx.compose.ui.test.manifest)
}
