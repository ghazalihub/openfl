package openfl.geom

open class Rectangle(
    var x: Double = 0.0,
    var y: Double = 0.0,
    var width: Double = 0.0,
    var height: Double = 0.0
) {
    var bottom: Double
        get() = y + height
        set(value) {
            height = value - y
        }

    var bottomRight: Point
        get() = Point(x + width, y + height)
        set(value) {
            width = value.x - x
            height = value.y - y
        }

    var left: Double
        get() = x
        set(value) {
            width -= value - x
            x = value
        }

    var right: Double
        get() = x + width
        set(value) {
            width = value - x
        }

    var size: Point
        get() = Point(width, height)
        set(value) {
            width = value.x
            height = value.y
        }

    var top: Double
        get() = y
        set(value) {
            height -= value - y
            y = value
        }

    var topLeft: Point
        get() = Point(x, y)
        set(value) {
            x = value.x
            y = value.y
        }

    fun clone(): Rectangle {
        return Rectangle(x, y, width, height)
    }

    fun contains(x: Double, y: Double): Boolean {
        return x >= this.x && y >= this.y && x < right && y < bottom
    }

    fun containsPoint(point: Point): Boolean {
        return contains(point.x, point.y)
    }

    fun containsRect(rect: Rectangle): Boolean {
        return if (rect.width <= 0 || rect.height <= 0) {
            rect.x > x && rect.y > y && rect.right < right && rect.bottom < bottom
        } else {
            rect.x >= x && rect.y >= y && rect.right <= right && rect.bottom <= bottom
        }
    }

    fun copyFrom(sourceRect: Rectangle) {
        x = sourceRect.x
        y = sourceRect.y
        width = sourceRect.width
        height = sourceRect.height
    }

    fun equals(toCompare: Rectangle?): Boolean {
        if (toCompare === this) return true
        return toCompare != null && x == toCompare.x && y == toCompare.y && width == toCompare.width && height == toCompare.height
    }

    fun inflate(dx: Double, dy: Double) {
        x -= dx
        width += dx * 2
        y -= dy
        height += dy * 2
    }

    fun inflatePoint(point: Point) {
        inflate(point.x, point.y)
    }

    fun intersection(toIntersect: Rectangle): Rectangle {
        val x0 = if (x < toIntersect.x) toIntersect.x else x
        val x1 = if (right > toIntersect.right) toIntersect.right else right

        if (x1 <= x0) {
            return Rectangle()
        }

        val y0 = if (y < toIntersect.y) toIntersect.y else y
        val y1 = if (bottom > toIntersect.bottom) toIntersect.bottom else bottom

        if (y1 <= y0) {
            return Rectangle()
        }

        return Rectangle(x0, y0, x1 - x0, y1 - y0)
    }

    fun intersectionToOutput(toIntersect: Rectangle, output: Rectangle?): Rectangle {
        val result = output ?: Rectangle()

        val x0 = if (x < toIntersect.x) toIntersect.x else x
        val x1 = if (right > toIntersect.right) toIntersect.right else right

        if (x1 <= x0) {
            result.setTo(0.0, 0.0, 0.0, 0.0)
            return result
        }

        val y0 = if (y < toIntersect.y) toIntersect.y else y
        val y1 = if (bottom > toIntersect.bottom) toIntersect.bottom else bottom

        if (y1 <= y0) {
            result.setTo(0.0, 0.0, 0.0, 0.0)
            return result
        }

        result.setTo(x0, y0, x1 - x0, y1 - y0)
        return result
    }

    fun intersects(toIntersect: Rectangle): Boolean {
        val x0 = if (x < toIntersect.x) toIntersect.x else x
        val x1 = if (right > toIntersect.right) toIntersect.right else right

        if (x1 <= x0) {
            return false
        }

        val y0 = if (y < toIntersect.y) toIntersect.y else y
        val y1 = if (bottom > toIntersect.bottom) toIntersect.bottom else bottom

        return y1 > y0
    }

    fun isEmpty(): Boolean {
        return width <= 0.0 || height <= 0.0
    }

    fun offset(dx: Double, dy: Double) {
        x += dx
        y += dy
    }

    fun offsetPoint(point: Point) {
        x += point.x
        y += point.y
    }

    fun setEmpty() {
        x = 0.0
        y = 0.0
        width = 0.0
        height = 0.0
    }

    fun setTo(xa: Double, ya: Double, widtha: Double, heighta: Double) {
        x = xa
        y = ya
        width = widtha
        height = heighta
    }

    fun union(toUnion: Rectangle): Rectangle {
        if (width == 0.0 || height == 0.0) {
            return toUnion.clone()
        } else if (toUnion.width == 0.0 || toUnion.height == 0.0) {
            return clone()
        }

        val x0 = if (x > toUnion.x) toUnion.x else x
        val x1 = if (right < toUnion.right) toUnion.right else right
        val y0 = if (y > toUnion.y) toUnion.y else y
        val y1 = if (bottom < toUnion.bottom) toUnion.bottom else bottom

        return Rectangle(x0, y0, x1 - x0, y1 - y0)
    }

    fun unionToOutput(toUnion: Rectangle, output: Rectangle?): Rectangle {
        val result = output ?: Rectangle()

        if (width == 0.0 || height == 0.0) {
            result.setTo(toUnion.x, toUnion.y, toUnion.width, toUnion.height)
            return result
        } else if (toUnion.width == 0.0 || toUnion.height == 0.0) {
            result.setTo(x, y, width, height)
            return result
        }

        val x0 = if (x > toUnion.x) toUnion.x else x
        val x1 = if (right < toUnion.right) toUnion.right else right
        val y0 = if (y > toUnion.y) toUnion.y else y
        val y1 = if (bottom < toUnion.bottom) toUnion.bottom else bottom

        result.setTo(x0, y0, x1 - x0, y1 - y0)
        return result
    }

    override fun toString(): String {
        return "(x=$x, y=$y, width=$width, height=$height)"
    }
}
