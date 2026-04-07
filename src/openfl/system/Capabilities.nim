import ./TouchscreenType

type
  Capabilities* = ref object

const
  avHardwareDisable* = true
  hasAccessibility* = false
  hasAudio* = true
  hasAudioEncoder* = false
  hasEmbeddedVideo* = false
  hasIME* = false
  hasMP3* = false
  hasPrinting* = false
  hasScreenBroadcast* = false
  hasScreenPlayback* = false
  hasStreamingAudio* = false
  hasStreamingVideo* = false
  hasTLS* = true
  hasVideoEncoder* = false
  isDebugger* = false
  isEmbeddedInAcrobat* = false
  localFileReadDisable* = false
  maxLevelIDC* = 0
  playerType* = "Desktop"
  screenColor* = "color"
  supports32BitProcesses* = true
  supports64BitProcesses* = true
  touchscreenType* = TouchscreenType.FINGER

proc cpuArchitecture*(): string = "x86"
proc language*(): string = "en"
proc manufacturer*(): string = "OpenFL Nim"
proc os*(): string = "Linux"
proc pixelAspectRatio*(): float64 = 1.0
proc screenDPI*(): float64 = 72.0
proc screenResolutionX*(): float64 = 0.0
proc screenResolutionY*(): float64 = 0.0
proc version*(): string = "NIM 1,0,0,0"

proc hasMultiChannelAudio*(typeStr: string): bool = false
