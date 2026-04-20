package openfl.events

open class FullScreenEvent(
    type: String,
    bubbles: Boolean = false,
    cancelable: Boolean = false,
    var fullScreen: Boolean = false,
    var interactive: Boolean = false
) : ActivityEvent(type, bubbles, cancelable, fullScreen) {
    override fun clone(): FullScreenEvent {
        val event = FullScreenEvent(type, bubbles, cancelable, fullScreen, interactive)
        event.target = target
        event.currentTarget = currentTarget
        event.eventPhase = eventPhase
        return event
    }

    companion object {
        const val FULL_SCREEN = "fullScreen"
        const val FULL_SCREEN_INTERACTIVE_ACCEPTED = "fullScreenInteractiveAccepted"
    }
}
