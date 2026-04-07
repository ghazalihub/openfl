import ./URLRequest
import ./URLLoaderDataFormat
import ./SharedObject
import ../events/EventDispatcher
import ../events/Event
import ../events/IOErrorEvent
import std/httpclient
import std/asyncdispatch

type
  URLLoader* = ref object of EventDispatcher
    bytesLoaded*: int
    bytesTotal*: int
    data*: RootRef
    dataFormat*: URLLoaderDataFormat

proc newURLLoader*(request: URLRequest = nil): URLLoader =
  let self = URLLoader(
    bytesLoaded: 0,
    bytesTotal: 0,
    dataFormat: URLLoaderDataFormat.TEXT
  )
  self.initEventDispatcher()
  # TODO: load if request is not nil
  return self

proc load*(self: URLLoader, request: URLRequest) =
  let client = newAsyncHttpClient()

  proc handleLoad() {.async.} =
    try:
      let resp = await client.get(request.url)
      let body = await resp.body

      # RootRef can only be cast from a ref type. string is a value type.
      # We need to wrap it in a ref object if we want to store it in RootRef.
      self.data = StringWrapper(val: body)
      self.bytesLoaded = body.len
      self.bytesTotal = body.len
      discard self.dispatchEvent(newEvent(COMPLETE))
    except:
      let errorEvent = newIOErrorEvent(IO_ERROR)
      errorEvent.text = getCurrentExceptionMsg()
      discard self.dispatchEvent(errorEvent)
    finally:
      client.close()

  asyncCheck handleLoad()

proc close*(self: URLLoader) =
  # TODO: implement
  discard
