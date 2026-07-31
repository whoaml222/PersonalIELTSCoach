package com.personalieltscoach.update

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import java.io.File

class UpdateDownloadReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action != DownloadManager.ACTION_DOWNLOAD_COMPLETE) return
        val manager = UpdateDownloadManager(context)
        val pending = manager.pending() ?: return
        val completedId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
        if (completedId != pending.downloadId) return
        val progress = manager.progress(completedId) ?: return
        if (progress.status == DownloadManager.STATUS_SUCCESSFUL &&
            File(pending.filePath).isFile
        ) {
            UpdateNotifier.showDownloaded(context, pending.filePath, pending.version)
        } else if (progress.status == DownloadManager.STATUS_FAILED) {
            manager.clearPending(removeFile = true)
        }
    }
}

