package com.fneis.myevents.repository.`interface`

import com.fneis.myevents.model.data.CheckIn
import com.fneis.myevents.repository.callback.Result
import okhttp3.ResponseBody

interface CheckInRepository {
    suspend fun sendCheckIn(checkIn: CheckIn): Result<ResponseBody>
}
