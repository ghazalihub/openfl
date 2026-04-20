package openfl.display

open class MovieClip : Sprite() {
    var currentFrame: Int = 1
        private set
    var currentFrameLabel: String? = null
        private set
    var currentLabel: String? = null
        private set
    var currentLabels: Array<FrameLabel> = emptyArray()
        private set
    var currentScene: Scene = Scene("", emptyArray(), 1)
        private set
    var enabled: Boolean = true
    var framesLoaded: Int = 1
        private set
    var isPlaying: Boolean = false
        private set
    var scenes: Array<Scene> = emptyArray()
        private set
    var totalFrames: Int = 1
        private set

    fun gotoAndPlay(frame: Any, scene: String? = null) {
        __goto(frame)
        play()
    }

    fun gotoAndStop(frame: Any, scene: String? = null) {
        __goto(frame)
        stop()
    }

    fun nextFrame() {
        __goto(currentFrame + 1)
    }

    fun play() {
        isPlaying = true
    }

    fun prevFrame() {
        __goto(currentFrame - 1)
    }

    fun stop() {
        isPlaying = false
    }

    private fun __goto(frame: Any) {
        val f = when (frame) {
            is Int -> frame
            is String -> currentLabels.find { it.name == frame }?.frame ?: currentFrame
            else -> currentFrame
        }
        currentFrame = if (f < 1) 1 else if (f > totalFrames) totalFrames else f
    }

    override fun __update(transformOnly: Boolean, updateChildren: Boolean) {
        if (isPlaying) {
            currentFrame++
            if (currentFrame > totalFrames) {
                currentFrame = 1
            }
        }
        super.__update(transformOnly, updateChildren)
    }
}
