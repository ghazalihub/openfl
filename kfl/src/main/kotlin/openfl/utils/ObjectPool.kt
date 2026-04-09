package openfl.utils

class ObjectPool<T>(
    var create: () -> T? = { null },
    var clean: (T) -> Unit = { },
    size: Int? = null
) {
    var activeObjects: Int = 0
        private set
    var inactiveObjects: Int = 0
        private set

    private val pool = mutableMapOf<T, Boolean>()
    private val inactiveObjectList = mutableListOf<T>()
    private var __size: Int? = null

    var size: Int?
        get() = __size
        set(value) {
            if (value == null) {
                __size = null
            } else {
                val current = inactiveObjects + activeObjects
                __size = value
                if (current > value) {
                    __removeInactive(current - value)
                } else if (value > current) {
                    for (i in 0 until (value - current)) {
                        val obj = create()
                        if (obj != null) {
                            pool[obj] = false
                            inactiveObjectList.add(obj)
                            inactiveObjects++
                        } else {
                            break
                        }
                    }
                }
            }
        }

    init {
        if (size != null) {
            this.size = size
        }
    }

    fun add(obj: T) {
        if (obj != null && !pool.containsKey(obj)) {
            pool[obj] = false
            clean(obj)
            __addInactive(obj)
        }
    }

    fun clear() {
        pool.clear()
        activeObjects = 0
        inactiveObjects = 0
        inactiveObjectList.clear()
    }

    fun get(): T? {
        var obj: T? = null
        if (inactiveObjects > 0) {
            obj = __getInactive()
        } else if (__size == null || activeObjects < __size!!) {
            obj = create()
            if (obj != null) {
                pool[obj] = true
                activeObjects++
            }
        }
        return obj
    }

    fun release(obj: T) {
        if (obj == null || !pool.containsKey(obj)) return

        activeObjects--
        if (__size == null || activeObjects + inactiveObjects < __size!!) {
            clean(obj)
            __addInactive(obj)
        } else {
            pool.remove(obj)
        }
    }

    fun remove(obj: T) {
        if (obj != null && pool.containsKey(obj)) {
            pool.remove(obj)
            if (inactiveObjectList.remove(obj)) {
                inactiveObjects--
            } else {
                activeObjects--
            }
        }
    }

    private fun __addInactive(obj: T) {
        pool[obj] = false
        inactiveObjectList.add(obj)
        inactiveObjects++
    }

    private fun __getInactive(): T {
        val obj = inactiveObjectList.removeAt(inactiveObjectList.size - 1)
        pool[obj] = true
        inactiveObjects--
        activeObjects++
        return obj
    }

    private fun __removeInactive(count: Int) {
        var remaining = count
        while (remaining > 0 && inactiveObjectList.isNotEmpty()) {
            val obj = inactiveObjectList.removeAt(inactiveObjectList.size - 1)
            pool.remove(obj)
            inactiveObjects--
            remaining--
        }
    }
}
