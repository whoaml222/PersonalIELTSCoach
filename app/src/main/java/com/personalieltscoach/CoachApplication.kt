package com.personalieltscoach

import android.app.Application
import androidx.work.*
import com.personalieltscoach.ai.OpenAiSpeechService
import com.personalieltscoach.data.local.database.CoachDatabase
import com.personalieltscoach.data.repository.AiRepository
import com.personalieltscoach.data.repository.CoachRepository
import com.personalieltscoach.data.repository.SettingsRepository
import com.personalieltscoach.update.UpdateDownloadManager
import com.personalieltscoach.update.UpdateRepository
import com.personalieltscoach.worker.DailyPlanWorker
import com.personalieltscoach.worker.UpdateCheckWorker
import kotlinx.serialization.json.Json
import java.util.concurrent.TimeUnit

class CoachApplication : Application() {
    lateinit var container: AppContainer
        private set

    override fun onCreate() {
        super.onCreate()
        container = AppContainer(this)
        val request = PeriodicWorkRequestBuilder<DailyPlanWorker>(24, TimeUnit.HOURS)
            .setConstraints(Constraints.Builder().setRequiresBatteryNotLow(true).build())
            .build()
        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "daily_plan_generation",
            ExistingPeriodicWorkPolicy.UPDATE,
            request
        )
        val updateRequest = PeriodicWorkRequestBuilder<UpdateCheckWorker>(24, TimeUnit.HOURS)
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .setRequiresBatteryNotLow(true)
                    .build()
            )
            .build()
        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "app_update_check",
            ExistingPeriodicWorkPolicy.UPDATE,
            updateRequest
        )
    }
}

class AppContainer(val application: Application) {
    val json = Json {
        ignoreUnknownKeys = true
        explicitNulls = false
        encodeDefaults = true
    }
    val database = CoachDatabase.create(application)
    val settingsRepository = SettingsRepository(application)
    val speechService = OpenAiSpeechService(application, settingsRepository, json)
    val coachRepository = CoachRepository(database, settingsRepository, json)
    val aiRepository = AiRepository(database, settingsRepository, json)
    val updateRepository = UpdateRepository(json)
    val updateDownloadManager = UpdateDownloadManager(application)
}
