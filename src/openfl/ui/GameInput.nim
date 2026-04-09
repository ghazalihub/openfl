import ../events/EventDispatcher

type
  GameInputControl* = ref object of EventDispatcher
    id*: string
    maxValue*: float64
    minValue*: float64
    value*: float64

proc newGameInputControl*(id: string, minValue: float64, maxValue: float64, value: float64): GameInputControl =
  let self = GameInputControl(id: id, minValue: minValue, maxValue: maxValue, value: value)
  self.initEventDispatcher()
  return self

type
  GameInputDevice* = ref object of EventDispatcher
    enabled*: bool
    id*: string
    name*: string
    numControls*: int

proc initGameInputDevice*(self: GameInputDevice, id: string, name: string) =
  self.initEventDispatcher()
  self.id = id
  self.name = name
  self.enabled = true
  self.numControls = 0

proc newGameInputDevice*(id: string, name: string): GameInputDevice =
  let self = GameInputDevice()
  self.initGameInputDevice(id, name)
  return self

proc getControlAt*(self: GameInputDevice, index: int): GameInputControl =
  return nil

type
  GameInput* = ref object of EventDispatcher

proc newGameInput*(): GameInput =
  let self = GameInput()
  self.initEventDispatcher()
  return self

var numDevices*: int = 0

proc getDeviceAt*(index: int): GameInputDevice =
  return nil
