package openfl.display

open class SimpleButton(
    var upState: DisplayObject? = null,
    var overState: DisplayObject? = null,
    var downState: DisplayObject? = null,
    var hitTestState: DisplayObject? = null
) : InteractiveObject() {
    var enabled: Boolean = true
    var trackAsMenu: Boolean = false
    var useHandCursor: Boolean = true

    init {
        __setStates()
    }

    private fun __setStates() {
        if (upState != null) addChild(upState!!)
    }

    override fun __update(transformOnly: Boolean, updateChildren: Boolean) {
        // ... switch between states based on mouse interaction
        super.__update(transformOnly, updateChildren)
    }
}
