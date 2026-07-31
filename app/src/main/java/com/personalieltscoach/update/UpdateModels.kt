package com.personalieltscoach.update

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GitHubRelease(
    @SerialName("tag_name") val tagName: String,
    val name: String? = null,
    val body: String? = null,
    @SerialName("html_url") val htmlUrl: String,
    val draft: Boolean = false,
    val prerelease: Boolean = false,
    val assets: List<GitHubReleaseAsset> = emptyList()
)

@Serializable
data class GitHubReleaseAsset(
    val name: String,
    @SerialName("browser_download_url") val downloadUrl: String,
    @SerialName("content_type") val contentType: String? = null,
    val size: Long = 0
)

data class AppUpdate(
    val version: String,
    val title: String,
    val releaseNotes: String,
    val downloadUrl: String,
    val assetName: String,
    val assetSize: Long,
    val releasePageUrl: String
)

data class UpdateUiState(
    val checking: Boolean = false,
    val available: AppUpdate? = null,
    val downloading: Boolean = false,
    val downloadProgress: Int? = null,
    val downloadedFile: String? = null,
    val error: String? = null
)

data class PendingUpdateDownload(
    val downloadId: Long,
    val filePath: String,
    val version: String
)

data class DownloadProgress(
    val status: Int,
    val percent: Int?,
    val reason: Int = 0
)

object VersionComparator {
    fun isNewer(candidate: String, current: String): Boolean =
        compare(candidate, current) > 0

    fun compare(left: String, right: String): Int {
        val a = parts(left)
        val b = parts(right)
        val size = maxOf(a.size, b.size)
        repeat(size) { index ->
            val result = (a.getOrElse(index) { 0 }).compareTo(b.getOrElse(index) { 0 })
            if (result != 0) return result
        }
        return 0
    }

    private fun parts(value: String): List<Int> =
        value.trim()
            .removePrefix("v")
            .removePrefix("V")
            .substringBefore('-')
            .substringBefore('+')
            .split('.')
            .map { part -> part.takeWhile(Char::isDigit).toIntOrNull() ?: 0 }
}

