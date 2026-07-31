package com.personalieltscoach.update

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import java.io.File

class UpdateDownloadManager(private val context: Context) {
    private val downloadManager = context.getSystemService(DownloadManager::class.java)
    private val preferences = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE)

    fun enqueue(update: AppUpdate): PendingUpdateDownload {
        clearPending(removeFile = true)
        val safeName = "PersonalIELTSCoach-v${update.version}.apk"
        val file = File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), safeName)
        file.delete()
        val request = DownloadManager.Request(Uri.parse(update.downloadUrl))
            .setTitle("Personal IELTS Coach ${update.version}")
            .setDescription("正在下载应用更新")
            .setMimeType(APK_MIME)
            .setAllowedOverMetered(true)
            .setAllowedOverRoaming(false)
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
            .setDestinationInExternalFilesDir(context, Environment.DIRECTORY_DOWNLOADS, safeName)
        val id = downloadManager.enqueue(request)
        return PendingUpdateDownload(id, file.absolutePath, update.version).also(::savePending)
    }

    fun pending(): PendingUpdateDownload? {
        val id = preferences.getLong(KEY_ID, -1)
        val path = preferences.getString(KEY_PATH, null)
        val version = preferences.getString(KEY_VERSION, null)
        return if (id > 0 && path != null && version != null) {
            PendingUpdateDownload(id, path, version)
        } else null
    }

    fun progress(downloadId: Long): DownloadProgress? {
        val query = DownloadManager.Query().setFilterById(downloadId)
        return downloadManager.query(query)?.use { cursor ->
            if (!cursor.moveToFirst()) return@use null
            val status = cursor.getInt(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_STATUS))
            val reason = cursor.getInt(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_REASON))
            val downloaded = cursor.getLong(
                cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR)
            )
            val total = cursor.getLong(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_TOTAL_SIZE_BYTES))
            val percent = if (total > 0) ((downloaded * 100) / total).toInt().coerceIn(0, 100) else null
            DownloadProgress(status, percent, reason)
        }
    }

    fun cancel(downloadId: Long) {
        downloadManager.remove(downloadId)
        clearPending(removeFile = true)
    }

    fun clearPending(removeFile: Boolean = false) {
        if (removeFile) {
            preferences.getString(KEY_PATH, null)?.let { File(it).delete() }
        }
        preferences.edit().clear().apply()
    }

    private fun savePending(value: PendingUpdateDownload) {
        preferences.edit()
            .putLong(KEY_ID, value.downloadId)
            .putString(KEY_PATH, value.filePath)
            .putString(KEY_VERSION, value.version)
            .apply()
    }

    companion object {
        const val APK_MIME = "application/vnd.android.package-archive"
        private const val PREFERENCES = "pending_update_download"
        private const val KEY_ID = "download_id"
        private const val KEY_PATH = "file_path"
        private const val KEY_VERSION = "version"
    }
}

