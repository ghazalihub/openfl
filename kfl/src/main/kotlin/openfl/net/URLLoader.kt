package openfl.net

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.*
import openfl.events.*
import openfl.utils.ByteArray
import openfl.utils.ObjectPool

enum class URLLoaderDataFormat {
    BINARY,
    TEXT,
    VARIABLES
}

class URLLoader(request: URLRequest? = null) : EventDispatcher() {

    var bytesLoaded: Int = 0
    var bytesTotal: Int = 0
    var data: Any? = null
    var dataFormat: URLLoaderDataFormat = URLLoaderDataFormat.TEXT

    private val client = HttpClient(CIO)
    private var job: Job? = null

    init {
        if (request != null) {
            load(request)
        }
    }

    fun close() {
        job?.cancel()
        job = null
    }

    fun load(request: URLRequest) {
        val url = request.url ?: return
        dispatchEvent(Event(Event.OPEN))

        job = CoroutineScope(Dispatchers.IO).launch {
            try {
                val response: HttpResponse = client.request(url) {
                    method = HttpMethod.parse(request.method)
                    // TODO: Add headers and data from request
                }

                val status = response.status.value
                dispatchEvent(HTTPStatusEvent(HTTPStatusEvent.HTTP_STATUS, false, false, status))

                if (response.status.isSuccess()) {
                    when (dataFormat) {
                        URLLoaderDataFormat.TEXT -> {
                            data = response.bodyAsText()
                        }
                        URLLoaderDataFormat.BINARY -> {
                            // data = ByteArray(response.readRawBytes())
                        }
                        else -> {}
                    }
                    dispatchEvent(Event(Event.COMPLETE))
                } else {
                    dispatchEvent(IOErrorEvent(IOErrorEvent.IO_ERROR, text = "HTTP Error $status"))
                }
            } catch (e: Exception) {
                dispatchEvent(IOErrorEvent(IOErrorEvent.IO_ERROR, text = e.message ?: "Unknown error"))
            }
        }
    }
}
