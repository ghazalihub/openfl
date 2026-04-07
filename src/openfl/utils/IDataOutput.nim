import ./Endian

type
  IDataOutput* = concept x
    x.endian is Endian
    x.objectEncoding is int

    x.writeBoolean(value: bool)
    x.writeByte(value: int)
    x.writeBytes(bytes: seq[byte], offset: int, length: int)
    x.writeDouble(value: float64)
    x.writeFloat(value: float32)
    x.writeInt(value: int)
    x.writeMultiByte(value: string, charSet: string)
    x.writeObject(object: RootRef)
    x.writeShort(value: int)
    x.writeUnsignedInt(value: int)
    x.writeUTF(value: string)
    x.writeUTFBytes(value: string)
