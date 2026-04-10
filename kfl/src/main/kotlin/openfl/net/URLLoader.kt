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

class URLLoader(request: URLRequest? = null) : EventDispatcher() {

    var bytesLoaded: Int = 0
    var bytesTotal: Int = 0
    var data: Any? = null
    var dataFormat: String = URLLoaderDataFormat.TEXT

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
                    request.requestHeaders.forEach { header ->
                        headers.append(header.name, header.value)
                    }
                    if (request.data != null) {
                        setBody(request.data.toString())
                    }
                }

                val status = response.status.value
                dispatchEvent(HTTPStatusEvent(HTTPStatusEvent.HTTP_STATUS, false, false, status))

                if (response.status.isSuccess()) {
                    val bytes = response.readRawBytes()
                    bytesLoaded = bytes.size
                    bytesTotal = bytes.size

                    when (dataFormat) {
                        URLLoaderDataFormat.TEXT -> {
                            data = String(bytes, Charsets.UTF_8)
                        }
                        URLLoaderDataFormat.BINARY -> {
                            data = ByteArray.fromByteArray(bytes)
                        }
                        URLLoaderDataFormat.VARIABLES -> {
                            data = URLVariables(String(bytes, Charsets.UTF_8))
                        }
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
