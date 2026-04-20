package openfl.events

import openfl.net.URLRequestHeader

class HTTPStatusEvent(
    type: String,
    bubbles: Boolean = false,
    cancelable: Boolean = false,
    val status: Int = 0,
    var redirected: Boolean = false
) : Event(type, bubbles, cancelable) {

    companion object {
        const val HTTP_RESPONSE_STATUS = "httpResponseStatus"
        const val HTTP_STATUS = "httpStatus"
    }

    var responseHeaders: MutableList<URLRequestHeader> = mutableListOf()
    var responseURL: String? = null

    override fun clone(): HTTPStatusEvent {
        val event = HTTPStatusEvent(type, bubbles, cancelable, status, redirected)
        event.target = target
        event.currentTarget = currentTarget
        event.eventPhase = eventPhase
        event.responseHeaders = responseHeaders.toMutableList()
        event.responseURL = responseURL
        return event
    }

    override fun toString(): String {
        return formatToString("HTTPStatusEvent", "type", "bubbles", "cancelable", "status", "redirected")
    }
}
