package com.fneis.myevents.ui.features.event.checkIn

sealed class CheckInState {

    object EmailEmpty : CheckInState()
    object EmailInvalid : CheckInState()
    object ShortName : CheckInState()
    object NameEmpty : CheckInState()
    object FormDone : CheckInState()
}