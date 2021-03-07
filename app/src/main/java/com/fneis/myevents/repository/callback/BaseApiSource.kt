package com.fneis.myevents.repository.callback

import com.fneis.myevents.R
import com.fneis.myevents.util.NetworkUtils
import retrofit2.Response

abstract class BaseApiSource {

    private val OFFLINE = "OFFLINE"
    private val TIMEOUT = "TIMEOUT"
    private val DEFAULT = "DEFAULT"

    protected suspend fun <T> getResult(call: suspend () -> Response<T>): Result<T> {

        try {

            val response = call()
            response.body()?.let { return Result.success(it) }

            response.errorBody()?.let {
                return errorMessage(DEFAULT)
            }

            return errorMessage(DEFAULT)
        } catch (e: Exception) {
            return if (e.toString().contains("failed"))
                errorMessage(TIMEOUT)
            else if (!NetworkUtils.checkNetworkState()) {
                errorMessage(OFFLINE)
            } else {
                errorMessage(DEFAULT)
            }
        }
    }

    private fun <T> errorMessage(message: String): Result<T> {

        return when (message) {
            OFFLINE -> Result.error(R.string.response_offline)
            TIMEOUT -> Result.error(R.string.response_timeout)
            else -> Result.error(R.string.response_default)
        }
    }
}
