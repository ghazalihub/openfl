package openfl.system

object ApplicationDomain {
    var currentDomain: ApplicationDomain = ApplicationDomain()
        private set
    var parentDomain: ApplicationDomain? = null
        private set

    fun getDefinition(name: String): Any? {
        return null
    }

    fun hasDefinition(name: String): Boolean {
        return false
    }
}

class LoaderContext(
    var checkPolicyFile: Boolean = false,
    var applicationDomain: ApplicationDomain? = null,
    var securityDomain: SecurityDomain? = null
)

class SecurityDomain

object Security {
    fun allowDomain(vararg domains: String) {}
    fun allowInsecureDomain(vararg domains: String) {}
    fun loadPolicyFile(url: String) {}
}
