package openfl.display

import openfl.events.Event
import openfl.Lib

class FPS(x: Double = 10.0, y: Double = 10.0, color: Int = 0x000000) : openfl.text.TextField() {

    private var currentTime: Int = 0
    private var times = mutableListOf<Int>()

    init {
        this.x = x
        this.y = y
        this.textColor = color
        this.selectable = false
        this.mouseEnabled = false
        this.text = "FPS: 0"

        addEventListener(Event.ENTER_FRAME, { onEnterFrame() })
    }

    private fun onEnterFrame() {
        currentTime = Lib.getTimer()
        times.add(currentTime)

        while (times[0] < currentTime - 1000) {
            times.removeAt(0)
        }

        if (times.size > 1) {
            this.text = "FPS: ${times.size}"
        }
    }
}
