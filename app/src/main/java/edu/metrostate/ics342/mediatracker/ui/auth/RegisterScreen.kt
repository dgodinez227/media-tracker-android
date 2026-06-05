package edu.metrostate.ics342.mediatracker.ui.auth

import android.view.RoundedCorner
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SecureTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.R
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import edu.metrostate.ics342.mediatracker.theme.OnPrimaryContainer
import edu.metrostate.ics342.mediatracker.theme.OnSurface
import edu.metrostate.ics342.mediatracker.theme.PrimaryContainer
import java.util.random.RandomGeneratorFactory.all

@Composable
fun RegisterScreen(
    onRegisterSuccess: () -> Unit,
    onNavigateToLogin: () -> Unit,
    viewModel: AuthViewModel = viewModel()
) {

    val displayName by viewModel.displayName.collectAsState()
    val userName by viewModel.userName.collectAsState()
    val email       by viewModel.email.collectAsState()
    val password    by viewModel.password.collectAsState()
    val confirmPassword    by viewModel.confirmPassword.collectAsState()
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),

        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {  Image(
        painter = painterResource(id = edu.metrostate.ics342.mediatracker.R.drawable.smart_display),
    contentDescription = "Application Icon",
    modifier = Modifier
        .size(width = 64.dp, height = 64.dp)
        .background(
            color = PrimaryContainer,
            shape = RoundedCornerShape(size = 12.dp)
        )
        .padding(all = 12.dp),
    colorFilter = ColorFilter.tint(color = OnPrimaryContainer)
    )

        Text(
            text = "Create Account",
            style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold,
            color = Color.Black

        )

        Spacer(Modifier.height(8.dp))

        Text(
            text = "Join the Community",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(40.dp))

        OutlinedTextField(
            value = ,
            onValueChange = viewModel::,
            label = { Text(
                text = "Display Name",) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down) }
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(12.dp))


        OutlinedTextField(
            value = ,
            onValueChange = viewModel::,
            label = {
                Text(
                    text = "Username",
                )
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down) }
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value         = email,
            onValueChange = viewModel::onEmailChange,
            label         = { Text(stringResource(edu.metrostate.ics342.mediatracker.R.string.email_label)) },
            singleLine    = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction    = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down) }
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = password,
            onValueChange = viewModel::onPasswordChange,
            label = { Text(stringResource(edu.metrostate.ics342.mediatracker.R.string.password_label)) },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { focusManager.clearFocus(); viewModel.onLoginClick() }
            ),
            modifier = Modifier.fillMaxWidth()
        )


        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = ,
            onValueChange = viewModel::,
            label = {
                Text(
                    text = "Confirm Password",
                )
            },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { focusManager.clearFocus(); viewModel.onLoginClick() }
            ),
            modifier = Modifier.fillMaxWidth()
        )


        Spacer(Modifier.height(24.dp))

        Button(
            onClick = { focusManager.clearFocus(); viewModel.onLoginClick() },

            modifier = Modifier.fillMaxWidth().height(48.dp)
        ) {
            if (false) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    strokeWidth = 2.dp,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            } else {
                Text(text = "Sign Up")
            }
        }

        Spacer(Modifier.height(16.dp))

        Text(
            text = "Already have an account",
        )
        TextButton(onClick = onNavigateToLogin) {
            Text(text = "Log In",)
        }
    }
}