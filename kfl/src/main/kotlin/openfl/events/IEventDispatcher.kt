package openfl.events

interface IEventDispatcher {
    fun addEventListener(
        type: String,
        listener: (Event) -> Unit,
        useCapture: Boolean = false,
        priority: Int = 0,
        useWeakReference: Boolean = false
    )

    fun dispatchEvent(event: Event): Boolean
    fun hasEventListener(type: String): Boolean
    fun removeEventListener(
        type: String,
        listener: (Event) -> Unit,
        useCapture: Boolean = false
    )
    fun willTrigger(type: String): Boolean
}
