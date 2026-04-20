package openfl.net

class URLRequestHeader(var name: String = "", var value: String = "")

object URLRequestDefaults {
    var followRedirects: Boolean = true
    var idleTimeout: Double = 30000.0
    var manageCookies: Boolean = true
    var userAgent: String? = null
}

object URLRequestMethod {
    const val DELETE = "DELETE"
    const val GET = "GET"
    const val HEAD = "HEAD"
    const val OPTIONS = "OPTIONS"
    const val POST = "POST"
    const val PUT = "PUT"
}

class URLRequest(var url: String? = null) {
    var contentType: String? = null
    var data: Any? = null
    var followRedirects: Boolean = URLRequestDefaults.followRedirects
    var idleTimeout: Double = URLRequestDefaults.idleTimeout
    var manageCookies: Boolean = URLRequestDefaults.manageCookies
    var method: String = URLRequestMethod.GET
    var requestHeaders: MutableList<URLRequestHeader> = mutableListOf()
    var userAgent: String? = URLRequestDefaults.userAgent
    var withCredentials: Boolean = false
}
