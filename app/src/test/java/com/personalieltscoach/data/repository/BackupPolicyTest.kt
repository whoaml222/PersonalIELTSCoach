package com.personalieltscoach.data.repository

import android.content.Context
import android.content.pm.ApplicationInfo
import androidx.test.core.app.ApplicationProvider
import com.personalieltscoach.R
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.xmlpull.v1.XmlPullParser

/** Guards the packaged policy; does not pretend to exercise an OEM's backup transport. */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [28])
class BackupPolicyTest {
    private val context = ApplicationProvider.getApplicationContext<Context>()
    private val domains = setOf("root", "file", "database", "sharedpref", "external",
        "device_root", "device_file", "device_database", "device_sharedpref")

    @Test fun manifestKeepsAutomaticBackupDisabled() {
        assertFalse(context.applicationInfo.flags and ApplicationInfo.FLAG_ALLOW_BACKUP != 0)
    }

    @Test fun legacyRulesExcludeAllDataDomains() {
        assertEquals(mapOf("full-backup-content" to domains), exclusions(R.xml.backup_rules))
    }

    @Test fun cloudAndDeviceTransferBothExcludeAllDataDomains() {
        assertEquals(mapOf("cloud-backup" to domains, "device-transfer" to domains),
            exclusions(R.xml.data_extraction_rules))
    }

    private fun exclusions(resource: Int): Map<String, Set<String>> {
        val result = mutableMapOf<String, MutableSet<String>>()
        var section = ""
        context.resources.getXml(resource).use { parser ->
            while (parser.next() != XmlPullParser.END_DOCUMENT) {
                if (parser.eventType != XmlPullParser.START_TAG) continue
                when (parser.name) {
                    "full-backup-content", "cloud-backup", "device-transfer" -> {
                        section = parser.name
                        result[section] = mutableSetOf()
                    }
                    "include" -> error("Private learning data must not be included in automatic backup")
                    "exclude" -> {
                        assertEquals(".", parser.getAttributeValue(null, "path"))
                        requireNotNull(result[section]).add(parser.getAttributeValue(null, "domain"))
                    }
                }
            }
        }
        return result
    }
}
