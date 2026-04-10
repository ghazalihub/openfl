package openfl.utils

import openfl.display.BitmapData
import openfl.media.Sound
import openfl.text.Font
import openfl.events.EventDispatcher
import kotlinx.coroutines.*
import java.io.File
import javax.imageio.ImageIO

object Assets {
    var cache: IAssetCache = AssetCache()
    private val dispatcher = EventDispatcher()

    fun getBitmapData(id: String, useCache: Boolean = true): BitmapData? {
        if (useCache && cache.enabled && cache.hasBitmapData(id)) {
            return cache.getBitmapData(id)
        }
        val file = File(id)
        if (file.exists()) {
            val img = ImageIO.read(file)
            val bitmapData = BitmapData.fromBufferedImage(img)
            if (useCache) cache.setBitmapData(id, bitmapData)
            return bitmapData
        }
        return null
    }

    fun getBytes(id: String): ByteArray? {
        val file = File(id)
        if (file.exists()) {
            return ByteArray.fromByteArray(file.readBytes())
        }
        return null
    }

    fun getText(id: String): String? {
        val file = File(id)
        if (file.exists()) {
            return file.readText()
        }
        return null
    }

    suspend fun loadBitmapData(id: String, useCache: Boolean = true): BitmapData? = withContext(Dispatchers.IO) {
        getBitmapData(id, useCache)
    }

    suspend fun loadBytes(id: String): ByteArray? = withContext(Dispatchers.IO) {
        getBytes(id)
    }

    suspend fun loadText(id: String): String? = withContext(Dispatchers.IO) {
        getText(id)
    }
}
