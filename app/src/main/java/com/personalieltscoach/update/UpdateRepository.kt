package com.personalieltscoach.update

import com.personalieltscoach.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import java.util.concurrent.TimeUnit

class UpdateRepository(
    private val json: Json,
    private val client: OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(20, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .followRedirects(true)
        .build()
) {
    suspend fun check(repository: String): AppUpdate? = withContext(Dispatchers.IO) {
        val normalizedRepository = repository.trim()
        require(REPOSITORY_PATTERN.matches(normalizedRepository)) {
            "更新仓库格式应为 用户名/仓库名"
        }
        val request = Request.Builder()
            .url("https://api.github.com/repos/$normalizedRepository/releases/latest")
            .header("Accept", "application/vnd.github+json")
            .header("X-GitHub-Api-Version", "2022-11-28")
            .header("User-Agent", "PersonalIELTSCoach/${BuildConfig.VERSION_NAME}")
            .build()
        client.newCall(request).execute().use { response ->
            val body = response.body?.string().orEmpty()
            if (response.code == 404) {
                throw IOException("仓库中还没有已发布的 Release")
            }
            if (!response.isSuccessful) {
                throw IOException("检查更新失败：HTTP ${response.code}")
            }
            val release = json.decodeFromString<GitHubRelease>(body)
            if (release.draft || release.prerelease) return@withContext null
            val version = release.tagName.removePrefix("v").removePrefix("V")
            if (!VersionComparator.isNewer(version, BuildConfig.VERSION_NAME)) {
                return@withContext null
            }
            val asset = release.assets
                .filter { it.name.endsWith(".apk", ignoreCase = true) }
                .sortedWith(
                    compareByDescending<GitHubReleaseAsset> {
                        it.name.contains("release", ignoreCase = true) ||
                            it.name.contains("PersonalIELTSCoach", ignoreCase = true)
                    }.thenByDescending { it.size }
                )
                .firstOrNull()
                ?: throw IOException("最新 Release 中没有 APK 安装包")
            val downloadHost = runCatching { java.net.URI(asset.downloadUrl).host.orEmpty() }.getOrDefault("")
            if (!asset.downloadUrl.startsWith("https://") || downloadHost != "github.com") {
                throw IOException("更新下载地址不是受信任的 GitHub HTTPS 地址")
            }
            AppUpdate(
                version = version,
                title = release.name?.takeIf(String::isNotBlank) ?: "版本 $version",
                releaseNotes = release.body?.takeIf(String::isNotBlank) ?: "此版本包含功能改进和问题修复。",
                downloadUrl = asset.downloadUrl,
                assetName = asset.name,
                assetSize = asset.size,
                releasePageUrl = release.htmlUrl
            )
        }
    }

    companion object {
        private val REPOSITORY_PATTERN =
            Regex("[A-Za-z0-9_.-]+/[A-Za-z0-9_.-]+")
    }
}

