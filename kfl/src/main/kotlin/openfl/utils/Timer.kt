package openfl.utils

import openfl.events.EventDispatcher
import openfl.events.TimerEvent
import openfl.Lib
import kotlinx.coroutines.*

class Timer(var delay: Double, var repeatCount: Int = 0) : EventDispatcher() {
    var currentCount: Int = 0
        private set
    var running: Boolean = false
        private set

    private var __job: Job? = null

    fun start() {
        if (running) return
        running = true
        __job = CoroutineScope(Dispatchers.Default).launch {
            while (running && (repeatCount == 0 || currentCount < repeatCount)) {
                delay(delay.toLong())
                if (!running) break
                currentCount++
                withContext(Dispatchers.Main) {
                    dispatchEvent(TimerEvent(TimerEvent.TIMER))
                }
                if (repeatCount > 0 && currentCount >= repeatCount) {
                    running = false
                    withContext(Dispatchers.Main) {
                        dispatchEvent(TimerEvent(TimerEvent.TIMER_COMPLETE))
                    }
                }
            }
        }
    }

    fun stop() {
        running = false
        __job?.cancel()
        __job = null
    }

    fun reset() {
        stop()
        currentCount = 0
    }
}
