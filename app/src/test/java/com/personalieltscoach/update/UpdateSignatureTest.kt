package com.personalieltscoach.update

import org.junit.Assert.*
import org.junit.Test

class UpdateSignatureTest {
    @Test fun neverTreatsTwoMissingSignaturesAsTrusted() {
        assertFalse(UpdatePackageVerifier.matchingSigners(emptySet(), emptySet()))
        assertFalse(UpdatePackageVerifier.matchingSigners(setOf("a"), emptySet()))
        assertFalse(UpdatePackageVerifier.matchingSigners(setOf("a"), setOf("b")))
        assertTrue(UpdatePackageVerifier.matchingSigners(setOf("a"), setOf("a")))
    }
}
