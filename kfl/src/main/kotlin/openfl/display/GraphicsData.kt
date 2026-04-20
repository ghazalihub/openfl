package openfl.display

interface IGraphicsData
interface IGraphicsFill
interface IGraphicsPath
interface IGraphicsStroke

class GraphicsSolidFill(var color: Int = 0, var alpha: Double = 1.0) : IGraphicsData, IGraphicsFill
class GraphicsEndFill : IGraphicsData, IGraphicsFill
class GraphicsStroke(
    var thickness: Double = Double.NaN,
    var pixelHinting: Boolean = false,
    var scaleMode: LineScaleMode = LineScaleMode.NORMAL,
    var caps: CapsStyle = CapsStyle.ROUND,
    var joints: JointStyle = JointStyle.ROUND,
    var miterLimit: Double = 3.0,
    var fill: IGraphicsFill? = null
) : IGraphicsData, IGraphicsStroke

class GraphicsPath(
    var commands: MutableList<Int>? = null,
    var data: MutableList<Double>? = null,
    var winding: GraphicsPathWinding = GraphicsPathWinding.EVEN_ODD
) : IGraphicsData, IGraphicsPath

object GraphicsPathCommand {
    const val NO_OP = 0
    const val MOVE_TO = 1
    const val LINE_TO = 2
    const val CURVE_TO = 3
    const val WIDE_MOVE_TO = 4
    const val WIDE_LINE_TO = 5
    const val CUBIC_CURVE_TO = 6
}

enum class GraphicsPathWinding {
    EVEN_ODD,
    NON_ZERO
}

class GraphicsBitmapFill(
    var bitmapData: BitmapData? = null,
    var matrix: openfl.geom.Matrix? = null,
    var repeat: Boolean = true,
    var smooth: Boolean = false
) : IGraphicsData, IGraphicsFill

class GraphicsGradientFill(
    var type: GradientType = GradientType.LINEAR,
    var colors: Array<Int> = emptyArray(),
    var alphas: Array<Double> = emptyArray(),
    var ratios: Array<Int> = emptyArray(),
    var matrix: openfl.geom.Matrix? = null,
    var spreadMethod: SpreadMethod = SpreadMethod.PAD,
    var interpolationMethod: InterpolationMethod = InterpolationMethod.RGB,
    var focalPointRatio: Double = 0.0
) : IGraphicsData, IGraphicsFill

enum class GradientType {
    LINEAR,
    RADIAL
}

enum class SpreadMethod {
    PAD,
    REFLECT,
    REPEAT
}

enum class InterpolationMethod {
    LINEAR_RGB,
    RGB
}
