package openfl.display

import openfl.display._internal.IBitmapDrawableType
import openfl.events.Event
import openfl.geom.Matrix
import openfl.geom.Rectangle

class Stage : DisplayObjectContainer() {

    var align: StageAlign = StageAlign.TOP_LEFT
    var color: Int = 0xFFFFFF
    var frameRate: Double = 60.0
    var quality: StageQuality = StageQuality.HIGH
    var scaleMode: StageScaleMode = StageScaleMode.NO_SCALE

    var stageHeight: Int = 0
        internal set
    var stageWidth: Int = 0
        internal set

    init {
        __drawableType = IBitmapDrawableType.STAGE
        stage = this
    }

    override fun __update(transformOnly: Boolean, updateChildren: Boolean) {
        if (__transformDirty) {
            __transform.identity()
        }
        super.__update(transformOnly, updateChildren)
    }

    override fun __updateTransforms(overrideTransform: Matrix?) {
        __worldTransform.identity()
        __renderTransform.identity()
    }

    internal fun __resize(width: Int, height: Int) {
        stageWidth = width
        stageHeight = height
        dispatchEvent(Event(Event.RESIZE))
    }
}

enum class StageAlign {
    BOTTOM,
    BOTTOM_LEFT,
    BOTTOM_RIGHT,
    LEFT,
    RIGHT,
    TOP,
    TOP_LEFT,
    TOP_RIGHT
}

enum class StageQuality {
    BEST,
    HIGH,
    LOW,
    MEDIUM
}

enum class StageScaleMode {
    EXACT_FIT,
    NO_BORDER,
    NO_SCALE,
    SHOW_ALL
}
