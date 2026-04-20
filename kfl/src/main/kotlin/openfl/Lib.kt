package openfl

import openfl.display.MovieClip
import openfl.display.Stage
import openfl.net.URLRequest

object Lib {
    var current: MovieClip = MovieClip()
        private set

    internal fun __init(stage: Stage) {
        current = MovieClip()
        // current.stage = stage
        stage.addChild(current)
    }

    fun getTimer(): Int {
        return System.currentTimeMillis().toInt()
    }

    fun getURL(request: URLRequest, target: String? = null) {
        // Platform specific URL opening
        if (java.awt.Desktop.isDesktopSupported()) {
            java.awt.Desktop.getDesktop().browse(java.net.URI(request.url))
        }
    }

    fun trace(arg: Any?) {
        println(arg)
    }
}
