package ru.diploma.appcomponents

import android.app.Application
import androidx.compose.runtime.Composable
import dagger.hilt.android.HiltAndroidApp
import ru.diploma.appcomponents.core.NavigationDrawerContent

@HiltAndroidApp
class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
    }
}