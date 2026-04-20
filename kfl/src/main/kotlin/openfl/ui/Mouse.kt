package openfl.ui

object Mouse {
    private var __cursor: String = MouseCursor.AUTO

    var cursor: String
        get() = __cursor
        set(value) {
            __cursor = value

        }

    fun hide() {

    }

    fun show() {

    }
}
