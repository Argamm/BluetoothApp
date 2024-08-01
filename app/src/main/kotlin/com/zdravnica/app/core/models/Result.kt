package com.zdravnica.app.core.models

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * A generic class that holds a value.
 * @param <T>
 */
sealed class Result<out R> {

    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val error: Throwable) : Result<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[error=$error]"
        }
    }
}

/**
 * `true` if [Result] is of type [Success;] & holds non-null [Success.data;].
 */
val Result<*>.succeeded
    get() = this is Result.Success && data != null

fun <T> Result<T>.successOr(fallback: T): T {
    return (this as? Result.Success<T>)?.data ?: fallback
}

fun <T> Result<T>.successOrNull(): T? {
    return (this as? Result.Success<T>)?.data
}

@Throws(Throwable::class)
fun <T> Result<T>.successOrThrow(): T {
    when (val result = this) {
        is Result.Success -> return result.data
        is Result.Error -> throw result.error
    }
}

fun <T1, T2, R> combine2(
    result1: Result<T1>, result2: Result<T2>,
    combiner: (T1, T2) -> R
): Result<R> {
    return try {
        val data1 = result1.successOrThrow()
        val data2 = result2.successOrThrow()
        val result = combiner(data1, data2)
        Result.Success(result)
    } catch (loadDataException: Throwable) {
        Result.Error(loadDataException)
    }
}

/**
 * Updates value of [liveData] if [Result] is of type [Success]
 */
inline fun <reified T> Result<T>.updateOnSuccess(liveData: MutableLiveData<T>) {
    if (this is Result.Success) {
        liveData.value = data
    }
}

inline fun <reified T, reified R> Result<T>.map(transform: (T) -> R): Result<R> {
    return when (this) {
        is Result.Success<T> -> Result.Success(transform(this.data))
        is Result.Error -> Result.Error(this.error)
    }
}

inline fun <reified T, reified R> Flow<Result<T>>.mapResultData(
    crossinline transform: suspend (T) -> R
): Flow<Result<R>> {
    return map { result ->
        result.map { data ->
            transform(data)
        }
    }
}
