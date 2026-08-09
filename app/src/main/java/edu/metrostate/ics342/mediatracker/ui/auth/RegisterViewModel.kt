package edu.metrostate.ics342.mediatracker.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.metrostate.ics342.mediatracker.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import edu.metrostate.ics342.mediatracker.data.UserRepository
import kotlinx.coroutines.launch
import edu.metrostate.ics342.mediatracker.data.network.DefaultUserRepository
import edu.metrostate.ics342.mediatracker.data.RegisterResult


class RegisterViewModel : ViewModel() {

    sealed class RegisterUiState(errorPasswordsMismatch: Int) {
        object Idle    : RegisterUiState(R.string.error_passwords_mismatch)
        object Loading : RegisterUiState(R.string.error_passwords_mismatch)
        object Success : RegisterUiState(R.string.error_passwords_mismatch)
        data class Error(val msgResId: Int) : RegisterUiState(R.string.error_passwords_mismatch)
    }
    private val _displayName = MutableStateFlow(value = "")
    private val userRepository: UserRepository = DefaultUserRepository()
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

    private val _registerState = MutableStateFlow<RegisterUiState>(RegisterUiState.Idle)
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

    fun onRegisterClick(){
        viewModelScope.launch {
            _registerState.value = RegisterUiState.Loading

            if (_displayName.value.isBlank() || _email.value.isBlank() ||
                _userName.value.isBlank() || _password.value.isBlank() ||
                _confirmPassword.value.isBlank()
            ) {
                _registerState.value = RegisterUiState.Error(R.string.error_empty_fields)
                return@launch
            }

            if (_password.value != _confirmPassword.value) {
                _registerState.value = RegisterUiState.Error(R.string.error_passwords_mismatch)
                return@launch
            }

            val result = userRepository.register(
                email       = _email.value,
                password    = _password.value,
                username    = _userName.value,
                displayName = _displayName.value
            )

            _registerState.value = when (result) {
                RegisterResult.Success      -> RegisterUiState.Success
                RegisterResult.Conflict     -> RegisterUiState.Error(R.string.error_email_or_username_taken)
                RegisterResult.NetworkError -> RegisterUiState.Error(R.string.error_network)
                RegisterResult.UnknownError -> RegisterUiState.Error(R.string.error_generic)
            }
        }
    }
}
