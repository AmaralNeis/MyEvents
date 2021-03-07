package com.fneis.myevents.repository.api.implementation


import com.fneis.myevents.repository.`interface`.EventRepository
import com.fneis.myevents.repository.api.EventApi
import com.fneis.myevents.repository.callback.BaseApiSource

class EventRepositoryApi(private val api: EventApi) : EventRepository, BaseApiSource() {

    override suspend fun getEvents() = getResult { api.fetchUrl() }
}