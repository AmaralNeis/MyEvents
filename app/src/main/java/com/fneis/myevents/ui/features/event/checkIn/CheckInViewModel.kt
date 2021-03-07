package com.fneis.myevents.ui.features.event.checkIn

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fneis.myevents.extension.isNotEmail
import com.fneis.myevents.model.data.CheckIn
import com.fneis.myevents.repository.`interface`.CheckInRepository
import com.fneis.myevents.repository.callback.Result
import kotlinx.coroutines.launch

class CheckInViewModel(private val repository: CheckInRepository) : ViewModel() {

    private val _formLiveData: MutableLiveData<CheckInState> = MutableLiveData()
    val formLiveData: LiveData<CheckInState> get() = _formLiveData

    private val _checkinLiveDate: MutableLiveData<Boolean> = MutableLiveData()
    val checkinLiveDate get() = _checkinLiveDate

    fun validateEmailWith(email: String): Boolean {
        if (email.isEmpty()) {
            _formLiveData.value = CheckInState.EmailEmpty
            return false
        }

        if (email.isNotEmail()) {
            _formLiveData.value = CheckInState.EmailInvalid
            return false
        }

        return true
    }

    fun validateNameWith(name: String): Boolean {
        if (name.isEmpty()) {
            _formLiveData.value = CheckInState.NameEmpty
            return false
        }

        if (name.length < 3) {
            _formLiveData.value = CheckInState.ShortName
            return false
        }

        return true
    }

    fun validateFormWith(email: String, name: String) {
        val isEmail = validateEmailWith(email)
        val isName = validateNameWith(name)
        if (isName && isEmail)
            _formLiveData.value = CheckInState.FormDone
    }

    fun sendCheckin(checkIn: CheckIn) = viewModelScope.launch {
        val response = repository.sendCheckIn(checkIn)
        _checkinLiveDate.postValue(response.status == Result.Status.SUCCESS)
    }
}
