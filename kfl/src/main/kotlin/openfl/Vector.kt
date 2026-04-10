package openfl

class Vector<T>(capacity: Int = 0, var fixed: Boolean = false) : MutableList<T> by mutableListOf<T>() {
    init {
        if (capacity > 0) {
            // JVM ArrayList doesn't pre-fill, but we can if needed
        }
    }

    override fun add(element: T): Boolean {
        if (fixed) throw UnsupportedOperationException("Vector is fixed")
        return (this as MutableList<T>).add(element)
    }

    override fun removeAt(index: Int): T {
        if (fixed) throw UnsupportedOperationException("Vector is fixed")
        return (this as MutableList<T>).removeAt(index)
    }

    fun concat(a: Vector<T>? = null, b: Vector<T>? = null): Vector<T> {
        val result = Vector<T>()
        result.addAll(this)
        if (a != null) result.addAll(a)
        if (b != null) result.addAll(b)
        return result
    }

    fun join(separator: String = ","): String {
        return this.joinToString(separator)
    }

    fun push(element: T): Int {
        this.add(element)
        return this.size
    }

    fun pop(): T? {
        if (this.isEmpty()) return null
        return this.removeAt(this.size - 1)
    }

    fun shift(): T? {
        if (this.isEmpty()) return null
        return this.removeAt(0)
    }

    fun unshift(element: T) {
        this.add(0, element)
    }

    fun slice(startIndex: Int = 0, endIndex: Int = 16777215): Vector<T> {
        val end = minOf(endIndex, this.size)
        val result = Vector<T>()
        result.addAll(this.subList(startIndex, end))
        return result
    }

    fun sort(compare: (T, T) -> Int) {
        (this as MutableList<T>).sortWith(Comparator(compare))
    }

    fun splice(startIndex: Int, deleteCount: Int): Vector<T> {
        val result = Vector<T>()
        repeat(deleteCount) {
            if (startIndex < this.size) {
                result.add(this.removeAt(startIndex))
            }
        }
        return result
    }
}
