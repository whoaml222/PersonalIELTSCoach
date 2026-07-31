# 应用更新发布流程

应用通过公开 GitHub Releases 检查新版本。设置页中的仓库格式为：

```text
GitHub用户名/仓库名
```

## 首次配置

1. 创建 GitHub 仓库并上传本项目。
2. 在仓库 Settings > Secrets and variables > Actions 中添加：
   - `ANDROID_KEYSTORE_BASE64`
   - `ANDROID_KEYSTORE_PASSWORD`
   - `ANDROID_KEY_ALIAS`
   - `ANDROID_KEY_PASSWORD`
3. 本项目现有签名文件为 `.signing/personal-ielts-coach.jks`。必须长期安全保存；丢失后无法覆盖更新已安装的应用。
4. 从 `1.2.1` 开始，应用默认使用 `whoaml222/PersonalIELTSCoach` 检查更新；设置页仍可覆盖此地址。

GitHub Actions 构建时会自动把当前 `GITHUB_REPOSITORY` 写入安装包，因此由工作流发布的后续版本不需要用户再次配置。本地构建未提供环境变量时，也会回退到上述默认仓库。

生成 Base64 签名内容的 PowerShell 示例：

```powershell
[Convert]::ToBase64String(
  [IO.File]::ReadAllBytes(".signing\personal-ielts-coach.jks")
) | Set-Clipboard
```

## 发布新版本

1. 修改 `app/build.gradle.kts` 中的 `versionCode` 和 `versionName`。
2. 提交并推送代码。
3. 创建并推送标签，例如：

```powershell
git tag v1.1.1
git push origin v1.1.1
```

GitHub Actions 会使用同一签名构建 APK，并创建 Release。应用会在启动时和每天后台检查最新正式 Release。

注意：

- Release 必须包含 `.apk` 文件。
- 标签建议使用 `v1.1.1` 格式。
- 草稿和预发布版本不会作为正式更新。
- Android 不允许普通应用静默安装；用户仍需确认系统安装界面。
