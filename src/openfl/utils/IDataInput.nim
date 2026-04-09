import ./Endian

type
  IDataInput* = concept x
    x.bytesAvailable is int
    x.endian is Endian
    x.objectEncoding is int

    x.readBoolean() is bool
    x.readByte() is int
    x.readBytes(bytes: var seq[byte], offset: int, length: int)
    x.readDouble() is float64
    x.readFloat() is float32
    x.readInt() is int
    x.readMultiByte(length: int, charSet: string) is string
    x.readObject() is RootRef
    x.readShort() is int
    x.readUnsignedByte() is int
    x.readUnsignedInt() is int
    x.readUnsignedShort() is int
    x.readUTF() is string
    x.readUTFBytes(length: int) is string
