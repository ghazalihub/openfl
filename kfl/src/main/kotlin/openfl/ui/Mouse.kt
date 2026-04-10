package openfl.ui

object Mouse {
    private var __cursor: String = MouseCursor.AUTO

    var cursor: String
        get() = __cursor
        set(value) {
            __cursor = value
            // TODO: Update platform cursor
        }

    fun hide() {
        // TODO: Hide platform cursor
    }

    fun show() {
        // TODO: Show platform cursor
    }
}
