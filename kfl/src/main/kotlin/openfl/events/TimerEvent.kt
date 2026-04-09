package openfl.events

class TimerEvent(
    type: String,
    bubbles: Boolean = false,
    cancelable: Boolean = false
) : Event(type, bubbles, cancelable) {

    companion object {
        const val TIMER = "timer"
        const val TIMER_COMPLETE = "timerComplete"
    }

    internal var updateAfterEventFlag: Boolean = false

    override fun clone(): TimerEvent {
        val event = TimerEvent(type, bubbles, cancelable)
        event.target = target
        event.currentTarget = currentTarget
        event.eventPhase = eventPhase
        return event
    }

    override fun toString(): String {
        return formatToString("TimerEvent", "type", "bubbles", "cancelable")
    }

    fun updateAfterEvent() {
        updateAfterEventFlag = true
    }
}
