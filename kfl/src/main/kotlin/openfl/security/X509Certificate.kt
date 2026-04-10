package openfl.security

import openfl.utils.ByteArray
import java.security.cert.X509Certificate as JX509Certificate

class X509Certificate {
    var issuer: X500DistinguishedName = X500DistinguishedName()
        private set
    var subject: X500DistinguishedName = X500DistinguishedName()
        private set
    var validFrom: Double = 0.0
        private set
    var validTo: Double = 0.0
        private set

    internal var __cert: JX509Certificate? = null
}

class X500DistinguishedName {
    var commonName: String? = null
    var countryName: String? = null
    var localityName: String? = null
    var organizationName: String? = null
    var organizationalUnitName: String? = null
    var stateOrProvinceName: String? = null
}

enum class CertificateStatus {
    EXPIRED,
    INVALID,
    INVALID_CHAIN,
    NOT_YET_VALID,
    PRINCIPAL_MISMATCH,
    REVOKED,
    TRUSTED,
    UNTRUSTED_SIGNER
}
