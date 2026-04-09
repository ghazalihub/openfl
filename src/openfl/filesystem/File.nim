import std/os

type
  File* = ref object
    nativePath*: string

proc newFile*(path: string = ""): File =
  return File(nativePath: path)

proc exists*(self: File): bool =
  return fileExists(self.nativePath) or dirExists(self.nativePath)

proc isDirectory*(self: File): bool =
  return dirExists(self.nativePath)

proc resolvePath*(self: File, path: string): File =
  return newFile(joinPath(self.nativePath, path))

var applicationDirectory*: File = newFile(getAppDir())
var applicationStorageDirectory*: File = newFile(getAppDir())
var desktopDirectory*: File = newFile(getHomeDir() / "Desktop")
var documentsDirectory*: File = newFile(getHomeDir() / "Documents")
var userDirectory*: File = newFile(getHomeDir())
