package io.chopyourbrain.kontrol.ktor

import io.ktor.http.content.OutgoingContent
import io.ktor.utils.io.ByteChannel
import io.ktor.utils.io.ByteReadChannel
import io.ktor.utils.io.copyTo
import io.ktor.utils.io.writer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope

suspend fun OutgoingContent.observe(): OutgoingContent = when (this) {
    is OutgoingContent.ReadChannelContent -> {
        val responseChannel = ByteChannel()
        val content = readFrom()

        content.copyTo(responseChannel)
        this
    }

    is OutgoingContent.WriteChannelContent -> {
        val responseChannel = ByteChannel()
        val content = toReadChannel()
        content.copyTo(responseChannel)
        this
    }

    else -> this
}
internal fun OutgoingContent.WriteChannelContent.toReadChannel(): ByteReadChannel =
    GlobalScope.writer(Dispatchers.Unconfined) {
        writeTo(channel)
    }.channel
