package openfl.events

enum class EventPhase(val value: Int) {
    CAPTURING_PHASE(1),
    AT_TARGET(2),
    BUBBLING_PHASE(3);

    companion object {
        fun fromInt(value: Int) = values().first { it.value == value }
    }
}
