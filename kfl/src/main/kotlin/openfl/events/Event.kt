package openfl.events

open class Event(
    val type: String,
    val bubbles: Boolean = false,
    val cancelable: Boolean = false
) {
    companion object {
        const val ACTIVATE = "activate"
        const val ADDED = "added"
        const val ADDED_TO_STAGE = "addedToStage"
        const val CANCEL = "cancel"
        const val CHANGE = "change"
        const val CLEAR = "clear"
        const val CLOSING = "closing"
        const val CLOSE = "close"
        const val COMPLETE = "complete"
        const val CONNECT = "connect"
        const val CONTEXT3D_CREATE = "context3DCreate"
        const val COPY = "copy"
        const val CUT = "cut"
        const val DEACTIVATE = "deactivate"
        const val ENTER_FRAME = "enterFrame"
        const val EXIT_FRAME = "exitFrame"
        const val EXITING = "exiting"
        const val FRAME_CONSTRUCTED = "frameConstructed"
        const val FRAME_LABEL = "frameLabel"
        const val FULLSCREEN = "fullScreen"
        const val ID3 = "id3"
        const val INIT = "init"
        const val MOUSE_LEAVE = "mouseLeave"
        const val OPEN = "open"
        const val PASTE = "paste"
        const val REMOVED = "removed"
        const val REMOVED_FROM_STAGE = "removedFromStage"
        const val RENDER = "render"
        const val RESIZE = "resize"
        const val SCROLL = "scroll"
        const val SELECT = "select"
        const val SELECT_ALL = "selectAll"
        const val SOUND_COMPLETE = "soundComplete"
        const val TAB_CHILDREN_CHANGE = "tabChildrenChange"
        const val TAB_ENABLED_CHANGE = "tabEnabledChange"
        const val TAB_INDEX_CHANGE = "tabIndexChange"
        const val TEXTURE_READY = "textureReady"
        const val UNLOAD = "unload"
    }

    var currentTarget: Any? = null
        internal set

    var target: Any? = null
        internal set

    var eventPhase: EventPhase = EventPhase.AT_TARGET
        internal set

    internal var isCanceled: Boolean = false
    internal var isCanceledNow: Boolean = false
    internal var isDefaultPrevented: Boolean = false

    open fun clone(): Event {
        val event = Event(type, bubbles, cancelable)
        event.eventPhase = eventPhase
        event.target = target
        event.currentTarget = currentTarget
        return event
    }

    fun isDefaultPrevented(): Boolean = isDefaultPrevented

    fun preventDefault() {
        if (cancelable) {
            isDefaultPrevented = true
        }
    }

    fun stopImmediatePropagation() {
        isCanceled = true
        isCanceledNow = true
    }

    fun stopPropagation() {
        isCanceled = true
    }

    override fun toString(): String {
        return "[Event type=$type bubbles=$bubbles cancelable=$cancelable]"
    }

    protected fun formatToString(className: String, vararg args: String): String {
        var output = "[$className"
        for (arg in args) {
            val value = when (arg) {
                "type" -> type
                "bubbles" -> bubbles
                "cancelable" -> cancelable
                "eventPhase" -> eventPhase
                else -> {
                    try {
                        val field = this::class.java.getDeclaredField(arg)
                        field.isAccessible = true
                        field.get(this)
                    } catch (e: Exception) {
                        null
                    }
                }
            }
            output += if (value is String) " $arg=\"$value\"" else " $arg=$value"
        }
        output += "]"
        return output
    }
}
