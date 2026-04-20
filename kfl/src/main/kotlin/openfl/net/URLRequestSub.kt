package openfl.net

class URLRequestHeader(var name: String = "", var value: String = "")

object URLRequestMethod {
    const val DELETE = "DELETE"
    const val GET = "GET"
    const val HEAD = "HEAD"
    const val OPTIONS = "OPTIONS"
    const val POST = "POST"
    const val PUT = "PUT"
}

object URLLoaderDataFormat {
    const val BINARY = "binary"
    const val TEXT = "text"
    const val VARIABLES = "variables"
}

object URLRequestDefaults {
    var followRedirects: Boolean = true
    var idleTimeout: Int = 30000
    var manageCookies: Boolean = true
    var userAgent: String = "OpenFL/kfl"
}
