/package edu.metrostate.ics342.mediatracker.ui.auth

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.metrostate.ics342.mediatracker.data.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow


class RegisterViewModel {
    private val userRepository: UserRepository,
    ): ViewModel() {

    private val _displayName = MutableStateFlow(value = "")
    val displayName = _displayName.asStateFlow()

    fun setDisplayName(newValue: String){
        _displayName.value = newValue
    }

    fun onSignUpClick(){
        viewModelScope.launch {
            userRepository.createAccount()
        }
    }
}