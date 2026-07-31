package com.personalieltscoach.update

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts

class UpdateInstallActivity : ComponentActivity() {
    private lateinit var filePath: String
    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (canInstallPackages()) launchInstaller()
        else {
            Toast.makeText(this, "需要允许安装未知应用后才能更新", Toast.LENGTH_LONG).show()
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        filePath = intent.getStringExtra(EXTRA_FILE_PATH).orEmpty()
        when (val verification = UpdatePackageVerifier(this).verify(filePath)) {
            UpdateVerificationResult.Valid -> {
                if (canInstallPackages()) launchInstaller()
                else permissionLauncher.launch(UpdateInstaller.permissionIntent(this))
            }
            is UpdateVerificationResult.Invalid -> {
                Toast.makeText(this, verification.message, Toast.LENGTH_LONG).show()
                finish()
            }
        }
    }

    private fun canInstallPackages(): Boolean =
        packageManager.canRequestPackageInstalls()

    private fun launchInstaller() {
        runCatching { startActivity(UpdateInstaller.installIntent(this, filePath)) }
            .onFailure {
                Toast.makeText(this, "无法打开系统安装器：${it.message}", Toast.LENGTH_LONG).show()
            }
        finish()
    }

    companion object {
        const val EXTRA_FILE_PATH = "update_file_path"
    }
}
