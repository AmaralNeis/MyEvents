package com.fneis.myevents.ui.features.event.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fneis.myevents.R
import com.fneis.myevents.model.data.Event
import com.fneis.myevents.repository.`interface`.EventRepository
import kotlinx.coroutines.launch

class EventViewModel(private val repository: EventRepository) : ViewModel() {

    private val _listLiveData: MutableLiveData<Pair<List<Event>?, Int?>> = MutableLiveData()
    val listLiveData: LiveData<Pair<List<Event>?, Int?>> get() = _listLiveData

    fun getEvents() = viewModelScope.launch {
        val response = repository.getEvents()
        response.data?.let {
            if (it.isEmpty())
                _listLiveData.postValue(Pair(null, R.string.list_empty))
            else
                _listLiveData.postValue(Pair(it, null))
        }

        response.message?.let {
            _listLiveData.postValue(Pair(null, it))
        }
    }
}
