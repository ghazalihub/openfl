import ./URLRequestMethod
import ./URLRequestHeader

type
  URLRequest* = ref object
    contentType*: string
    data*: RootRef
    followRedirects*: bool
    idleTimeout*: float64
    manageCookies*: bool
    `method`*: URLRequestMethod
    requestHeaders*: seq[URLRequestHeader]
    url*: string
    userAgent*: string
    withCredentials*: bool

proc newURLRequest*(url: string = ""): URLRequest =
  return URLRequest(
    url: url,
    contentType: "",
    followRedirects: true,
    idleTimeout: 30000,
    manageCookies: true,
    `method`: URLRequestMethod.GET,
    requestHeaders: @[],
    userAgent: "OpenFL Nim",
    withCredentials: false
  )
