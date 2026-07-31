package com.personalieltscoach.update

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.core.content.FileProvider
import java.io.File

object UpdateInstaller {
    fun permissionIntent(context: Context): Intent =
        Intent(
            Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES,
            Uri.parse("package:${context.packageName}")
        )

    fun installIntent(context: Context, filePath: String): Intent {
        val uri = FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            File(filePath)
        )
        return Intent(Intent.ACTION_VIEW).apply {
            setDataAndType(uri, UpdateDownloadManager.APK_MIME)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
    }

    fun openInstallFlow(context: Context, filePath: String) {
        context.startActivity(
            Intent(context, UpdateInstallActivity::class.java)
                .putExtra(UpdateInstallActivity.EXTRA_FILE_PATH, filePath)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        )
    }
}

