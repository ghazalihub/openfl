package openfl.events

open class EventDispatcher(private val target: IEventDispatcher? = null) : IEventDispatcher {

    private class Listener(
        val callback: (Event) -> Unit,
        val useCapture: Boolean,
        val priority: Int,
        val useWeakReference: Boolean
    ) {
        fun match(callback: (Event) -> Unit, useCapture: Boolean): Boolean {
            return this.callback == callback && this.useCapture == useCapture
        }
    }

    private val eventMap = mutableMapOf<String, MutableList<Listener>>()
    private val iterators = mutableMapOf<String, MutableList<DispatchIterator>>()

    private class DispatchIterator(var list: List<Listener>) : Iterator<Listener> {
        var active = false
        var index = 0
        var isCopy = false

        fun copy() {
            if (!isCopy) {
                list = list.toList()
                isCopy = true
            }
        }

        override fun hasNext(): Boolean = index < list.size

        override fun next(): Listener = list[index++]

        fun remove(listener: Listener, listIndex: Int) {
            if (active) {
                if (!isCopy) {
                    if (listIndex < index) {
                        index--
                    }
                } else {
                    val mutableList = list as MutableList
                    for (i in index until mutableList.size) {
                        if (mutableList[i] == listener) {
                            mutableList.removeAt(i)
                            break
                        }
                    }
                }
            }
        }

        fun reset(newList: List<Listener>) {
            list = newList
            isCopy = false
            index = 0
        }

        fun start() {
            active = true
        }

        fun stop() {
            active = false
        }
    }

    override fun addEventListener(
        type: String,
        listener: (Event) -> Unit,
        useCapture: Boolean,
        priority: Int,
        useWeakReference: Boolean
    ) {
        val list = eventMap.getOrPut(type) { mutableListOf() }

        for (item in list) {
            if (item.match(listener, useCapture)) return
        }

        val iters = iterators.getOrPut(type) { mutableListOf() }
        for (iterator in iters) {
            if (iterator.active) {
                iterator.copy()
            }
        }

        val newListener = Listener(listener, useCapture, priority, useWeakReference)
        var addAtPosition = list.size
        for (i in list.indices) {
            if (list[i].priority < priority) {
                addAtPosition = i
                break
            }
        }
        list.add(addAtPosition, newListener)

        if (iters.isEmpty()) {
            iters.add(DispatchIterator(list))
        }
    }

    override fun dispatchEvent(event: Event): Boolean {
        if (event.target == null) {
            event.target = target ?: this
        }
        return __dispatchEvent(event)
    }

    override fun hasEventListener(type: String): Boolean {
        return eventMap.containsKey(type)
    }

    override fun removeEventListener(
        type: String,
        listener: (Event) -> Unit,
        useCapture: Boolean
    ) {
        val list = eventMap[type] ?: return
        val iters = iterators[type] ?: return

        for (i in list.indices) {
            if (list[i].match(listener, useCapture)) {
                val removedListener = list[i]
                for (iterator in iters) {
                    iterator.remove(removedListener, i)
                }
                list.removeAt(i)
                break
            }
        }

        if (list.isEmpty()) {
            eventMap.remove(type)
            iterators.remove(type)
        }
    }

    override fun willTrigger(type: String): Boolean {
        return hasEventListener(type)
    }

    internal open fun __dispatchEvent(event: Event): Boolean {
        val list = eventMap[event.type] ?: return true

        if (event.target == null) {
            event.target = target ?: this
        }

        event.currentTarget = this
        val capture = event.eventPhase == EventPhase.CAPTURING_PHASE

        val iters = iterators[event.type]!!
        var iterator = iters[0]

        if (iterator.active) {
            iterator = DispatchIterator(list)
            iters.add(iterator)
        }

        iterator.start()
        while (iterator.hasNext()) {
            val listener = iterator.next()
            if (listener.useCapture == capture) {
                listener.callback(event)
                if (event.isCanceledNow) {
                    break
                }
            }
        }
        iterator.stop()

        if (iterator != iters[0]) {
            iters.remove(iterator)
        } else {
            iterator.reset(list)
        }

        return !event.isDefaultPrevented()
    }

    override fun toString(): String {
        return "[object ${this::class.java.simpleName}]"
    }
}
