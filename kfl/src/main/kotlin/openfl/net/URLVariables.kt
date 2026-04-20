package openfl.net

class URLVariables(source: String? = null) {
    private val __variables = mutableMapOf<String, String>()

    init {
        if (source != null) {
            decode(source)
        }
    }

    fun decode(source: String) {
        val pairs = source.split("&")
        for (pair in pairs) {
            val kv = pair.split("=")
            if (kv.size == 2) {
                __variables[kv[0]] = kv[1]
            }
        }
    }

    override fun toString(): String {
        return __variables.entries.joinToString("&") { "${it.key}=${it.value}" }
    }
}
