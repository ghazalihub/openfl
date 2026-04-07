type
  Keyboard* = ref object

var capsLock*: bool = false
var numLock*: bool = false

proc isAccessible*(): bool =
  return true

# Common Key Codes
const
  NUMBER_0* = 48
  NUMBER_1* = 49
  NUMBER_2* = 50
  NUMBER_3* = 51
  NUMBER_4* = 52
  NUMBER_5* = 53
  NUMBER_6* = 54
  NUMBER_7* = 55
  NUMBER_8* = 56
  NUMBER_9* = 57
  A* = 65
  B* = 66
  C* = 67
  D* = 68
  E* = 69
  F* = 70
  G* = 71
  H* = 72
  I* = 73
  J* = 74
  K* = 75
  L* = 76
  M* = 77
  N* = 78
  O* = 79
  P* = 80
  Q* = 81
  R* = 82
  S* = 83
  T* = 84
  U* = 85
  V* = 86
  W* = 87
  X* = 88
  Y* = 89
  Z* = 90
  ENTER* = 13
  ESCAPE* = 27
  SPACE* = 32
  UP* = 38
  DOWN* = 40
  LEFT* = 37
  RIGHT* = 39
  SHIFT* = 16
  CONTROL* = 17
  ALT* = 18
