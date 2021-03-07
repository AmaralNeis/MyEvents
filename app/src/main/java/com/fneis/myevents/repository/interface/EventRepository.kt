package com.fneis.myevents.repository.`interface`

import com.fneis.myevents.model.data.Event
import com.fneis.myevents.repository.callback.Result

interface EventRepository {
    suspend fun getEvents(): Result<List<Event>>
}