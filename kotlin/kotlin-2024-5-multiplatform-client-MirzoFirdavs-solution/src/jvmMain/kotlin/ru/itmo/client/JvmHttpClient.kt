package ru.itmo.client

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.URI
import java.net.http.HttpResponse.BodyHandlers
import java.net.http.HttpClient as JavaHttpClient
import java.net.http.HttpRequest as JavaHttpRequest
import java.net.http.HttpResponse as JavaHttpResponse

class JvmHttpClient : HttpClient {
    private val client = JavaHttpClient.newBuilder().build()

    override suspend fun request(method: HttpMethod, request: HttpRequest): HttpResponse {
        val javaRequestBuilder = JavaHttpRequest.newBuilder()
            .uri(URI.create(request.url))

        when (method) {
            HttpMethod.GET -> javaRequestBuilder.GET()
            HttpMethod.POST -> {
                javaRequestBuilder.POST(JavaHttpRequest.BodyPublishers.ofByteArray(request.body ?: ByteArray(0)))
            }
            HttpMethod.PUT -> {
                javaRequestBuilder.PUT(JavaHttpRequest.BodyPublishers.ofByteArray(request.body ?: ByteArray(0)))
            }
            HttpMethod.DELETE -> javaRequestBuilder.DELETE()
        }

        request.headers.value.forEach { (key, value) ->
            javaRequestBuilder.header(key, value)
        }

        val response: JavaHttpResponse<ByteArray> = withContext(Dispatchers.IO) {
            client.send(javaRequestBuilder.build(), BodyHandlers.ofByteArray())
        }

        return HttpResponse(
            status = HttpStatus(response.statusCode()),
            headers = HttpHeaders(response.headers().map().mapValues { it.value.joinToString(", ") }),
            body = response.body()
        )
    }

    override fun close() {}
}
