type
  URLRequestHeader* = ref object
    name*: string
    value*: string

proc newURLRequestHeader*(name: string = "", value: string = ""): URLRequestHeader =
  return URLRequestHeader(name: name, value: value)
