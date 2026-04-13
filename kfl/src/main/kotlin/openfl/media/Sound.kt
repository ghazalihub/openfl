package openfl.media

import openfl.events.Event
import openfl.events.EventDispatcher
import openfl.net.URLRequest
import openfl.utils.ByteArray
import kotlinx.coroutines.*
import java.io.BufferedInputStream
import java.io.File
import java.net.URL
import javax.sound.sampled.*

class Sound(stream: URLRequest? = null) : EventDispatcher() {

    var bytesLoaded: Int = 0
    var bytesTotal: Int = 0
    var isBuffering: Boolean = false
    var url: String? = null

    private var __audioBuffer: kotlin.ByteArray? = null
    private var __audioFormat: AudioFormat? = null

    val length: Double
        get() {
            val format = __audioFormat ?: return 0.0
            val bytes = __audioBuffer ?: return 0.0
            return (bytes.size / format.frameSize).toDouble() / format.frameRate * 1000.0
        }

    init {
        if (stream != null) {
            load(stream)
        }
    }

    fun close() {
        __audioBuffer = null
    }

    fun load(stream: URLRequest) {
        this.url = stream.url
        val urlStr = stream.url ?: return
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val inputStream = if (urlStr.startsWith("http")) {
                    URL(urlStr).openStream()
                } else {
                    File(urlStr).inputStream()
                }
                val audioStream = AudioSystem.getAudioInputStream(BufferedInputStream(inputStream))
                __audioFormat = audioStream.format
                __audioBuffer = audioStream.readAllBytes()
                bytesLoaded = __audioBuffer!!.size
                bytesTotal = __audioBuffer!!.size
                withContext(Dispatchers.Main) {
                    dispatchEvent(Event(Event.COMPLETE))
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    dispatchEvent(openfl.events.IOErrorEvent(openfl.events.IOErrorEvent.IO_ERROR, text = e.message ?: "Error loading sound"))
                }
            }
        }
    }

    fun play(startTime: Double = 0.0, loops: Int = 0, sndTransform: SoundTransform? = null): SoundChannel {
        val channel = SoundChannel(this, sndTransform ?: SoundTransform())
        if (__audioBuffer != null && __audioFormat != null) {
            __playChannel(channel, startTime, loops)
        }
        return channel
    }

    private fun __playChannel(channel: SoundChannel, startTime: Double, loops: Int) {
        val format = __audioFormat ?: return
        val buffer = __audioBuffer ?: return

        try {
            val info = DataLine.Info(Clip::class.java, format)
            val clip = AudioSystem.getLine(info) as Clip
            clip.open(format, buffer, 0, buffer.size)
            clip.setMicrosecondPosition((startTime * 1000).toLong())
            if (loops > 0) clip.loop(loops)
            clip.start()
            // In a real , we'd wrap this in SoundChannel to control it
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
