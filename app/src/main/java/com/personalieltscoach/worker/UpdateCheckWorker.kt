package com.personalieltscoach.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.personalieltscoach.CoachApplication
import com.personalieltscoach.update.UpdateNotifier

class UpdateCheckWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        val container = (applicationContext as CoachApplication).container
        val settings = container.settingsRepository.current()
        if (!settings.autoCheckUpdates || settings.updateRepository.isBlank()) {
            return Result.success()
        }
        return runCatching {
            container.updateRepository.check(settings.updateRepository)?.let {
                UpdateNotifier.showAvailable(applicationContext, it)
            }
            container.settingsRepository.setLastUpdateCheckAt(System.currentTimeMillis())
            Result.success()
        }.getOrElse { Result.success() }
    }
}
