package edu.metrostate.ics342.mediatracker.ui.auth

import android.view.View
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


class RegisterViewModel : ViewModel() {

    private val _displayName = MutableStateFlow(value = "")
    val displayName = _displayName.asStateFlow()

    private val _userName = MutableStateFlow("")

    val userName = _userName.asStateFlow()

    private val _confirmPassword = MutableStateFlow("")

    val confirmPassword = _confirmPassword.asStateFlow()

    private val _email    = MutableStateFlow("")
    val email: StateFlow<String> = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()


    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage = _errorMessage.asStateFlow()

    fun setDisplayName(newValue: String) {
        _displayName.value = newValue
        _errorMessage.value = null
    }

    fun onConfirmPassword(value: String) {
        _confirmPassword.value = value
        _errorMessage.value = null
    }
    fun onUserName(value: String) { _userName.value = value }
    fun onEmailChange(value: String)    { _email.value    = value }
    fun onPasswordChange(value: String) { _password.value = value }

    fun onSignUpClick(): String {
        return when { _displayName.value.isBlank() ->
            "Please enter a display Name"

            _userName.value.isBlank() ->
                "Please enter a username"
            _email.value.isBlank() ->
                "Please enter an email"
            _password.value.isBlank() ->
                "Please enter a password"
            _confirmPassword.value.isBlank() ->
                "Please confirm your password"
            _password.value != _confirmPassword.value ->
                "Passwords dont match"

         else ->
            "Sign Up screen not implemented yet"
        }
    }
}
