package openfl.events

open class DataEvent(
    type: String,
    bubbles: Boolean = false,
    cancelable: Boolean = false,
    var data: String = ""
) : TextEvent(type, bubbles, cancelable, data) {
    override fun clone(): DataEvent {
        val event = DataEvent(type, bubbles, cancelable, data)
        event.target = target
        event.currentTarget = currentTarget
        event.eventPhase = eventPhase
        return event
    }

    companion object {
        const val DATA = "data"
        const val UPLOAD_COMPLETE_DATA = "uploadCompleteData"
    }
}
