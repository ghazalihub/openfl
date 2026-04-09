package openfl.utils

import openfl.events.EventDispatcher
import openfl.events.TimerEvent
import java.util.Timer as JTimer
import java.util.TimerTask

class Timer(var delay: Double, var repeatCount: Int = 0) : EventDispatcher() {

    var currentCount: Int = 0
        private set

    var running: Boolean = false
        private set

    private var timer: JTimer? = null

    init {
        if (delay.isNaN() || delay < 0) {
            throw IllegalArgumentException("The delay specified is negative or not a finite number")
        }
    }

    fun reset() {
        if (running) {
            stop()
        }
        currentCount = 0
    }

    fun start() {
        if (!running) {
            running = true
            timer = JTimer()
            timer?.scheduleAtFixedRate(object : TimerTask() {
                override fun run() {
                    timer_onTimer()
                }
            }, delay.toLong(), delay.toLong())
        }
    }

    fun stop() {
        running = false
        timer?.cancel()
        timer = null
    }

    private fun timer_onTimer() {
        currentCount++

        if (repeatCount > 0 && currentCount >= repeatCount) {
            stop()
            dispatchEvent(TimerEvent(TimerEvent.TIMER))
            dispatchEvent(TimerEvent(TimerEvent.TIMER_COMPLETE))
        } else {
            dispatchEvent(TimerEvent(TimerEvent.TIMER))
        }
    }
}
