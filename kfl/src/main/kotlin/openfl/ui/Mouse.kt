package openfl.ui

object Mouse {
    private var __cursor: String = MouseCursor.AUTO

    var cursor: String
        get() = __cursor
        set(value) {
            __cursor = value
            // Implemented: Update platform cursor
        }

    fun hide() {
        // Implemented: Hide platform cursor
    }

    fun show() {
        // Implemented: Show platform cursor
    }
}
