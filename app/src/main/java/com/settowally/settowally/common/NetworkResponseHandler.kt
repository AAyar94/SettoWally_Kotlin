package com.settowally.settowally.common

sealed class NetworkResponseHandler<T>(val data: T? = null, val message: String? = null) {

    class Success<T>(data: T) : NetworkResponseHandler<T>(data)
    class Error<T>(message: String?, data: T? = null) : NetworkResponseHandler<T>(data, message)
    class Loading<T>(data: T? = null) : NetworkResponseHandler<T>(data)

}