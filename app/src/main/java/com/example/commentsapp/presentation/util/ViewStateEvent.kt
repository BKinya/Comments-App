package com.example.commentsapp.presentation.util

import java.util.concurrent.atomic.AtomicBoolean

// FIXME: Correct SingleLiveEventBehaviour
abstract class SingleEvent<T>(
    private val argument: T,
    protected open val isConsumed: AtomicBoolean = AtomicBoolean(false)
) {

    private fun isConsumed(setAsConsumed: Boolean = false) = isConsumed.getAndSet(setAsConsumed)

    fun consume(action: (T) -> Unit) {
        if (!isConsumed(true)) {
            action.invoke(argument)
        }
    }

    fun resend() {
        isConsumed.set(false)
    }
}

data class ViewStateErrorEvent(
    val payload: Throwable,
    override val isConsumed: AtomicBoolean = AtomicBoolean(false)
): SingleEvent<Throwable>(payload) {

    override fun equals(other: Any?): Boolean {
        return super.equals(other)
    }

    override fun hashCode(): Int {
        var result = payload.hashCode()
        result = 31 * result + isConsumed.hashCode()
        return result
    }
}