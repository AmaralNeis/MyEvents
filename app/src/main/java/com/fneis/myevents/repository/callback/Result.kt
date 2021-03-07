package com.fneis.myevents.repository.callback

data class Result<out T>(val status: Status, val data: T?, val message: Int?) {

    enum class Status {
        SUCCESS,
        ERROR
    }

    companion object {
        fun <T> success(data: T): Result<T> {
            return Result(Status.SUCCESS, data, null)
        }

        fun <T> error(message: Int, data: T? = null): Result<T> {
            return Result(Status.ERROR, data, message)
        }
    }
}