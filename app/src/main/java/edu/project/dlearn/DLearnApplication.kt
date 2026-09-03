package edu.project.dlearn

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import edu.project.dlearn.core.AppInitializer
import javax.inject.Inject

@HiltAndroidApp
class DLearnApplication : Application() {

    @Inject lateinit var appInitializer: AppInitializer

    override fun onCreate() {
        super.onCreate()
        appInitializer.initialiser()
    }
}
