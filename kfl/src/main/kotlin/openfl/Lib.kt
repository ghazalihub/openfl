package openfl

import openfl.display.Application
import openfl.display.MovieClip

object Lib {
    var application: Application? = null
    val current: MovieClip by lazy { MovieClip() }

    fun getTimer(): Int {
        return System.currentTimeMillis().toInt()
    }

    fun setTimeout(closure: () -> Unit, delay: Int): Int {
        java.util.Timer().schedule(object : java.util.TimerTask() {
            override fun run() {
                closure()
            }
        }, delay.toLong())
        return 0 // Placeholder ID
    }
}
