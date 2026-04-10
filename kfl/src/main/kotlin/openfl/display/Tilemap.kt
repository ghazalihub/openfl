package openfl.display

import openfl.geom.ColorTransform
import openfl.geom.Matrix
import openfl.geom.Rectangle
import org.jetbrains.skia.Canvas
import org.jetbrains.skia.Paint
import org.jetbrains.skia.Rect

class Tilemap(var width: Int, var height: Int, var tileset: Tileset? = null, var smoothing: Boolean = true) : DisplayObject() {
    private val __tiles = mutableListOf<Tile>()

    var tileAlphaEnabled: Boolean = true
    var tileBlendModeEnabled: Boolean = true
    var tileColorTransformEnabled: Boolean = true

    fun addTile(tile: Tile): Tile {
        __tiles.add(tile)
        tile.__tilemap = this
        __setRenderDirty()
        return tile
    }

    fun removeTile(tile: Tile): Tile {
        __tiles.remove(tile)
        tile.__tilemap = null
        __setRenderDirty()
        return tile
    }

    override fun __getBounds(rect: Rectangle, matrix: Matrix) {
        rect.union(Rectangle(0.0, 0.0, width.toDouble(), height.toDouble()))
    }

    internal fun __draw(canvas: Canvas) {
        val currentTileset = tileset ?: return
        val bitmapData = currentTileset.bitmapData ?: return
        val image = org.jetbrains.skia.Image.makeFromBitmap(bitmapData.__skiaBitmap)
        val paint = Paint().apply { isAntiAlias = smoothing }

        for (tile in __tiles) {
            val rect = currentTileset.__rects.getOrNull(tile.id) ?: continue
            canvas.save()

            // Apply tile transform
            val matrix = org.jetbrains.skia.Matrix33.makeTranslate(tile.x.toFloat(), tile.y.toFloat())
            if (tile.rotation != 0.0) {
                // Skia rotation is in degrees
                matrix.preConcat(org.jetbrains.skia.Matrix33.makeRotate(tile.rotation.toFloat()))
            }
            if (tile.scaleX != 1.0 || tile.scaleY != 1.0) {
                matrix.preConcat(org.jetbrains.skia.Matrix33.makeScale(tile.scaleX.toFloat(), tile.scaleY.toFloat()))
            }
            canvas.concat(matrix)

            canvas.drawImageRect(
                image,
                Rect.makeXYWH(rect.x.toFloat(), rect.y.toFloat(), rect.width.toFloat(), rect.height.toFloat()),
                Rect.makeXYWH(0f, 0f, rect.width.toFloat(), rect.height.toFloat()),
                paint
            )
            canvas.restore()
        }
    }
}

class Tile(
    var id: Int = 0,
    var x: Double = 0.0,
    var y: Double = 0.0,
    var scaleX: Double = 1.0,
    var scaleY: Double = 1.0,
    var rotation: Double = 0.0
) {
    internal var __tilemap: Tilemap? = null
}

class Tileset(var bitmapData: BitmapData? = null) {
    internal val __rects = mutableListOf<Rectangle>()

    fun addRect(rect: Rectangle): Int {
        __rects.add(rect)
        return __rects.size - 1
    }
}
