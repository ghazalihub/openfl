package openfl.display

import openfl.display3D.Context3D
import openfl.events.EventDispatcher
import openfl.events.Event
import openfl.geom.Matrix3D

class Stage3D(private val __stage: Stage) : EventDispatcher() {
    var context3D: Context3D? = null
        private set
    var visible: Boolean = true
    var x: Double = 0.0
    var y: Double = 0.0

    private var __projectionTransform: Matrix3D = Matrix3D()
    private var __renderTransform: Matrix3D = Matrix3D()

    fun requestContext3D(context3DRenderMode: String = "auto", profile: String = "baseline") {
        if (context3D == null) {
            context3D = Context3D()
        }
        dispatchEvent(Event(Event.CONTEXT3D_CREATE))
    }

    fun requestContext3DMatchingProfiles(profiles: Array<String>) {
        requestContext3D()
    }

    internal fun __resize(width: Int, height: Int) {
        val w = if (width > 0) width else 1
        val h = if (height > 0) height else 1

        __projectionTransform.rawData = doubleArrayOf(
            2.0 / w, 0.0, 0.0, 0.0,
            0.0, -2.0 / h, 0.0, 0.0,
            0.0, 0.0, -2.0 / 2000.0, 0.0,
            -1.0, 1.0, 0.0, 1.0
        )
    }
}
