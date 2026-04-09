package openfl.display

import openfl.events.Event
import openfl.geom.Matrix
import openfl.geom.Rectangle

abstract class DisplayObjectContainer : InteractiveObject() {

    var mouseChildren: Boolean = true
    var tabChildren: Boolean = true
        set(value) {
            if (field != value) {
                field = value
                dispatchEvent(Event(Event.TAB_CHILDREN_CHANGE, true, false))
            }
        }

    val numChildren: Int
        get() = __children.size

    internal val __children = mutableListOf<DisplayObject>()

    fun addChild(child: DisplayObject): DisplayObject {
        return addChildAt(child, numChildren)
    }

    fun addChildAt(child: DisplayObject, index: Int): DisplayObject {
        if (child === this) throw IllegalArgumentException("An object cannot be added as a child of itself.")

        if (index > __children.size || index < 0) throw IndexOutOfBoundsException("Invalid index position $index")

        if (child.parent === this) {
            if (__children[index] !== child) {
                __children.remove(child)
                __children.add(index, child)
                __setRenderDirty()
            }
        } else {
            child.parent?.removeChild(child)
            __children.add(index, child)
            child.parent = this

            child.__setTransformDirty()
            child.__setRenderDirty()
            __setRenderDirty()

            val event = Event(Event.ADDED, true)
            child.dispatchEvent(event)
        }
        return child
    }

    fun contains(child: DisplayObject): Boolean {
        var current: DisplayObject? = child
        while (current != null && current !== this) {
            current = current.parent
        }
        return current === this
    }

    fun getChildAt(index: Int): DisplayObject? {
        return if (index in 0 until __children.size) __children[index] else null
    }

    fun getChildByName(name: String): DisplayObject? {
        return __children.find { it.name == name }
    }

    fun getChildIndex(child: DisplayObject): Int {
        return __children.indexOf(child)
    }

    fun removeChild(child: DisplayObject): DisplayObject {
        if (child.parent === this) {
            child.__setTransformDirty()
            child.__setRenderDirty()
            __setRenderDirty()

            val event = Event(Event.REMOVED, true)
            child.dispatchEvent(event)

            child.parent = null
            __children.remove(child)
        }
        return child
    }

    fun removeChildAt(index: Int): DisplayObject? {
        val child = getChildAt(index)
        return if (child != null) removeChild(child) else null
    }

    fun setChildIndex(child: DisplayObject, index: Int) {
        if (index >= 0 && index <= __children.size && child.parent === this) {
            __children.remove(child)
            __children.add(index, child)
            __setRenderDirty()
        }
    }

    fun swapChildren(child1: DisplayObject, child2: DisplayObject) {
        if (child1.parent === this && child2.parent === this) {
            val index1 = __children.indexOf(child1)
            val index2 = __children.indexOf(child2)
            __children[index1] = child2
            __children[index2] = child1
            __setRenderDirty()
        }
    }

    override fun __getBounds(rect: Rectangle, matrix: Matrix) {
        super.__getBounds(rect, matrix)
        if (__children.isEmpty()) return

        val childWorldTransform = Matrix()
        for (child in __children) {
            if (child.scaleX == 0.0 && child.scaleY == 0.0) continue
            // Simple absolute transform calculation
            childWorldTransform.copyFrom(child.__transform)
            childWorldTransform.concat(matrix)
            child.__getBounds(rect, childWorldTransform)
        }
    }

    override fun __update(transformOnly: Boolean, updateChildren: Boolean) {
        super.__update(transformOnly, updateChildren)
        if (updateChildren) {
            for (child in __children) {
                child.__update(transformOnly, true)
            }
        }
    }
}
