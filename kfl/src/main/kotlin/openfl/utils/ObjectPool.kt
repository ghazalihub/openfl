package openfl.utils

class ObjectPool<T>(val create: () -> T, val clean: ((T) -> Unit)? = null, var size: Int = 0) {
    private val __pool = mutableListOf<T>()

    init {
        repeat(size) {
            __pool.add(create())
        }
    }

    fun get(): T {
        return if (__pool.isNotEmpty()) __pool.removeAt(__pool.size - 1) else create()
    }

    fun release(obj: T) {
        clean?.invoke(obj)
        __pool.add(obj)
    }
}
