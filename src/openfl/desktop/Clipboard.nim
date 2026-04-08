import ./ClipboardFormats
import ./ClipboardTransferMode
import std/tables

type
  Clipboard* = ref object
    data: TableRef[ClipboardFormats, RootRef]

proc newClipboard*(): Clipboard =
  return Clipboard(data: new(TableRef[ClipboardFormats, RootRef]))

var generalClipboard*: Clipboard = newClipboard()

proc clear*(self: Clipboard) =
  self.data.clear()

proc clearData*(self: Clipboard, format: ClipboardFormats) =
  self.data.del(format)

proc getData*(self: Clipboard, format: ClipboardFormats, transferMode: ClipboardTransferMode = ClipboardTransferMode.CLONE_PREFERRED): RootRef =
  if self.data.hasKey(format):
    return self.data[format]
  return nil

proc setData*(self: Clipboard, format: ClipboardFormats, data: RootRef, serializable: bool = true): bool =
  self.data[format] = data
  return true

proc hasFormat*(self: Clipboard, format: ClipboardFormats): bool =
  return self.data.hasKey(format)
