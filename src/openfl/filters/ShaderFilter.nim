import ../display/BlendMode
import ./BitmapFilter

type
  ShaderFilter* = ref object of BitmapFilter
    blendMode*: BlendMode
    shader*: RootRef # Placeholder for Shader

proc newShaderFilter*(shader: RootRef): ShaderFilter =
  let self = ShaderFilter(
    shader: shader,
    blendMode: BlendMode.NORMAL
  )
  self.numShaderPasses = 1
  return self

method clone*(self: ShaderFilter): BitmapFilter =
  let filter = newShaderFilter(self.shader)
  filter.bottomExtension = self.bottomExtension
  filter.leftExtension = self.leftExtension
  filter.rightExtension = self.rightExtension
  filter.topExtension = self.topExtension
  filter.blendMode = self.blendMode
  return filter

proc invalidate*(self: ShaderFilter) =
  self.renderDirty = true
