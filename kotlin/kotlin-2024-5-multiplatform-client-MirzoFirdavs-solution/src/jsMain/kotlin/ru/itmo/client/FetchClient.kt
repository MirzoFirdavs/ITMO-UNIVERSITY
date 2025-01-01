package ru.itmo.client

import org.w3c.fetch.RequestInit
import kotlinx.browser.window
import kotlinx.coroutines.await
import org.w3c.fetch.Response
import kotlin.js.Promise

private enum class Platform { Node, Browser }

private val platform: Platform
    get() {
        val hasNodeApi = js(
            """
            (typeof process !== 'undefined' 
                && process.versions != null 
                && process.versions.node != null) ||
            (typeof window !== 'undefined' 
                && typeof window.process !== 'undefined' 
                && window.process.versions != null 
                && window.process.versions.node != null)
            """
        ) as Boolean
        return if (hasNodeApi) Platform.Node else Platform.Browser
    }

private val nodeFetch: dynamic
    get() = js("eval('require')('node-fetch')")

private fun RequestInit.asNodeOptions(): dynamic =
    js("Object").assign(js("Object").create(null), this)

class FetchClient : HttpClient {
    override suspend fun request(method: HttpMethod, request: HttpRequest): HttpResponse {
        val init = RequestInit(
            method = method.name,
            headers = request.headers.value.toJsObject(),
            body = request.body?.decodeToString()
        )

        val response: Response = when (platform) {
            Platform.Browser -> window.fetch(request.url, init).await()
            Platform.Node -> (nodeFetch(request.url, init.asNodeOptions()) as Promise<Response>).await()
        }

        val responseBody = response.text().await().encodeToByteArray()
        val m: MutableMap<String, String> = mutableMapOf()

        response.headers.asDynamic().forEach { key: String, value: String ->
            value.also { m[key] = it }
        }

        return HttpResponse(
            status = HttpStatus(response.status.toInt()),
            headers = HttpHeaders(m),
            body = responseBody as? ByteArray
        )
    }

    override fun close() {}

    private fun Map<String, String>.toJsObject(): dynamic {
        val obj = js("Object").create(null)
        forEach { (key, value) ->
            obj[key] = value
        }
        return obj
    }
}
