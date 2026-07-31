package com.personalieltscoach.update

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build
import com.personalieltscoach.BuildConfig
import java.io.File
import java.security.MessageDigest

sealed interface UpdateVerificationResult {
    data object Valid : UpdateVerificationResult
    data class Invalid(val message: String) : UpdateVerificationResult
}

class UpdatePackageVerifier(private val context: Context) {
    fun verify(filePath: String): UpdateVerificationResult {
        val file = File(filePath)
        if (!file.isFile || file.length() == 0L) {
            return UpdateVerificationResult.Invalid("更新文件不存在或下载不完整")
        }
        val archive = packageInfo(file.absolutePath)
            ?: return UpdateVerificationResult.Invalid("无法解析更新安装包")
        if (archive.packageName != context.packageName) {
            return UpdateVerificationResult.Invalid("更新包名不匹配，已拒绝安装")
        }
        if (archive.longVersionCodeCompat() <= BuildConfig.VERSION_CODE.toLong()) {
            return UpdateVerificationResult.Invalid("下载的版本不高于当前版本")
        }
        val current = packageInfo(context.packageName, installed = true)
            ?: return UpdateVerificationResult.Invalid("无法读取当前应用签名")
        if (signerDigests(archive) != signerDigests(current)) {
            return UpdateVerificationResult.Invalid("更新签名与当前应用不一致，已拒绝安装")
        }
        return UpdateVerificationResult.Valid
    }

    @Suppress("DEPRECATION")
    private fun packageInfo(value: String, installed: Boolean = false): PackageInfo? =
        runCatching {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                val flags = PackageManager.PackageInfoFlags.of(
                    PackageManager.GET_SIGNING_CERTIFICATES.toLong()
                )
                if (installed) context.packageManager.getPackageInfo(value, flags)
                else context.packageManager.getPackageArchiveInfo(value, flags)
            } else {
                val flags = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    PackageManager.GET_SIGNING_CERTIFICATES
                } else {
                    PackageManager.GET_SIGNATURES
                }
                if (installed) context.packageManager.getPackageInfo(value, flags)
                else context.packageManager.getPackageArchiveInfo(value, flags)
            }
        }.getOrNull()

    @Suppress("DEPRECATION")
    private fun signerDigests(info: PackageInfo): Set<String> {
        val signatures = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val signingInfo = info.signingInfo ?: return emptySet()
            if (signingInfo.hasMultipleSigners()) signingInfo.apkContentsSigners.toList()
            else signingInfo.signingCertificateHistory.toList()
        } else {
            info.signatures?.toList().orEmpty()
        }
        return signatures.mapTo(mutableSetOf()) { signature ->
            MessageDigest.getInstance("SHA-256")
                .digest(signature.toByteArray())
                .joinToString("") { "%02x".format(it) }
        }
    }

    @Suppress("DEPRECATION")
    private fun PackageInfo.longVersionCodeCompat(): Long =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) longVersionCode else versionCode.toLong()
}

