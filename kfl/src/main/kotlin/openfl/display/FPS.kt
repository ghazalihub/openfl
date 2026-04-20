package openfl.display

import openfl.text.TextField
import openfl.text.TextFormat
import openfl.events.Event
import openfl.Lib

class FPS : TextField() {
    private var __currentTime: Int = 0
    private var __times: MutableList<Int> = mutableListOf()

    init {
        defaultTextFormat = TextFormat("_sans", 12, 0x000000)
        text = "FPS: 60"
        selectable = false
        mouseEnabled = false
        addEventListener(Event.ENTER_FRAME) {
            __onEnterFrame()
        }
    }

    private fun __onEnterFrame() {
        val now = Lib.getTimer()
        __times.add(now)
        while (__times[0] < now - 1000) {
            __times.removeAt(0)
        }
        if (__times.size > 1) {
            text = "FPS: " + __times.size
        }
    }
}
