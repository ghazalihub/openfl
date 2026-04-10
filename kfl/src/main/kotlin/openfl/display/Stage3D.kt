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
        // Update projection matrix
    }
}
