package com.drcmind.cleaapp.ui.auth.login

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.outlined.AlternateEmail
import androidx.compose.material.icons.outlined.Person
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
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel
import com.drcmind.cleaapp.ui.components.CleaButton
import com.drcmind.cleaapp.ui.components.CleaLogo
import com.drcmind.cleaapp.ui.components.CleaTextField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInScreen(
    onBackToLogin: () -> Unit,
    viewModel: SignInViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()
    val focusManager = LocalFocusManager.current
    var passwordVisible by remember { mutableStateOf(false) }
    var passwordConfirmationVisible by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }
    var acceptedTerms by remember { mutableStateOf(false) }

    LaunchedEffect(state.isSuccess) {
        if (state.isSuccess) {
            snackbarHostState.showSnackbar("Inscription réussie, vous pouvez vous connecter.")
            onBackToLogin()
        }
    }
    
    LaunchedEffect(state.globalError) {
        state.globalError?.let {
            if (state.fieldErrors.isEmpty()) {
                snackbarHostState.showSnackbar(it)
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("") },
                navigationIcon = {
                    IconButton(onClick = onBackToLogin) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Retour")
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState())
        ) {
            CleaLogo(horizontal = true)

            Spacer(modifier = Modifier.height(24.dp))
            
            Text(
                text = buildAnnotatedString {
                    append("Prenez soin de vous avec\n")
                    withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.secondary)) {
                        append("douceur et expertise.")
                    }
                },
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Rejoignez une communauté bienveillante dédiée à la santé féminine et au bien-être quotidien.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(32.dp))

            Surface(
                color = MaterialTheme.colorScheme.background,
                shadowElevation = 0.dp
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    Text(
                        text = "Créer votre compte",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    
                    Text(
                        text = "Commencez votre voyage santé dès aujourd'hui.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    
                    CleaTextField(
                        value = state.name,
                        onValueChange = viewModel::onNameChange,
                        label = "Nom complet",
                        isError = state.fieldErrors.containsKey("name"),
                        errorMessage = state.fieldErrors["name"]?.firstOrNull(),
                        trailingIcon = {
                            Icon(Icons.Outlined.Person, contentDescription = null, modifier = Modifier.size(20.dp))
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Next
                        )
                    )

                    CleaTextField(
                        value = state.email,
                        onValueChange = viewModel::onEmailChange,
                        label = "Email",
                        isError = state.fieldErrors.containsKey("email"),
                        errorMessage = state.fieldErrors["email"]?.firstOrNull(),
                        trailingIcon = {
                            Icon(Icons.Outlined.AlternateEmail, contentDescription = null, modifier = Modifier.size(20.dp))
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email,
                            imeAction = ImeAction.Next
                        )
                    )

                    CleaTextField(
                        value = state.password,
                        onValueChange = viewModel::onPasswordChange,
                        label = "Mot de passe",
                        isError = state.fieldErrors.containsKey("password"),
                        errorMessage = state.fieldErrors["password"]?.firstOrNull(),
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Next
                        ),
                        trailingIcon = {
                            val image = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                Icon(imageVector = image, contentDescription = if (passwordVisible) "Cacher" else "Afficher", modifier = Modifier.size(20.dp))
                            }
                        }
                    )

                    CleaTextField(
                        value = state.passwordConfirmation,
                        onValueChange = viewModel::onPasswordConfirmationChange,
                        label = "Confirmer le mot de passe",
                        isError = state.fieldErrors.containsKey("password_confirmation"),
                        errorMessage = state.fieldErrors["password_confirmation"]?.firstOrNull(),
                        visualTransformation = if (passwordConfirmationVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                focusManager.clearFocus()
                                viewModel.register()
                            }
                        ),
                        trailingIcon = {
                            val image = if (passwordConfirmationVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                            IconButton(onClick = { passwordConfirmationVisible = !passwordConfirmationVisible }) {
                                Icon(imageVector = image, contentDescription = if (passwordConfirmationVisible) "Cacher" else "Afficher", modifier = Modifier.size(20.dp))
                            }
                        }
                    )
                    
                    Row(verticalAlignment = Alignment.Top) {
                        Checkbox(
                            checked = acceptedTerms,
                            onCheckedChange = { acceptedTerms = it }
                        )
                        Text(
                            text = "J'accepte les Conditions d'utilisation et la Politique de confidentialité de CLEA.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(top = 12.dp)
                        )
                    }

                    AnimatedVisibility(visible = state.globalError != null && state.fieldErrors.isNotEmpty()) {
                        Text(
                            text = "Veuillez corriger les erreurs ci-dessus",
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }

                    CleaButton(
                        text = "CRÉER MON COMPTE",
                        onClick = {
                            focusManager.clearFocus()
                            viewModel.register()
                        },
                        isLoading = state.isLoading,
                        enabled = acceptedTerms
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Déjà un compte ? ",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                TextButton(
                    onClick = { onBackToLogin() }
                ) {
                    Text(
                        text = "Se connecter",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            }
        }
    }
}
