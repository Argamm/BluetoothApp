package com.zdravnica.uikit.extensions.compose

internal interface MultipleEventsCutter {
    fun processEvent(event: () -> Unit)

    companion object
}

internal fun MultipleEventsCutter.Companion.get(): MultipleEventsCutter =
    MultipleEventsCutterImpl()

private class MultipleEventsCutterImpl : MultipleEventsCutter {
    private val now: Long
        get() = System.currentTimeMillis()

    private var lastEventTimeMs: Long = 0
    private val eventPauseTimeMs = 300L

    override fun processEvent(event: () -> Unit) {
        if (now - lastEventTimeMs >= eventPauseTimeMs) {
            event.invoke()
        }
        lastEventTimeMs = now
    }
}
