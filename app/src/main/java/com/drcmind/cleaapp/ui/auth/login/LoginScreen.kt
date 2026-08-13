package com.drcmind.cleaapp.ui.auth.login

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel
import com.drcmind.cleaapp.ui.components.CleaButton
import com.drcmind.cleaapp.ui.components.CleaLogo
import com.drcmind.cleaapp.ui.components.CleaTextField
import com.drcmind.cleaapp.ui.components.SocialLoginButton

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    viewModel: LoginViewModel = koinViewModel(),
    onSignInNavigation: () -> Unit
) {
    val state by viewModel.state.collectAsState()
    val focusManager = LocalFocusManager.current
    var passwordVisible by remember { mutableStateOf(false) }

    LaunchedEffect(state.isSuccess) {
        if (state.isSuccess) {
            onLoginSuccess()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.widthIn(max = 400.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CleaLogo()
            
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Votre santé, en toute sérénité.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(32.dp))

            CleaTextField(
                value = state.email,
                onValueChange = viewModel::onEmailChange,
                label = "Email",
                isError = state.fieldErrors.containsKey("email"),
                errorMessage = state.fieldErrors["email"]?.firstOrNull(),
                leadingIcon = {
                    Icon(Icons.Outlined.Email, contentDescription = null, modifier = Modifier.size(20.dp))
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            CleaTextField(
                value = state.password,
                onValueChange = viewModel::onPasswordChange,
                label = "Mot de passe",
                isError = state.fieldErrors.containsKey("password"),
                errorMessage = state.fieldErrors["password"]?.firstOrNull(),
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                        viewModel.login()
                    }
                ),
                leadingIcon = {
                    Icon(Icons.Outlined.Lock, contentDescription = null, modifier = Modifier.size(20.dp))
                },
                trailingIcon = {
                    val image = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(imageVector = image, contentDescription = if (passwordVisible) "Cacher" else "Afficher", modifier = Modifier.size(20.dp))
                    }
                }
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(onClick = { /* TODO */ }) {
                    Text(
                        "Mot de passe oublié ?",
                        color = MaterialTheme.colorScheme.secondary,
                        style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold)
                    )
                }
            }

            AnimatedVisibility(visible = state.globalError != null) {
                Text(
                    text = state.globalError ?: "",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            CleaButton(
                text = "Se connecter",
                onClick = {
                    focusManager.clearFocus()
                    viewModel.login()
                },
                isLoading = state.isLoading
            )

            Spacer(modifier = Modifier.height(32.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                HorizontalDivider(modifier = Modifier.weight(1f), color = MaterialTheme.colorScheme.outlineVariant)
                Text(
                    " OU CONTINUER AVEC ",
                    modifier = Modifier.padding(horizontal = 8.dp),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                HorizontalDivider(modifier = Modifier.weight(1f), color = MaterialTheme.colorScheme.outlineVariant)
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                SocialLoginButton(
                    text = "Google",
                    onClick = { /* TODO */ },
                    modifier = Modifier.weight(1f)
                )
                SocialLoginButton(
                    text = "Apple",
                    onClick = { /* TODO */ },
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Pas encore de compte ? ",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                TextButton(
                    onClick = { onSignInNavigation()}
                ) {
                    Text(
                        text = "S'inscrire",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            }
        }
    }
}
