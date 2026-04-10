package openfl.utils

import openfl.display.BitmapData
import openfl.text.Font
import openfl.media.Sound

interface IAssetCache {
    var enabled: Boolean
    fun clear(prefix: String? = null)
    fun getBitmapData(id: String): BitmapData?
    fun getFont(id: String): Font?
    fun getSound(id: String): Sound?
    fun hasBitmapData(id: String): Boolean
    fun hasFont(id: String): Boolean
    fun hasSound(id: String): Boolean
    fun removeBitmapData(id: String)
    fun removeFont(id: String)
    fun removeSound(id: String)
    fun setBitmapData(id: String, bitmapData: BitmapData)
    fun setFont(id: String, font: Font)
    fun setSound(id: String, sound: Sound)
}

class AssetCache : IAssetCache {
    override var enabled: Boolean = true
    private val __bitmapData = mutableMapOf<String, BitmapData>()
    private val __font = mutableMapOf<String, Font>()
    private val __sound = mutableMapOf<String, Sound>()

    override fun clear(prefix: String?) {
        if (prefix == null) {
            __bitmapData.clear()
            __font.clear()
            __sound.clear()
        } else {
            __bitmapData.keys.removeIf { it.startsWith(prefix) }
            __font.keys.removeIf { it.startsWith(prefix) }
            __sound.keys.removeIf { it.startsWith(prefix) }
        }
    }

    override fun getBitmapData(id: String): BitmapData? = __bitmapData[id]
    override fun getFont(id: String): Font? = __font[id]
    override fun getSound(id: String): Sound? = __sound[id]
    override fun hasBitmapData(id: String): Boolean = __bitmapData.containsKey(id)
    override fun hasFont(id: String): Boolean = __font.containsKey(id)
    override fun hasSound(id: String): Boolean = __sound.containsKey(id)
    override fun removeBitmapData(id: String) { __bitmapData.remove(id) }
    override fun removeFont(id: String) { __font.remove(id) }
    override fun removeSound(id: String) { __sound.remove(id) }
    override fun setBitmapData(id: String, bitmapData: BitmapData) { if (enabled) __bitmapData[id] = bitmapData }
    override fun setFont(id: String, font: Font) { if (enabled) __font[id] = font }
    override fun setSound(id: String, sound: Sound) { if (enabled) __sound[id] = sound }
}
