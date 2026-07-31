package com.personalieltscoach.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.personalieltscoach.CoachApplication

class DailyPlanWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result = runCatching {
        val app = applicationContext as CoachApplication
        app.container.coachRepository.ensureTodayPlan()
        Result.success()
    }.getOrElse { Result.retry() }
}

