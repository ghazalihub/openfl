import ./Matrix
import ./Matrix3D
import ./ColorTransform
import ./Rectangle

type
  DisplayObject = ref object of RootObj # Forward declaration placeholder

  Transform* = ref object
    colorTransformInternal: ColorTransform
    concatenatedColorTransform*: ColorTransform
    pixelBounds*: Rectangle
    displayObject: RootRef # Using RootRef to avoid circular dependency issues for now
    hasMatrix: bool
    hasMatrix3D: bool

proc newTransform*(displayObject: RootRef): Transform =
  return Transform(
    colorTransformInternal: newColorTransform(),
    concatenatedColorTransform: newColorTransform(),
    pixelBounds: newRectangle(),
    displayObject: displayObject,
    hasMatrix: true,
    hasMatrix3D: false
  )

proc colorTransform*(self: Transform): ColorTransform =
  return self.colorTransformInternal.clone()

proc `colorTransform=`*(self: Transform, value: ColorTransform) =
  self.colorTransformInternal.copyFrom(value)

proc matrix*(self: Transform): Matrix =
  # TODO: get from display object
  return newMatrix()

proc `matrix=`*(self: Transform, value: Matrix) =
  self.hasMatrix = true
  self.hasMatrix3D = false
  # TODO: update display object
