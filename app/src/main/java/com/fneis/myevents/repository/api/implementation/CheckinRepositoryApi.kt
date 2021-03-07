package com.fneis.myevents.repository.api.implementation

import com.fneis.myevents.model.data.CheckIn
import com.fneis.myevents.repository.`interface`.CheckInRepository
import com.fneis.myevents.repository.api.EventApi
import com.fneis.myevents.repository.callback.BaseApiSource

class CheckinRepositoryApi(private val api: EventApi) : CheckInRepository, BaseApiSource() {

    override suspend fun sendCheckIn(checkIn: CheckIn) = getResult { api.sendChekIn(checkIn) }
}