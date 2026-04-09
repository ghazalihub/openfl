import ./Event

type
  IEventDispatcher* = concept x
    x.addEventListener(string, proc (event: Event), bool, int, bool)
    x.dispatchEvent(Event) is bool
    x.hasEventListener(string) is bool
    x.removeEventListener(string, proc (event: Event), bool)
    x.willTrigger(string) is bool
