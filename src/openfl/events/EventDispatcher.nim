import std/tables
import ./Event
import ./EventPhase

type
  Listener = ref object
    callback: proc (event: Event)
    useCapture: bool
    priority: int
    useWeakReference: bool

  DispatchIterator = ref object
    active: bool
    index: int
    isCopy: bool
    list: seq[Listener]

  EventDispatcher* = ref object of RootObj
    eventMap: TableRef[string, seq[Listener]]
    iterators: TableRef[string, seq[DispatchIterator]]
    targetDispatcher: EventDispatcher

proc initEventDispatcher*(self: EventDispatcher, target: EventDispatcher = nil) =
  self.eventMap = new(TableRef[string, seq[Listener]])
  self.iterators = new(TableRef[string, seq[DispatchIterator]])
  self.targetDispatcher = target

proc newEventDispatcher*(target: EventDispatcher = nil): EventDispatcher =
  let self = EventDispatcher()
  self.initEventDispatcher(target)
  return self

proc addListenerByPriority(list: var seq[Listener], listener: Listener) =
  var addAtPosition = list.len
  for i in 0..<list.len:
    if list[i].priority < listener.priority:
      addAtPosition = i
      break
  list.insert(listener, addAtPosition)

method addEventListener*(self: EventDispatcher, typeStr: string, listener: proc (event: Event), useCapture: bool = false, priority: int = 0, useWeakReference: bool = false) {.base.} =
  if listener.isNil: return

  if not self.eventMap.hasKey(typeStr):
    let list = @[Listener(callback: listener, useCapture: useCapture, priority: priority, useWeakReference: useWeakReference)]
    self.eventMap[typeStr] = list
    self.iterators[typeStr] = @[DispatchIterator(active: false, index: 0, isCopy: false, list: list)]
  else:
    var list = self.eventMap[typeStr]
    for l in list:
      if l.callback.rawProc == listener.rawProc and l.useCapture == useCapture: return

    let iterators = self.iterators[typeStr]
    for it in iterators:
      if not it.isCopy:
        if it.active:
          it.list = list
          it.isCopy = true
        else:
          # If not active, we can just update the pointer to the new list after modification,
          # or let it be updated at the end of this proc.
          discard

    let newListener = Listener(callback: listener, useCapture: useCapture, priority: priority, useWeakReference: useWeakReference)
    self.eventMap[typeStr].addListenerByPriority(newListener)

    # Ensure all inactive iterators point to the newly updated list
    for it in self.iterators[typeStr]:
      if not it.active and not it.isCopy:
        it.list = self.eventMap[typeStr]

method hasEventListener*(self: EventDispatcher, typeStr: string): bool {.base.} =
  self.eventMap.hasKey(typeStr)

method removeEventListener*(self: EventDispatcher, typeStr: string, listener: proc (event: Event), useCapture: bool = false) {.base.} =
  if not self.eventMap.hasKey(typeStr) or listener.isNil: return

  var list = self.eventMap[typeStr]
  var foundIndex = -1
  for i in 0..<list.len:
    if list[i].callback.rawProc == listener.rawProc and list[i].useCapture == useCapture:
      foundIndex = i
      break

  if foundIndex != -1:
    let iterators = self.iterators[typeStr]
    for it in iterators:
      if it.active:
        if not it.isCopy:
          if foundIndex < it.index:
            it.index -= 1
        else:
          for j in it.index..<it.list.len:
            if it.list[j] == list[foundIndex]:
              it.list.delete(j)
              break

    self.eventMap[typeStr].delete(foundIndex)
    if self.eventMap[typeStr].len == 0:
      self.eventMap.del(typeStr)
      self.iterators.del(typeStr)

method dispatchEvent*(self: EventDispatcher, event: Event): bool {.base.} =
  if event.target.isNil:
    if not self.targetDispatcher.isNil:
      event.target = self.targetDispatcher
    else:
      event.target = self

  if not self.eventMap.hasKey(event.`type`): return true

  event.currentTarget = self
  let capture = (event.eventPhase == EventPhase.CAPTURING_PHASE)
  let typeStr = event.`type`

  if not self.iterators.hasKey(typeStr): return true

  var iterators = self.iterators[typeStr]
  var it = iterators[0]

  if it.active:
    it = DispatchIterator(active: false, index: 0, isCopy: false, list: self.eventMap[typeStr])
    self.iterators[typeStr].add(it)

  it.active = true
  it.index = 0

  while it.index < it.list.len:
    let listener = it.list[it.index]
    it.index += 1
    if listener.useCapture == capture:
      listener.callback(event)
      if event.kindIsCanceledNow: break

  it.active = false

  if self.iterators.hasKey(typeStr):
    if it != self.iterators[typeStr][0]:
      for i in 0..<self.iterators[typeStr].len:
        if self.iterators[typeStr][i] == it:
          self.iterators[typeStr].delete(i)
          break
    else:
      if self.eventMap.hasKey(typeStr):
        it.list = self.eventMap[typeStr]
      else:
        it.list = @[]
      it.isCopy = false
      it.index = 0

  return not event.isDefaultPrevented()

method willTrigger*(self: EventDispatcher, typeStr: string): bool {.base.} =
  self.hasEventListener(typeStr)
