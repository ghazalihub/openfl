import ./DisplayObjectContainer
import ../events/Event
import ../net/URLRequest

type
  LoaderInfo* = ref object of EventDispatcher
    bytesLoaded*: int
    bytesTotal*: int
    content*: DisplayObject
    url*: string

proc newLoaderInfo*(): LoaderInfo =
  let self = LoaderInfo(
    bytesLoaded: 0,
    bytesTotal: 0
  )
  self.initEventDispatcher()
  return self

type
  Loader* = ref object of DisplayObjectContainer
    content*: DisplayObject
    contentLoaderInfo*: LoaderInfo

proc newLoader*(): Loader =
  let self = Loader(
    contentLoaderInfo: newLoaderInfo()
  )
  self.initDisplayObjectContainer()
  return self

proc load*(self: Loader, request: URLRequest, context: RootRef = nil) =
  self.contentLoaderInfo.url = request.url
  # TODO: implement async loading and content creation
  discard
